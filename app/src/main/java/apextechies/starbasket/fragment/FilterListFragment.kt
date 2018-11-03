package apextechies.starbasket.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apextechies.starbasket.R
import apextechies.starbasket.R.id.filterListRV
import apextechies.starbasket.adapter.FilterCategoryAdapter
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategoryModel
import apextechies.starbasket.retrofit.DownlodableCallback
import apextechies.starbasket.retrofit.RetrofitDataProvider
import kotlinx.android.synthetic.main.fragment_filterlist.*

@SuppressLint("ValidFragment")
class FilterListFragment(s: String) : Fragment(){

    var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filterlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filterListRV.layoutManager = LinearLayoutManager(activity)
        retrofitDataProvider = RetrofitDataProvider(activity)
        getCategoryList()
    }

    private fun getCategoryList() {
        var list = ArrayList<CategoryDataModel>()
        retrofitDataProvider!!.category(object : DownlodableCallback<CategoryModel> {
            override fun onSuccess(result: CategoryModel?) {

                if (result != null){
                    for (i in 0 until result!!.data!!.size) {
                        if (result.data!![i].status.equals("1"))
                            list.add(CategoryDataModel(result.data!![i].id, result.data!![i].name, result.data!![i].icon, result.data!![i].status, result.data!![i].subcat))
                    }
                    if (list.size > 0) {
                        filterListRV.adapter = FilterCategoryAdapter(object : FilterCategoryAdapter.OnItemClickListener {
                            override fun onItemClick(item: CategoryDataModel) {

                            }

                        }, list)
                    }
                }

            }

            override fun onFailure(error: String?) {    }

            override fun onUnauthorized(errorNumber: Int) {    }
        })
    }
}