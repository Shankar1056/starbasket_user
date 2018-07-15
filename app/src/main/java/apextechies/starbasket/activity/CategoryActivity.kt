package apextechies.starbasket.activity

import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import apextechies.starbasket.R
import apextechies.starbasket.adapter.AllCategoryAdapter
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategoryModel
import apextechies.starbasket.model.CategorysSubcatModel

class CategoryActivity: AppCompatActivity(), AllCategoryAdapter.OnItemClickListener {


    private var mAdapter: AllCategoryAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

      var  mCategory = intent.getParcelableExtra<CategoryDataModel>(EXTRA_DATA) as CategoryModel

        mAdapter = AllCategoryAdapter(this)
        val categoryRV = findViewById(R.id.rv_category) as RecyclerView
        categoryRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        categoryRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        categoryRV.adapter = mAdapter
    }

    override fun onItemClick(item: CategorysSubcatModel) {

    }
}