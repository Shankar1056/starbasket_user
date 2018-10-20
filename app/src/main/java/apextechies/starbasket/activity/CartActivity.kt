package apextechies.starbasket.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.CartModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.toolbar.*

class CartActivity : AppCompatActivity(), OnCartListener {

    var retrofitDataProvider: RetrofitDataProvider? = null
    var totalprice: Int = 0

    private var mCartAdapter: CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "My Cart"

        retrofitDataProvider = RetrofitDataProvider(this)
        mCartAdapter = CartAdapter(this)
        val cartRV = findViewById(R.id.rv_cart) as RecyclerView
        cartRV.layoutManager = LinearLayoutManager(this)
        cartRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        cartRV.adapter = mCartAdapter

        getCartItem()
        tv_checkout.setOnClickListener {
            startActivity(Intent(this@CartActivity, CheckoutActivity::class.java)
                    .putExtra("itemcount", mCartAdapter!!.itemCount)
                    .putExtra("price", totalprice)
                    .putParcelableArrayListExtra("list", mCartAdapter!!.list()))
        }

        toolbarr.setNavigationOnClickListener {
            finish()
        }

        btn_home.setOnClickListener {
            startActivity(Intent(this@CartActivity, MainActivity::class.java))
            finish()
        }

    }

    private fun getCartItem() {
        retrofitDataProvider!!.cartItem(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<CartModel> {
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
        if (result!!.data!!.size > 0) {
            mCartAdapter!!.clear()
            for (i in 0 until result!!.data!!.size) {
                mCartAdapter!!.addItem(result.data!![i])
            }

            tv_label_sub_total.text = getString(R.string.format_cart_subtotal, mCartAdapter!!.itemCount)

            ll_empty.visibility = View.GONE
            ll_content.visibility = View.VISIBLE
            ClsGeneral.setPreferences(this@CartActivity, AppConstants.CARTCOUNT, result!!.data!!.size.toString())
            getTotalPrice(result.data)
        } else {
            ll_empty.visibility = View.VISIBLE
            ll_content.visibility = View.GONE
        }

    }

    private fun getTotalPrice(data: ArrayList<CartDataModel>?) {

        for (i in 0 until data!!.size) {
            totalprice = totalprice + Integer.parseInt(data[i].price)
        }
        tv_grand_total.text = totalprice.toString()
    }

    override fun onCartUpdate(item: CartDataModel?) {
        if (item!!.quantity.equals("0")) {
            showDilog(item)
        }else{
            updateart(item)
        }

    }

    private fun updateart(item: CartDataModel) {
        retrofitDataProvider!!.addUpdaDteCart(ClsGeneral.getStrPreferences(this, AppConstants.USERID), item!!.product_id, item.quantity, item.name, item.price, "1", item.varient, item.seller_id, object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                getCartItem()
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }
        })
    }

    private fun showDilog(item: CartDataModel) {
        val builder = AlertDialog.Builder(this)

        if (title != null) builder.setTitle(title)

        builder.setMessage("Are you sure you want to delete this item from your cart?")
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                updateart(item)
            }

        })
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}