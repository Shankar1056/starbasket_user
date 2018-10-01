package apextechies.starbasket.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.CommonModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_writeuloadprescription.*
import kotlinx.android.synthetic.main.toolbar.*

class WriteUploadPrecription : AppCompatActivity() {
    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writeuloadprescription)

        retrofitDataProvider = RetrofitDataProvider(this)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Upload Prescription"

        toolbarr.setNavigationOnClickListener {
            finish()
        }

        tv_proceed_to_pay.setOnClickListener {
            if (descriptionET.text.toString().trim().equals("")){
                Toast.makeText(this, "Enter your prescription", Toast.LENGTH_SHORT).show()
            }
            else{
                saveData()
            }
        }
    }

    private fun saveData() {
        retrofitDataProvider!!.insertPrescription(ClsGeneral.getStrPreferences(this, AppConstants.USERID), descriptionET.text.toString(), object : DownlodableCallback<CommonModel> {
            override fun onSuccess(result: CommonModel?) {
                if (result!!.status.equals("true")){
                    Toast.makeText(this@WriteUploadPrecription, ""+result.data, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(error: String?) {   }

            override fun onUnauthorized(errorNumber: Int) {  }

        })
    }
}