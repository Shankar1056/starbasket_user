package apextechies.starbasket.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.CommonModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_changepassword.*
import kotlinx.android.synthetic.main.toolbar.*

class ChangePassword: AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Change Password"

        retrofitDataProvider = RetrofitDataProvider(this)

        toolbarr.setNavigationOnClickListener {
            finish()
        }

        submit.setOnClickListener {
            if (input_password.text.toString().trim().equals(""))
                Toast.makeText(this, "Enter your current password", Toast.LENGTH_SHORT).show()
            else if (input_newpassword.text.toString().trim().equals(""))
                Toast.makeText(this, "Enter new password", Toast.LENGTH_SHORT).show()
            else if (input_confirmpassword.text.toString().trim().equals(""))
                Toast.makeText(this, "confirm new password", Toast.LENGTH_SHORT).show()
            else if (!input_confirmpassword.text.toString().trim().equals(input_newpassword.text.toString().trim()))
                Toast.makeText(this, "password & confirm password are not same", Toast.LENGTH_SHORT).show()
            else
                changePassword()
        }
    }

    private fun changePassword() {
        retrofitDataProvider!!.changePassord(ClsGeneral.getStrPreferences(this, AppConstants.USEREMAIL), "", input_password.text.toString(), input_confirmpassword.text.toString(), object : DownlodableCallback<CommonModel> {
            override fun onSuccess(result: CommonModel?) {
                if (result!!.msg.equals("true")) {
                    Toast.makeText(this@ChangePassword, result!!.data, Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@ChangePassword, result!!.data, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })
    }
}