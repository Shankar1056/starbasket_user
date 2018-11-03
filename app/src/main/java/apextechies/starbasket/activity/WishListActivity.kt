package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.adapter.WishListAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.CartModel
import apextechies.starbasket.model.WishListDataMode
import apextechies.starbasket.model.WishListMode
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activitywishlist.*
import kotlinx.android.synthetic.main.toolbar.*

class WishListActivity : AppCompatActivity(), WishListAdapter.OnItemClickListener, WishListAdapter.OnClickListenr {

    override fun onClick(prod_id: String, operation: String) {
        retrofitDataProvider!!.addpdateWishList(ClsGeneral.getStrPreferences(this, AppConstants.USERID), prod_id, operation, object : DownlodableCallback<WishListMode> {
            override fun onSuccess(result: WishListMode?) {
                if (result != null) {
                    mAdapter!!.removeList()
                    for (i in 0 until result!!.data!!.size) {
                        mAdapter!!.addWishlist(result.data!![i], i)
                    }
                }
            }

            override fun onFailure(error: String?) {}

            override fun onUnauthorized(errorNumber: Int) {}
        })
    }


    override fun onItemClick(item: ArrayList<WishListDataMode>, pos: Int) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("list", item)
        intent.putExtra("pos", pos)
        intent.putExtra("hashcart", "no")
        startActivity(intent)
    }


    override fun onQuantityUpdate(id: String?, quantity: String, name: String?, selling_price: String?, image: String, varient: String?, seller_id: String, pos: Int) {

        retrofitDataProvider!!.addUpdaDteCart(ClsGeneral.getStrPreferences(this, AppConstants.USERID), id, quantity, name, selling_price, "1", varient, seller_id, object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {

            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })
    }

    private var mProductIndex = 0
    private var isLoading = false
    private var hasLoadedAll = false
    private var retrofitDataProvider: RetrofitDataProvider? = null
    private var mAdapter: WishListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitywishlist)

        initWidgit()
    }

    private fun initWidgit() {
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "My WishLis"
        searchRL.visibility = View.GONE
        retrofitDataProvider = RetrofitDataProvider(this)
        mAdapter = WishListAdapter(this, this)
        wishlistRV.adapter = mAdapter
        wishlistRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        mProductIndex = 0
        isLoading = false
        hasLoadedAll = false
        retrofitDataProvider!!.getWishList(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<WishListMode> {
            override fun onSuccess(result: WishListMode?) {
                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(result.data!!.get(i))
                }

            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
        toolbarr.setNavigationOnClickListener {
            finish()
        }

    }


}
