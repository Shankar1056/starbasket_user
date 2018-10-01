package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import apextechies.starbasket.R
import apextechies.starbasket.adapter.OrderAdapter
import apextechies.starbasket.adapter.PrescriptionAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener, PrescriptionAdapter.OnPreItemClickListener {
    override fun onItemClick(item: PrescriptionDataModel?) {

    }

    var mAdapter: OrderAdapter? = null
     var mPreAdapter: PrescriptionAdapter? = null
    var retrofitDataProvider: RetrofitDataProvider?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_order)
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "My Order"
        retrofitDataProvider = RetrofitDataProvider(this)

        mAdapter = OrderAdapter(this)
        mPreAdapter = PrescriptionAdapter(this)
        rv_order.adapter = mAdapter
        rv_prescription.adapter = mPreAdapter

        rv_prescription.layoutManager = LinearLayoutManager(this)
        rv_order.layoutManager = LinearLayoutManager(this)

        ll_empty!!.visibility = View.GONE
        rv_order.isNestedScrollingEnabled = false
        rv_prescription.isNestedScrollingEnabled = false



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

                getPrescription()

            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })

        toolbarr.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getPrescription() {
        retrofitDataProvider!!.getPrescription(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<PrescriptionModel> {
            override fun onSuccess(result: PrescriptionModel?) {
                if (result!!.data!!.size > 0) {
                    for (i in 0 until result.data!!.size) {
                        mPreAdapter!!.addItem(result.data!![i]);
                    }
                } else {
                }

            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) {  }

        })
    }

    override fun onItemClick(item: UserOrderDataListModel) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        intent.putExtra("extra_data", item);
        startActivity(intent)
    }
}
