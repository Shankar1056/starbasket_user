package apextechies.starbasket.activity

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.dialog.DateTimeDialog
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.android.synthetic.main.activity_checkout.*
import java.lang.Exception

class CheckoutActivity: AppCompatActivity(), PaymentResultWithDataListener {

    var retrofitDataProvider: RetrofitDataProvider?= null

    private val RC_ADDRESS = 1
    private val RC_ORDER = 2
    private val RC_CHECKOUT = 3
    private val RC_PAYMENT = 4
    var EXTRA_DATA = "extra_data"
    var list = ArrayList<CartDataModel>()
    var addressId: String?= null

    private var paymentRG: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        retrofitDataProvider = RetrofitDataProvider(this)
        tv_label_address.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivityForResult(intent, RC_ADDRESS)


        }
        list = intent.getParcelableArrayListExtra("list")

        totalItems.text = "Total Item "+intent.getIntExtra("itemcount", 0)
        tv_item.text = "\u20B9 "+intent.getIntExtra("price", 0)
        tv_sub_total.text = "\u20B9 "+intent.getIntExtra("price", 0)
        tv_grand_total.text = "\u20B9 "+intent.getIntExtra("price", 0)

        tv_proceed_to_pay.setOnClickListener {
            doPayment()
        }
    }

    private fun doPayment() {
        val data: CheckoutModel = getData()
        retrofitDataProvider!!.paymant(data, object : DownlodableCallback<CommonModel> {
            override fun onSuccess(result: CommonModel?) {

                if (result!!.data.equals("success")){
                    Toast.makeText(this@CheckoutActivity, ""+result.data, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })

    }

    private fun getData(): CheckoutModel {
        val checkoutModel= CheckoutModel()
        val checkoutlistModel = ArrayList<ProductDetailsModel>()
        checkoutModel.address_id = addressId
        checkoutModel.coupon_code = ""
        checkoutModel.delivery_charge = ""
        checkoutModel.discount = ""
        checkoutModel.payment_type = "COD"
        checkoutModel.price = ""+intent.getIntExtra("price", 0)
        checkoutModel.total_price = ""+intent.getIntExtra("price", 0)
        checkoutModel.user_id = ClsGeneral.getStrPreferences(this, AppConstants.USERID)

        for (i in 0 until list.size){
            checkoutlistModel.add(ProductDetailsModel(list[i].name, list[i].quantity, list[i].price, list[i].varient, list[i].image, list[i].seller_id))

        }
        checkoutModel.order_product_history=(checkoutlistModel)

        return checkoutModel
    }

    override
     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_ADDRESS && resultCode == RESULT_OK) {
            try{
                val address: AddressDataModel? = data!!.getParcelableExtra(EXTRA_DATA)
//                tv_address.text = (data!!.getSerializableExtra(EXTRA_DATA) as AddressDataModel).toString()
                tv_address.text = address!!.address1+","+address!!.address2+","+ address.city+","+address.landmark+","+ address.landmark
                addressId = address.address_id
            }catch (e: Exception){

                e.printStackTrace()
            }

        }
    }


    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
    }

}