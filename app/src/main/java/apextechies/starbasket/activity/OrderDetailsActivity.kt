package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


import apextechies.starbasket.R
import apextechies.starbasket.adapter.OrderDetailsAdapter
import apextechies.starbasket.common.Utilz
import apextechies.starbasket.model.CommonModel
import apextechies.starbasket.model.UserOrderDataListModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderDetailsActivity : AppCompatActivity() {
    private var mAdapter: OrderDetailsAdapter? = null
    var retrofitDataProvider: RetrofitDataProvider?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_order_details)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Order Details"

        retrofitDataProvider = RetrofitDataProvider((this))

        mAdapter = OrderDetailsAdapter()
        rv_order.adapter = mAdapter

       var  mOrder: UserOrderDataListModel? = intent.getParcelableExtra("extra_data")

        tv_order_id.text = (getString(R.string.format_order_id, mOrder!!.transaction_id));
        tv_total.text = "₹ "+mOrder.total_price
        tv_order_status.text = Utilz.getStatus(mOrder.order_status);


        mAdapter!!.addItems(mOrder.varientdetails!!)

        toolbarr.setNavigationOnClickListener {
            finish()
        }

        tv_cancel.setOnClickListener {
            val strng = mOrder!!.transaction_id!!.replace("#", "")
            cancelOrder(strng)
        }
    }

    private fun cancelOrder(transaction_id: String?) {

        retrofitDataProvider!!.cancelOrder(transaction_id, object : DownlodableCallback<CommonModel> {
            override fun onSuccess(result: CommonModel?) {
                if (result!!.status.equals("true")){
                    startActivity(Intent(this@OrderDetailsActivity, OrderActivity::class.java))
                    finish()
                }
            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })
    }


}
