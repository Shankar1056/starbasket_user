package apextechies.starbasket.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.R
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.ProductDataModel
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.ArrayList

class SearchAdapter (private val mItemList: ArrayList<ProductDataModel>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_search_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItemList[position]
        val context = holder.itemView.context

        holder.titleTV.text = item.name
        holder.itemView.setOnClickListener {
            mListener.onItemClick(mItemList, position)
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }


    interface OnItemClickListener {
        fun onItemClick(item: ArrayList<ProductDataModel>, pos: Int)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView

        init {

            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView

        }


}
}
