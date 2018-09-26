package apextechies.starbasket.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.activity.MainActivity
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.model.LoginModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*

class SignUpActivity: AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "SignUp"

        retrofitDataProvider = RetrofitDataProvider(this)

        btn_submit.setOnClickListener {
            if (Utilz.isInternetConnected(this)){
            if (input_name.text.toString().trim().equals("")) Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            else if (input_email.text.toString().trim().equals("")) Toast.makeText(this, "Enter your email id", Toast.LENGTH_SHORT).show()
            else if (input_mobile.text.toString().trim().equals("")) Toast.makeText(this, "Enter your mobile id", Toast.LENGTH_SHORT).show()
            else if (input_password.text.toString().trim().equals("")) Toast.makeText(this, "Enter your passwordd", Toast.LENGTH_SHORT).show()
            else{

                retrofitDataProvider!!.userSignup(input_name.text.toString(), input_email.text.toString(), input_password.text.toString(), input_mobile.text.toString(),
                        "address", "device_token", object : DownlodableCallback<LoginModel> {
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
            } else {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        toolbarr.setNavigationOnClickListener {
            finish()
        }

    }
}