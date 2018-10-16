package apextechies.starbasket.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.activity.MainActivity
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.model.LoginModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.IllegalStateException

class SignUpActivity: AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener{
    private val RC_SIGN_IN = 7

    private var mGoogleApiClient: GoogleApiClient? = null
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "SignUp"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        retrofitDataProvider = RetrofitDataProvider(this)
        gplus_sign_in.setOnClickListener {
            ClsGeneral.setBoolean(this, "islogout", false)
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        btn_submit.setOnClickListener {
            if (Utilz.isInternetConnected(this)){
            if (input_name.text.toString().trim().equals("")) Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            else if (input_lastname.text.toString().trim().equals("")) Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show()
            else if (input_email.text.toString().trim().equals("")) Toast.makeText(this, "Enter your email id", Toast.LENGTH_SHORT).show()
            else if (input_mobile.text.toString().trim().equals("")) Toast.makeText(this, "Enter your mobile id", Toast.LENGTH_SHORT).show()
            else if (input_password.text.toString().trim().equals("")) Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
            else if (input_confirmpassword.text.toString().trim().equals("")) Toast.makeText(this, "Enter Confirm password", Toast.LENGTH_SHORT).show()
            else if (!input_confirmpassword.text.toString().trim().equals(input_password.text.toString().trim())) Toast.makeText(this, "Password & Confirm password are different", Toast.LENGTH_SHORT).show()
            else{

               callLoginApi(input_name.text.toString(), input_lastname.text.toString(), input_email.text.toString(), input_password.text.toString(), input_mobile.text.toString())

            }
            } else {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        toolbarr.setNavigationOnClickListener {
            finish()
        }


        gplus_sign_in.setSize(SignInButton.SIZE_STANDARD)
        gplus_sign_in.setScopes(gso.scopeArray)

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

                callLoginApi(acct!!.displayName!!, "", acct.email!!, "gplus", "")

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

    private fun callLoginApi(name: String, lastname: String, emil: String, password: String, mobile: String) {
        retrofitDataProvider!!.userSignup(name, lastname, emil, password, mobile, "address", "device_token", object : DownlodableCallback<LoginModel> {
            override fun onSuccess(result: LoginModel?) {

                if (result!!.status.equals("true")) {
                    if (result.data!![0].status.equals("1")) {
                        ClsGeneral.setPreferences(this@SignUpActivity, AppConstants.USERID, result.data!![0].id)
                        ClsGeneral.setPreferences(this@SignUpActivity, AppConstants.USEREMAIL, result.data!![0].email)
                        ClsGeneral.setPreferences(this@SignUpActivity, AppConstants.USERNAME, result.data!![0].name)
                        ClsGeneral.setPreferences(this@SignUpActivity, AppConstants.USEREADRESS, result.data!![0].address)
                        ClsGeneral.setPreferences(this@SignUpActivity, AppConstants.MOBILE, result.data!![0].mobile)
                        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@SignUpActivity, result.msg.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(error: String?) {}

            override fun onUnauthorized(errorNumber: Int) {}
        })
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

}