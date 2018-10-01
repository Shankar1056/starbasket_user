package apextechies.starbasket.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.R.string.submit
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.model.CommonModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasket.sendmailinbackground.LongOperation
import kotlinx.android.synthetic.main.activityforgotpasswor.*

class ForgotPassword : AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider? = null
    var verify = false
    var type = "generateotp"
    var opreation: LongOperation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityforgotpasswor)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        opreation = LongOperation()
        retrofitDataProvider = RetrofitDataProvider(this)

        tv_proceed_to_pay.setOnClickListener {
            if (email.text.toString().trim().equals("")) {
                Toast.makeText(this, "Enter your email id", Toast.LENGTH_SHORT).show()
            }
            if (verify) {
                type = "submit"
                if (otp.text.toString().trim().equals("")) {
                    Toast.makeText(this, "Enter OTP sent to your email id", Toast.LENGTH_SHORT).show()
                }
                if (password.text.toString().trim().equals("")) {
                    Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show()
                }
                else{
                    verifyOtp(email.text.toString().trim(), password.text.toString().trim(), otp.text.toString(), type)
                }
            } else {
                verifyOtp(email.text.toString().trim(), password.text.toString().trim(), otp.text.toString(), type)
            }
        }

    }

    private fun verifyOtp(emil: String, pasword: String, ootp: String, type: String) {
        if (Utilz.isInternetConnected(this)) {
            Utilz.showProgress(this, resources.getString(R.string.pleaewait))
        retrofitDataProvider!!.forgotPassword(emil, ootp, pasword,  type, object : DownlodableCallback<CommonModel> {
            override fun onSuccess(result: CommonModel?) {

                Utilz.dismissProgressDialog()
                if (result!!.status.equals("true")) {
                    if (type.equals("generateotp")) {

                        opreation!!.emailOtp(emil, "" + result.data)
                        opreation!!.execute()
                        email.isClickable = false
                        email.isFocusable = false
                        email.alpha = 0.4f
                        verify = true
                        password.visibility = View.VISIBLE
                        otp.visibility = View.VISIBLE
                        showMessageDialog()
                    } else {
                        Toast.makeText(this@ForgotPassword, ""+result.data, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            override fun onFailure(error: String?) {
                Utilz.dismissProgressDialog()
            }

            override fun onUnauthorized(errorNumber: Int) {
                Utilz.dismissProgressDialog()
            }
        })
        }

        else {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showMessageDialog() {
        Utilz.displayMessageAlert(resources.getString(R.string.otpsentmessage), this@ForgotPassword)
    }
}