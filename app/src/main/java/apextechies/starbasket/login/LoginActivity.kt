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
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception
import java.lang.NumberFormatException

class LoginActivity : AppCompatActivity() {
    var retrofitDataProvider: RetrofitDataProvider? = null
    var mobile = ""
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofitDataProvider = RetrofitDataProvider(this)
        input_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {

                if (validmobile(p0)) {
                    mobile = p0.toString()
                } else {
                    email = p0.toString()
                }
            }
        })

        btn_submit.setOnClickListener {
            if (Utilz.isInternetConnected(this)) {
                if (input_email.text.toString().trim().equals("")) Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show()
                else if (input_password.text.toString().trim().equals("")) Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                else {
                    retrofitDataProvider!!.userLogin(email, mobile, input_password.text.toString(), object : DownlodableCallback<LoginModel> {
                        override fun onSuccess(result: LoginModel?) {

                            if (result!!.status.equals("true")) {
                                if (result.data!![0].status.equals("1")) {
                                    ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USERID, result.data!![0].id)
                                    ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USEREMAIL, result.data!![0].email)
                                    ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USERNAME, result.data!![0].name)
                                    ClsGeneral.setPreferences(this@LoginActivity, AppConstants.USEREADRESS, result.data!![0].address)
                                    ClsGeneral.setPreferences(this@LoginActivity, AppConstants.MOBILE, result.data!![0].mobile)
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    finish()
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, "Wrong credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(error: String?) {}

                        override fun onUnauthorized(errorNumber: Int) {}
                    })

                }
            } else {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
            }

        }

        forgotpassword.setOnClickListener {
            //            startActivity(Intent(this, ForgotPassword::clas.java))
        }

        signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun validmobile(p0: CharSequence?): Boolean {
        try {
            if (Integer.parseInt(p0.toString()) > 0) {
                return true
            }
        } catch (e: NumberFormatException) {
            return false
        }
        return false
    }
}