package apextechies.starbasket.activity

import android.app.Dialog
import android.nfc.NfcAdapter
import android.os.Bundle
import android.preference.Preference
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.R
import apextechies.starbasket.R.drawable.cart
import apextechies.starbasket.R.id.*
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.adapter.CombinationAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.fragment.CategoryFragment
import apextechies.starbasket.fragment.TextFragment
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import com.rollbar.android.Rollbar.init
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.cart_drawer.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ProductDetailsActivity: BaseActivity(), CombinationAdapter.OnItemClickListener, OnCartListener, View.OnClickListener  {

    private var product = ArrayList<ProductDataModel>()
    private var mAdapter: ViewPagerAdapter? = null
    private var dialog: Dialog? = null
    private var mCartAdapter: CartAdapter? = null
    private var retrofitDataProvider: RetrofitDataProvider?= null
    private var cartlist = ArrayList<CartDataModel>()
    private  var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
        product = getIntent().getParcelableArrayListExtra("list")
        pos = intent.getIntExtra("pos", 0)
        supportActionBar!!.setTitle(product[pos].name)

        if (!product[pos].unitdetails!![0].discount!!.isEmpty()) {
            (findViewById(R.id.tv_off) as TextView).setText(product[pos].unitdetails!![0].discount)
        } else {
            tv_off.setVisibility(View.GONE)
        }

        Picasso.with(this)
                .load(product[pos].image)
                .fit()
                .centerInside()
                .into(findViewById(R.id.iv_image) as ImageView)

        (findViewById(R.id.tv_title) as TextView).setText(product[pos].name)

        tv_combination.setOnClickListener(this)


        tv_dec_quantity.setOnClickListener(this)
        tv_inc_quantity.setOnClickListener(this)

        mAdapter = ViewPagerAdapter(supportFragmentManager)
        val descVP = findViewById(R.id.vp_content) as ViewPager
        descVP.adapter = mAdapter

        (findViewById(R.id.tl_product) as TabLayout).setupWithViewPager(descVP)

        mCartAdapter = CartAdapter(this)
        val cartRV = findViewById(R.id.rv_cart) as RecyclerView
        cartRV.adapter = mCartAdapter
        cartRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        init()


       /* ApiTask.builder(this)
                .setGET()
                .setUrl(ApiUrl.GET_PRODUCT_DETAILS + product.getProductId())
                .setProgressMessage(R.string.loading_product_details)
                .setRequestCode(RC_DETAILS)
                .setResponseListener(this)
                .exec()*/


    }

    override fun onStart() {
        super.onStart()

      getCartItem()
    }

    private fun getCartItem() {
        retrofitDataProvider!!.cartItem(ClsGeneral.getStrPreferences(this, AppConstants.USERID), object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {

                mCartAdapter!!.clear()
                for (i in 0 until result!!.data!!.size) {
                    mCartAdapter!!.addItem(result.data!![i])

                    if (result != null) {
                        cartlist = result.data!!
                    }
                }
                setQuantity(false, "")

                if (mCartAdapter!!.getItemCount() > 0) {
                    if (slider.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                        slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
                    }
                    tv_total_amount.text = mCartAdapter!!.totalPrice.toString()
                    tv_cart_count.text = mCartAdapter!!.getItemCount().toString()
                } else {
                    slider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN)
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_combination -> showCombinationDialog()

            R.id.tv_dec_quantity -> {
                if (tv_quantity.text.toString().trim().equals("") || tv_quantity.text.toString().trim().equals("0")){}
                else {
                    cartlist!![product[pos].selectedIndes!!].quantity = (Integer.parseInt(cartlist!![product[pos].selectedIndes!!].quantity) - 1).toString()
                    setQuantity(true, cartlist!![product[pos].selectedIndes!!].quantity)
                }
            }

            R.id.tv_inc_quantity -> {
                if (tv_quantity.text.toString().trim().equals("") || tv_quantity.text.toString().trim().equals("0"))
                {
                    if (cartlist!=null) {
                        tv_quantity.text = "1"
                    }
                        setQuantity(true, "1")
                }else{
                    cartlist!![product[pos].selectedIndes!!].quantity = (Integer.parseInt(tv_quantity.text.toString()) + 1).toString()
                    setQuantity(true, cartlist!![product[pos].selectedIndes!!].quantity)
                }


            }


        }
    }

    private fun showCombinationDialog() {
        dialog = Dialog(this, R.style.AppThemeDialog)
        dialog!!.setContentView(R.layout.dialog_combination)
        dialog!!.show()
        val combinationRV = dialog!!.findViewById(R.id.rv_combination) as RecyclerView
        combinationRV.layoutManager = LinearLayoutManager(this)
        combinationRV.setHasFixedSize(true)
        combinationRV.adapter = CombinationAdapter(product[pos], this)
    }

    private fun setQuantity(updateToServer: Boolean, quan: String) {
        if (cartlist!=null) {
            try {
                tv_quantity.text = cartlist!![product[pos].selectedIndes!!].quantity
                if (Integer.parseInt(cartlist!![product[pos].selectedIndes!!].quantity) < 1) {
                    tv_dec_quantity.setVisibility(View.INVISIBLE)
                } else {
                    tv_dec_quantity.setVisibility(View.VISIBLE)
                }
                tv_dec_quantity.setVisibility(View.VISIBLE)
            }catch (e: Exception){

            }
        }

        if (updateToServer) {
            retrofitDataProvider!!.addUpdaDteCart(ClsGeneral.getStrPreferences(this, AppConstants.USERID), product[pos]!!.id, quan, product[pos]!!.name, product[pos]!!.unitdetails!![0].selling_price,"1", product[pos]!!.unitdetails!![0].varient, object : DownlodableCallback<CartModel> {
                override fun onSuccess(result: CartModel?) {

                    mCartAdapter!!.clear()
                    for (i in 0 until result!!.data!!.size) {
                        mCartAdapter!!.addItem(result.data!![i])
                    }
                    if (mCartAdapter!!.getItemCount() > 0) {
                        if (slider.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                            slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
                        }
                        tv_total_amount.text = mCartAdapter!!.totalPrice.toString()
                       // tv_cart_count.setText(mCartAdapter!!.getItemCount())
                    } else {
                        slider.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN)
                    }

                    getCartItem()
                }

                override fun onFailure(error: String?) {
                }

                override fun onUnauthorized(errorNumber: Int) {
                }
            })
        }
    }

    private fun init() {
        tv_price.text = product[pos]!!.unitdetails!![product[pos].selectedIndes!!].selling_price
        if (product[pos]!!.selectedIndes!! > -1) {
            tv_combination.setText(product[pos]!!.unitdetails!!.get(product[pos].selectedIndes!!).varient)
        } else {
            tv_combination.setVisibility(View.GONE)
        }

        Picasso.with(this)
                .load(product[pos].image)
                .fit()
                .centerInside()
                .into(findViewById(R.id.iv_image) as ImageView)
    }

    override fun onItemClick(position: Int) {
        if (dialog!!.isShowing()) {
            dialog!!.dismiss()
        }

        product[pos].selectedIndes = position

        init()
        setQuantity(false, "")
    }


    @Throws(JSONException::class)
    private fun parseDetails(response: JSONObject) {
        val `object` = response.getJSONObject("data").getJSONArray("products").getJSONObject(0)
        val about = `object`.optString("product_about")
        val ingredients = `object`.optString("product_ingredients")
        val nutritional = `object`.optString("product_nutritional")

        if (!about.isEmpty()) {
            mAdapter!!.addItem(TextFragment.newInstance(about), "About")
        }

        if (!ingredients.isEmpty()) {
            mAdapter!!.addItem(TextFragment.newInstance(ingredients), "Ingredients")
        }

        if (!nutritional.isEmpty()) {
            mAdapter!!.addItem(TextFragment.newInstance(nutritional), "Nutritional Facts")
        }
    }

    fun onFailure(requestCode: Int, savedData: Bundle) {

    }

    @Throws(JSONException::class)
    private fun parseCartItems(response: JSONObject) {
      /*  mCartAdapter.clear()
        val data = response.getJSONObject("data").getJSONArray("cart_items")
        for (i in 0 until data.length()) {
            mCartAdapter.addItem(CartItemModel(data.getJSONObject(i)))
        }
        updateCartCount(mCartAdapter.getItemCount())

        if (mCartAdapter.getItemCount() > 0) {
            if (sliderSUPL.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                sliderSUPL.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
            }
            tv_total_amount.setText(getString(R.string.format_price, mCartAdapter.getTotalPrice()))
            tv_cart_count.setText(String.valueOf(mCartAdapter.getItemCount()))
        } else {
            sliderSUPL.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN)
        }*/
    }

   override fun onBackPressed() {
     super.onBackPressed()
    }

   override fun onCartUpdate(item: CartDataModel) {
       retrofitDataProvider!!.addUpdaDteCart( ClsGeneral.getStrPreferences(this, AppConstants.USERID),item!!.product_id, item!!.quantity, item!!.name, item.price,"1", item.varient, object : DownlodableCallback<CartModel> {
           override fun onSuccess(result: CartModel?) {
               mCartAdapter!!.clear()
               for (i in 0 until result!!.data!!.size) {
                   mCartAdapter!!.addItem(result.data!![i])
               }
               setQuantity(false, "")

           }

           override fun onFailure(error: String?) {
           }

           override fun onUnauthorized(errorNumber: Int) {
           }
       })
    }


}
