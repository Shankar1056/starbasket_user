package apextechies.starbasket.activity

import android.os.Bundle
import android.preference.Preference
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView


import org.json.JSONException
import org.json.JSONObject

import apextechies.starbasket.R
import apextechies.starbasket.adapter.OrderDetailsAdapter
import apextechies.starbasket.model.OrderDetailsModel
import apextechies.starbasket.model.OrderModel
import apextechies.starbasket.model.UserOrderDataListModel
import apextechies.starbasket.retrofit.ApiUrl
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderDetailsActivity : AppCompatActivity() {
    private var mAdapter: OrderDetailsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_order_details)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Order Details"

        mAdapter = OrderDetailsAdapter()
        rv_order.adapter = mAdapter

       var  mOrder: UserOrderDataListModel? = intent.getParcelableExtra("extra_data")

        tv_order_id.text = (getString(R.string.format_order_id, mOrder!!.transaction_id));
        tv_total.text = "₹ "+mOrder.total_price
        tv_order_status.text = (mOrder.order_status);


        mAdapter!!.addItems(mOrder.varientdetails!!)

        toolbarr.setNavigationOnClickListener {
            finish()
        }
    }



}
