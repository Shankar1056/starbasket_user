package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import apextechies.starbasket.R
import apextechies.starbasket.R.id.ll_empty
import apextechies.starbasket.adapter.OrderAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.CheckoutModel
import apextechies.starbasket.model.OrderModel
import apextechies.starbasket.model.UserOrderDataListModel
import apextechies.starbasket.model.UserOrderListModel
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener {
     var mAdapter: OrderAdapter? = null
    var retrofitDataProvider: RetrofitDataProvider?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_order)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "My Order"
        retrofitDataProvider = RetrofitDataProvider(this)

        mAdapter = OrderAdapter(this)
        val orderRV = findViewById<View>(R.id.rv_order) as RecyclerView
        orderRV.adapter = mAdapter

        ll_empty!!.visibility = View.GONE



        retrofitDataProvider!!.userOrderList(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<UserOrderListModel> {
            override fun onSuccess(result: UserOrderListModel?) {
                if (result!!.data!!.size > 0) {
                    for (i in 0 until result.data!!.size) {
                        mAdapter!!.addItem(result.data!![i]);
                    }
                    ll_empty.setVisibility(View.GONE);
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                }

            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })

        toolbarr.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onItemClick(item: UserOrderDataListModel) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        intent.putExtra("extra_data", item);
        startActivity(intent)
    }
}
