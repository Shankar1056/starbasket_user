package apextechies.starbasket.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import apextechies.starbasket.R
import apextechies.starbasket.activity.FilterActivity
import apextechies.starbasket.activity.ProductDetailsActivity
import apextechies.starbasket.adapter.ProductListAdapter
import apextechies.starbasket.common.ClsGeneral
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import apextechies.starbasketseller.common.AppConstants
import kotlinx.android.synthetic.main.fragment_category.*


@SuppressLint("ValidFragment")
class CategoryFragment(private val sub_cat_id: String, private val id: String) : Fragment(), ProductListAdapter.OnItemClickListener, ProductListAdapter.OnClickListenr {

    var mItemList = ArrayList<ProductDataModel>()

    override fun onClick(prod_id: String, operation: String) {
        retrofitDataProvider!!.addpdateWishList(ClsGeneral.getStrPreferences(activity, AppConstants.USERID), prod_id, operation, object : DownlodableCallback<WishListMode> {
            override fun onSuccess(result: WishListMode?) {
                if (result != null) {
                    mAdapter!!.removeList()
                    for (i in 0 until result!!.data!!.size) {

                        for (j in 0 until mItemList.size) {
                            if (mItemList[j].id.equals(result.data!![i].prod_id))
                                mItemList[j].iswishlist = true
                        }
                        mAdapter!!.addWishlist(result.data!![i], i)
                    }
                    mAdapter!!.notifyWish()

                }
            }

            override fun onFailure(error: String?) {}

            override fun onUnauthorized(errorNumber: Int) {}
        })
    }


    override fun onItemClick(item: ArrayList<ProductDataModel>, pos: Int) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("list", item)
        intent.putExtra("pos", pos)
        intent.putExtra("hashcart", "no")
        startActivity(intent)
    }


    override fun onQuantityUpdate(id: String?, quantity: String, name: String?, selling_price: String?, image: String, varient: String?, seller_id: String, pos: Int) {

        retrofitDataProvider!!.addUpdaDteCart(ClsGeneral.getStrPreferences(activity, AppConstants.USERID), id, quantity, name, selling_price, "1", varient, seller_id, object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {

                (activity as OnProductListener).onCartUpdate(result!!.data!!)
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
    private var mAdapter: ProductListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        retrofitDataProvider = RetrofitDataProvider(activity)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = ProductListAdapter(this, this)
        prodRV.adapter = mAdapter
        prodRV.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))

        mProductIndex = 0
        isLoading = false
        hasLoadedAll = false

        callProductApi()
        clickListener()


    }

    private fun clickListener() {
        sorting.setOnClickListener {
            CustomDialogClass()
        }

        filter.setOnClickListener {
            startActivity(Intent(activity, FilterActivity::class.java))
        }
    }

    private fun callProductApi() {
        retrofitDataProvider!!.productList(sub_cat_id, id, object : DownlodableCallback<ProductModel> {
            override fun onSuccess(result: ProductModel?) {
                mItemList = result!!.data!!
                mAdapter!!.removeItem()
                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(result.data!![i])
                }

                getWishList()
                getCartItem()
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })

    }

    private fun getWishList() {
        retrofitDataProvider!!.getWishList(ClsGeneral.getStrPreferences(activity, AppConstants.USERID), object : DownlodableCallback<WishListMode> {
            override fun onSuccess(result: WishListMode?) {
                if (result != null) {
                    mAdapter!!.removeList()
                    for (i in 0 until result!!.data!!.size) {

                        for (j in 0 until mItemList.size) {
                            if (mItemList[j].id.equals(result.data!![i].prod_id))
                                mItemList[j].iswishlist = true
                        }
                        mAdapter!!.addWishlist(result.data!![i], i)
                    }
                    mAdapter!!.notifyWish()
                }
            }

            override fun onFailure(error: String?) {}

            override fun onUnauthorized(errorNumber: Int) {}

        })
    }

    private fun getCartItem() {
        retrofitDataProvider!!.cartItem(ClsGeneral.getStrPreferences(activity, AppConstants.USERID), object : DownlodableCallback<CartModel> {
            override fun onSuccess(result: CartModel?) {
                for (i in 0 until result!!.data!!.size) {
                    (activity as OnProductListener).onCartUpdate(result.data!!)
                    mAdapter!!.addCart(result.data[i], i)
                }


            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }

    interface OnProductListener {
        fun onCartUpdate(response: ArrayList<CartDataModel>)
    }

    fun CustomDialogClass() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_filter)
        var rg = dialog.findViewById(R.id.radioSort) as RadioGroup
        var btn = dialog.findViewById(R.id.okBTN) as Button
        btn.setOnClickListener {
            val selectedId = rg.getCheckedRadioButtonId()
            var radioSortButton = dialog.findViewById(selectedId) as RadioButton
            Toast.makeText(activity, radioSortButton.getText(), Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val window = dialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawableResource(R.color.white)
        window.setGravity(Gravity.CENTER)
        dialog.show()
    }
}