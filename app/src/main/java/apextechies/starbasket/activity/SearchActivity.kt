package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import apextechies.starbasket.R
import apextechies.starbasket.adapter.ProductListAdapter
import apextechies.starbasket.adapter.SearchAdapter
import apextechies.starbasket.model.ProductDataModel
import apextechies.starbasket.model.ProductModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class SearchActivity: AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider?= null
    var callApi = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        searchRV.layoutManager = LinearLayoutManager(this)

        retrofitDataProvider = RetrofitDataProvider((this))
        toolbarr.setNavigationOnClickListener {
            finish()
        }

        searchRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (callApi){
                    callSearchApi(s.toString())
                }
            }
        })

    }

    private fun callSearchApi(name: String) {
        retrofitDataProvider!!.getSearchedProduct(name, object : DownlodableCallback<ProductModel> {
            override fun onSuccess(result: ProductModel?) {
                if (result != null) {
                    searchRV.adapter = SearchAdapter(result.data!!, object : SearchAdapter.OnItemClickListener {
                        override fun onItemClick(item: ArrayList<ProductDataModel>, pos: Int) {
                            val intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                            intent.putParcelableArrayListExtra("list", item)
                                    .putExtra("pos", pos)
                            startActivity(intent)
                        }

                    })
                }

            }

            override fun onFailure(error: String?) {
            }

            override fun onUnauthorized(errorNumber: Int) {
            }

        })
    }
}