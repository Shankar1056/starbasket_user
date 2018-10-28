package apextechies.starbasket.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.activity.MainActivity
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.model.LoginModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.NumberFormatException
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import android.util.Log
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import java.lang.IllegalStateException


class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private val RC_SIGN_IN = 7

    private var mGoogleApiClient: GoogleApiClient? = null
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    var retrofitDataProvider: RetrofitDataProvider? = null
    var mobile = ""
    var email = ""
    var status = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofitDataProvider = RetrofitDataProvider(this)

        buttonClickListener()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        input_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                validmobile(p0)
            }
        })



        // Customizing G+ button
        gplus_sign_in.setSize(SignInButton.SIZE_STANDARD)
        gplus_sign_in.setScopes(gso!!.scopeArray)

    }

    private fun buttonClickListener() {
        btn_submit.setOnClickListener {
            chckValidationCallApi()
        }

        forgotpassword.setOnClickListener {
                        startActivity(Intent(this, ForgotPassword::class.java))
        }

        signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        gplus_sign_in.setOnClickListener {
            ClsGeneral.setBoolean(this, "islogout", false)
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        imageView2.setOnClickListener {
            ClsGeneral.setBoolean(this, "islogout", false)
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            if (ClsGeneral.getBoolPreferences(this, "islogout")){
                try {
                    logoutGPlus()
                }catch (e: IllegalStateException){
                   startActivity(Intent(this, LoginActivity::class.java))
                }

            }
            else {
                val acct = result.signInAccount
                val personName = acct!!.displayName
                val personPhotoUrl = acct.photoUrl!!.toString()
                val email = acct.email

                Log.e("", "Name: " + personName + ", email: " + email
                        + ", Image: " + personPhotoUrl)

                callLoginApi(acct.email!!, "", "gplus")
            }

            /*txtName.setText(personName)
            txtEmail.setText(email)
            Glide.with(applicationContext).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic)
*/

        }
    }

    private fun logoutGPlus() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                object : ResultCallback<Status> {
                    override fun onResult(status: Status) {

                    }
                })
    }



    override fun onPause() {
        super.onPause()
        mGoogleApiClient!!.stopAutoManage(this)
        mGoogleApiClient!!.disconnect();

    }
    override fun onStart() {
        super.onStart()

        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            val result = opr.get()
            handleSignInResult(result)
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            Utilz.showDailog(this, resources.getString(R.string.pleaewait))
            opr.setResultCallback(object : ResultCallback<GoogleSignInResult> {
              override fun onResult(googleSignInResult: GoogleSignInResult) {
                    Utilz.dismissProgressDialog()
                    handleSignInResult(googleSignInResult)
                }
            })
        }
    }

    private fun chckValidationCallApi() {
        if (status){
            mobile = input_email.text.toString().trim()
            email = ""
        }else{
            email = input_email.text.toString().trim()
            mobile = ""
        }
        if (Utilz.isInternetConnected(this)) {
            if (input_email.text.toString().trim().equals("")) Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show()
            else if (input_password.text.toString().trim().equals("")) Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            else {
                callLoginApi(email, mobile, input_password.text.toString())

            }
        } else {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun callLoginApi(email: String, moble: String, password: String) {
        Utilz.showDailog(this, resources.getString(R.string.pleaewait))
        retrofitDataProvider!!.userLogin(email, moble, password, object : DownlodableCallback<LoginModel> {
            override fun onSuccess(result: LoginModel?) {

                Utilz.dismissProgressDialog()
                if (result!!.status.equals("true")) {
                    if (result.data!![0].status.equals("1")) {
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USERID, result.data!![0].id)
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USEREMAIL, result.data!![0].email)
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USERNAME, result.data!![0].name)
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USERLASTNAME, result.data!![0].last_name)
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USEREADRESS, result.data!![0].address)
                        ClsGeneral.setPreferences(this@LoginActivity, AppConstants.MOBILE, result.data!![0].mobile)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Wrong credentials", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(error: String?) {Utilz.dismissProgressDialog()}

            override fun onUnauthorized(errorNumber: Int) {Utilz.dismissProgressDialog()}
        })
    }

    private fun validmobile(p0: CharSequence?) {

        try {
            if (java.lang.Double.parseDouble(p0.toString()) > 0) {
                 status = true
            }
        } catch (e: NumberFormatException) {
            status  = false
        }
    }
}