package apextechies.starbasket.activity

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import apextechies.starbasket.R
import apextechies.starbasket.adapter.AllCategoryAdapter
import apextechies.starbasket.model.*
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.toolbar.*

class CategoryActivity: AppCompatActivity(), AllCategoryAdapter.OnItemClickListener {
    private var mAdapter: AllCategoryAdapter?= null
    private var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbarr)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)
      var  mCategory = intent.getParcelableExtra<CategoryDataModel>(EXTRA_DATA) as CategoryDataModel

        supportActionBar!!.title = mCategory.name

        mAdapter = AllCategoryAdapter(this)
        val categoryRV = findViewById(R.id.rv_category) as RecyclerView
        categoryRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        categoryRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        categoryRV.adapter = mAdapter

        for (i in 0 until mCategory!!.subcat!!.size) {
            mAdapter!!.addItem(mCategory!!.subcat!![i])
        }

        /*retrofitDataProvider!!.subCategory(mCategory.id, object : DownlodableCallback<SubCategoryModel> {
            override fun onSuccess(result: SubCategoryModel?) {
                for (i in 0 until result!!.data!!.size) {
                    mAdapter!!.addItem(result.data!![i])
                }
            }

            override fun onFailure(error: String?) { }

            override fun onUnauthorized(errorNumber: Int) { }
        })*/

        toolbarr.setNavigationOnClickListener {
            finish()
        }

        search.visibility = View.VISIBLE
        search.setOnClickListener {
            startActivity(Intent(this@CategoryActivity, SearchActivity::class.java))
        }

    }

    override fun onItemClick(item: CategorysSubcatModel) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("id", item.id)
        intent.putExtra("icon", item.icon)
        startActivity(intent)
    }
}