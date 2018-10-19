package apextechies.starbasket.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import apextechies.starbasket.R
import apextechies.starbasket.R.string.name
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.fragment.CategoryFragment
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.cart_drawer.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONException

class ProductListActivity: AppCompatActivity(), CategoryFragment.OnProductListener, OnCartListener {
    var mAdapter: ViewPagerAdapter?= null
    private var mCartAdapter: CartAdapter? = null
    private var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
        mCartAdapter = CartAdapter(this)
        val bundle = intent.extras

        supportActionBar!!.setTitle(intent.getStringExtra("name"))
        mAdapter = ViewPagerAdapter(supportFragmentManager)

        vp_list.adapter =mAdapter
        tl_list.setupWithViewPager(
                vp_list)

        getSubSubCat(intent.getStringExtra("id"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun getSubSubCat(id: String?) {
       retrofitDataProvider!!.subSubCategory(id, object : DownlodableCallback<SubSubCategory> {
            override fun onSuccess(result: SubSubCategory?) {
                for (i in 0 until result!!.data!!.size) {

                    if (result.data!![i].status.equals("1")) {
                        val fView = CategoryFragment(result.data!![i].sub_cat_id!!, result.data!![i].id!!)
                        mAdapter!!.addItem(fView, result.data!![i].name!!)

                    }
                }

            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })

    }


    override fun onStart() {
        super.onStart()
        retrofitDataProvider!!.cartItem( ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                for (i in 0 until result!!.data!!.size) {
                    mCartAdapter!!.addItem(result.data!![i])

                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }
        override fun onCartUpdate(item: CartDataModel?) {

    }
    override fun onCartUpdate(response: ArrayList<CartDataModel>) {
        mCartAdapter!!.clear()
        for (i in 0 until response.size) {

              //  mCartAdapter!!.addItem(response[i])

        }
       // updateCartCount(mCartAdapter!!.getItemCount())
        if (mCartAdapter!!.getItemCount() > 0) {
            if (slider.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
            }
            tv_total_amount.setText(getString(R.string.format_price, mCartAdapter!!.totalPrice))
            tv_cart_count.setText(""+(mCartAdapter!!.getItemCount()))
        } else {
            slider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN)
        }
    }
}