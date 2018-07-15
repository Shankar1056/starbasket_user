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
import apextechies.starbasket.adapter.CartAdapter
import apextechies.starbasket.adapter.CombinationAdapter
import apextechies.starbasket.adapter.ViewPagerAdapter
import apextechies.starbasket.fragment.CategoryFragment
import apextechies.starbasket.fragment.TextFragment
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.ApiUrl
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.cart_drawer.*
import kotlinx.android.synthetic.main.dialog_combination.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONException
import org.json.JSONObject

class ProductDetailsActivity: BaseActivity(), CombinationAdapter.OnItemClickListener, OnCartListener, View.OnClickListener  {

    private var product: ProductDataModel? = null
    private var mAdapter: ViewPagerAdapter? = null
    private var dialog: Dialog? = null
    private var mCartAdapter: CartAdapter? = null
    private var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
        product = getIntent().getParcelableExtra<ProductDataModel>("list")
        supportActionBar!!.setTitle(product!!.name)

        if (!product!!.unitdetails!![0].discount!!.isEmpty()) {
            (findViewById(R.id.tv_off) as TextView).setText(product!!.unitdetails!![0].discount)
        } else {
            tv_off.setVisibility(View.GONE)
        }

        Picasso.with(this)
                .load(product!!.image)
                .fit()
                .centerInside()
                .into(findViewById(R.id.iv_image) as ImageView)

        (findViewById(R.id.tv_title) as TextView).setText(product!!.name)

        tv_combination.setOnClickListener(this)


        tv_dec_quantity.setOnClickListener(this)

        mAdapter = ViewPagerAdapter(supportFragmentManager)
        val descVP = findViewById(R.id.vp_content) as ViewPager
        descVP.adapter = mAdapter

        (findViewById(R.id.tl_product) as TabLayout).setupWithViewPager(descVP)

        mCartAdapter = CartAdapter(this)
        val cartRV = findViewById(R.id.rv_cart) as RecyclerView
        cartRV.adapter = mCartAdapter
        cartRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        init()
        setQuantity(false)

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

        var productDataModel= QuantityModel()
        retrofitDataProvider!!.cartItem("1", object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                for (i in 0 until result!!.data!!.size) {
                    mCartAdapter!!.addItem(result.data!![i])
                    if (productDataModel != null) {
                        productDataModel.setQty(result.data[i].quantity!!)
                    }

                }

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
                product!!.quantity = (Integer.parseInt(product!!.quantity) - 1).toString()
                setQuantity(true)
            }

            R.id.tv_inc_quantity -> {
                product!!.quantity = (Integer.parseInt(product!!.quantity) + 1).toString()
                setQuantity(true)
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
        combinationRV.adapter = CombinationAdapter(product, this)
    }

    private fun setQuantity(updateToServer: Boolean) {
        tv_quantity.text = product!!.quantity

        if (Integer.parseInt(product!!.quantity) < 1) {
            tv_dec_quantity.setVisibility(View.INVISIBLE)
            tv_dec_quantity.setVisibility(View.INVISIBLE)
        } else {
            tv_dec_quantity.setVisibility(View.VISIBLE)
            tv_dec_quantity.setVisibility(View.VISIBLE)
        }

        if (updateToServer) {
            retrofitDataProvider!!.addUpdaDteCart("1", product!!.id, product!!.quantity, product!!.name, product!!.unitdetails!![0].selling_price,"1", product!!.unitdetails!![0].unit, object : DownlodableCallback<CartModel> {
                override fun onSuccess(result: CartModel?) {

                    for (i in 0 until result!!.data!!.size) {
                        mCartAdapter!!.addItem(result.data!![i])
                    }
                    if (mCartAdapter!!.getItemCount() > 0) {
                        if (slider.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                            slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED)
                        }
                        tv_total_amount.text = mCartAdapter!!.totalPrice.toString()
                        tv_cart_count.setText(mCartAdapter!!.getItemCount())
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
    }

    private fun init() {
        tv_price.text = product!!.unitdetails!![0].selling_price
        if (product!!.selectedIndes!! > -1) {
            tv_combination.setText(product!!.unitdetails!!?.get(0).unit)
        } else {
            tv_combination.setVisibility(View.GONE)
        }

        Picasso.with(this)
                .load(product!!.image)
                .fit()
                .centerInside()
                .into(findViewById(R.id.iv_image) as ImageView)
    }

    override fun onItemClick(position: Int) {
        if (dialog!!.isShowing()) {
            dialog!!.dismiss()
        }

        product!!.selectedIndes = position

        init()
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
       retrofitDataProvider!!.addUpdaDteCart("1", product!!.id, product!!.quantity, product!!.name, product!!.unitdetails!![0].selling_price,"1", product!!.unitdetails!![0].unit, object : DownlodableCallback<CartModel> {
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


}
