package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.CartModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity: AppCompatActivity(), OnCartListener {

    var retrofitDataProvider: RetrofitDataProvider?= null

    private var mCartAdapter: CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        retrofitDataProvider = RetrofitDataProvider(this)
        mCartAdapter = CartAdapter(this)
        val cartRV = findViewById(R.id.rv_cart) as RecyclerView
        cartRV.layoutManager = LinearLayoutManager(this)
        cartRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        cartRV.adapter = mCartAdapter

        getCartItem()
        tv_checkout.setOnClickListener {
            startActivity(Intent(this@CartActivity, CheckoutActivity::class.java))
        }

    }

    private fun getCartItem() {
        retrofitDataProvider!!.cartItem("1", object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                setValue(result)
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }

    private fun setValue(result: CartModel?) {
        if (result!!.data!!.size>0) {
            mCartAdapter!!.clear()
            for (i in 0 until result!!.data!!.size) {
                mCartAdapter!!.addItem(result.data!![i])
            }

            tv_label_sub_total.text = getString(R.string.format_cart_subtotal, mCartAdapter!!.itemCount)

            ll_empty.visibility = View.GONE
            ll_content.visibility = View.VISIBLE
        }
        else{
            ll_empty.visibility = View.VISIBLE
            ll_content.visibility = View.GONE
        }

    }

    override fun onCartUpdate(item: CartDataModel?) {
        retrofitDataProvider!!.cartItem("1", object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                setValue(result)
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }
}