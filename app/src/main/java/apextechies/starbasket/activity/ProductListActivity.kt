package apextechies.starbasket.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import apextechies.starbasket.R
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.fragment.CategoryFragment
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.toolbar.*

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
        val group = intent.getParcelableExtra<CategorysSubcatModel>(EXTRA_DATA)
        supportActionBar!!.setTitle(group.name)

        mAdapter = ViewPagerAdapter(supportFragmentManager)

        vp_list.adapter =mAdapter
        tl_list.setupWithViewPager(
                vp_list)

        getSubSubCat(group.id)
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

                    val fView = CategoryFragment(result.data!![i].sub_cat_id!!, result.data!![i].id!!)
                    val view = fView.getView()
                    mAdapter!!.addItem(fView, result.data!![i].name!!)

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
        var productDataModel= QuantityModel()
        retrofitDataProvider!!.cartItem("1", object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                for (i in 0 until result!!.data!!.size) {
                    mCartAdapter!!.addItem(result.data!![i])
                    if (productDataModel != null) {
                        productDataModel.setQty(result.data[i].quantity!!)
                    }

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
        for (i in 0 until response.size) {

                mCartAdapter!!.addItem(response[i])

        }
    }
}