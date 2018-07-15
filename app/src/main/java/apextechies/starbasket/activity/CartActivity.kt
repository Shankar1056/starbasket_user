package apextechies.starbasket.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import apextechies.starbasket.R
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.CartModel

class CartActivity: AppCompatActivity(), OnCartListener {


    private var mAdapter: CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        mAdapter = CartAdapter(this)
        val cartRV = findViewById(R.id.rv_cart) as RecyclerView
        cartRV.layoutManager = LinearLayoutManager(this)
        cartRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        cartRV.adapter = mAdapter
    }

    override fun onCartUpdate(item: CartDataModel?) {

    }
}