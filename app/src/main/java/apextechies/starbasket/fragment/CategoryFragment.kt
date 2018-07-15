package apextechies.starbasket.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apextechies.starbasket.R
import apextechies.starbasket.activity.ProductDetailsActivity
import apextechies.starbasket.adapter.ProductListAdapter
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.CartModel
import apextechies.starbasket.model.ProductDataModel
import apextechies.starbasket.model.ProductModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import com.paginate.recycler.LoadingListItemCreator
import com.paginate.recycler.LoadingListItemSpanLookup
import org.json.JSONObject


@SuppressLint("ValidFragment")
class CategoryFragment(private  val sub_cat_id: String, private val id: String) : Fragment(), ProductListAdapter.OnItemClickListener {

    override fun onItemClick(item: ProductDataModel) {
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("list", item)
        startActivity(intent)
    }

    override fun onQuantityUpdate(item: ProductDataModel) {

        retrofitDataProvider!!.addUpdaDteCart("1", item.id, item.quantity, item.name, item.unitdetails!![0].selling_price,"1", item.unitdetails[0].unit, object : DownlodableCallback<CartModel> {
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
    private var retrofitDataProvider: RetrofitDataProvider?= null



    private var mAdapter: ProductListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        retrofitDataProvider = RetrofitDataProvider(activity)
        mAdapter = ProductListAdapter(this)
        val listRV = view as RecyclerView
        listRV.adapter = mAdapter
        listRV.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))

        mProductIndex = 0
        isLoading = false
        hasLoadedAll = false

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       retrofitDataProvider!!.productList(sub_cat_id, id, object : DownlodableCallback<ProductModel> {
            override fun onSuccess(result: ProductModel?) {
               for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(result.data!!.get(i))
                }
            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })


    }

    private inner class CustomLoadingListItemCreator : LoadingListItemCreator {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = activity!!.layoutInflater.inflate(R.layout.loading_row, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

    private inner class CustomLoadingListItemSpanLookup : LoadingListItemSpanLookup {
        override fun getSpanSize(): Int {
            return 1
        }
    }

    interface OnProductListener {
        fun onCartUpdate(response: ArrayList<CartDataModel>)
    }
}