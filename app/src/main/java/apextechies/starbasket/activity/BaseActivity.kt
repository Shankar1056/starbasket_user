package apextechies.starbasket.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.Preference
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast


import org.json.JSONObject

import apextechies.starbasket.R
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.login.LoginActivity
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 13 Mar 2017 at 6:38 PM
 */

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    private var mCartHandler: Handler? = null
    private var cartCountTV: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mCartHandler = Handler()
    }

    override fun onStart() {
        super.onStart()

        if (cartCountTV != null) {
            updateCartCount(cartCount)
        }
    }

    protected fun updateCartCount(count: Int) {
        cartCount = count
        if (cartCount > 0) {
            cartCountTV!!.visibility = View.VISIBLE
            cartCountTV!!.text = cartCount.toString()
        } else {
            cartCountTV!!.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.cart_item_count)

        cartCountTV = item.actionView.findViewById<View>(R.id.tv_notif_count) as TextView
        updateCartCount(cartCount)

        val rotation = AnimationUtils.loadAnimation(this, R.anim.shake)
        mCartHandler!!.postDelayed(object : Runnable {
            override fun run() {
                cartCountTV!!.startAnimation(rotation)
                mCartHandler!!.postDelayed(this, 5000)
            }
        }, 5000)

        item.actionView.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    /*final void makePayment(double amount, String orderId) {
		Checkout co = new Checkout();
		Preference preference = new Preference(this);

		try {
			JSONObject options = new JSONObject();
			options.put("name", "Quikmart");
			options.put("description", "Quikmart payment");
			options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
			options.put("currency", "INR");
			options.put("amount", amount * 100);
			options.put("order_id", orderId);

			JSONObject preFill = new JSONObject();
			preFill.put("email", preference.getEmail());
			preFill.put("contact", preference.getMobile());

			options.put("prefill", preFill);

			co.open(this, options);
		} catch (Exception e) {
			Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.e(TAG, e.getMessage(), e);
		}
	}*/

    override fun onClick(v: View) {
        when (v.id) {

            R.id.action_cart -> {
                if (ClsGeneral.getStrPreferences(this@BaseActivity, AppConstants.USERID).equals("")) {
                    startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(Intent(this, CartActivity::class.java))
                }
            }

            R.id.btn_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        var EXTRA_DATA = "extra_data"
        protected val IS_HOME = "is_home"
        private val TAG = "BaseActivity"

        private var cartCount = 0
    }
}
