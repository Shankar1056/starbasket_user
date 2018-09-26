package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.dialog.DateTimeDialog
import apextechies.starbasket.model.AddressModel
import apextechies.starbasket.model.CheckoutModel
import apextechies.starbasket.model.DateModel
import apextechies.starbasket.model.TimeModel
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity: AppCompatActivity(), DateTimeDialog.OnDateTimeListener, PaymentResultWithDataListener {


    private val RC_ADDRESS = 1
    private val RC_ORDER = 2
    private val RC_CHECKOUT = 3
    private val RC_PAYMENT = 4
    val EXTRA_DATA = "extra_data"

    private var checkout: CheckoutModel? = null
    private var paymentRG: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        tv_label_address.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivityForResult(intent, RC_ADDRESS)

            /*tv_label_date_time.setOnClickListener {
                val dialog = DateTimeDialog()
                dialog.show(supportFragmentManager, null)

                tv_proceed_to_pay.setOnClickListener {
                    if (isInputValid()) {

                    }
                }
            }*/
        }

        totalItems.text = "4"
        tv_item.text = "\u20B9 200"
        tv_sub_total.text = "\u20B9 200"
        tv_grand_total.text = "\u20B9 200"
    }

    private fun isInputValid(): Boolean {
        if (checkout!!.getAddress() == null) {
            startActivityForResult(Intent(this, AddressActivity::class.java), RC_ADDRESS)
            return false
        }

        // check shipping time
        if (checkout!!.getDate() == null || checkout?.getTime() == null) {
            val dialog = DateTimeDialog()
            dialog.show(supportFragmentManager, null)
            return false
        }

        // check payment method
        if (paymentRG?.getCheckedRadioButtonId() != -1) {
            val index = paymentRG!!.indexOfChild(paymentRG!!.findViewById(paymentRG!!.getCheckedRadioButtonId()))
            checkout!!.setPayment(checkout!!.getPaymentList()[index])
        } else {
            Toast.makeText(this, R.string.select_payment_method, Toast.LENGTH_SHORT).show()
            return false
        }

        return true

    }

    override
     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_ADDRESS && resultCode == RESULT_OK) {
            checkout!!.setAddress(data!!.getSerializableExtra(EXTRA_DATA) as AddressModel)
            tv_address.text = checkout!!.getAddress().toString()
        }
    }

    override fun onDateTime(date: DateModel, time: TimeModel) {
        checkout!!.setDate(date)
        checkout!!.setTime(time)

        tv_date_time.text = date!!.getDate() + ", " + time!!.getTimeSlot()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
    }

}