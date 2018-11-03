package apextechies.starbasket.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.R
import apextechies.starbasket.model.CategoryDataModel
import apextechies.starbasket.model.CategorysSubcatModel
import apextechies.starbasket.retrofit.ApiUrl
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import java.util.ArrayList

class FilterCategoryAdapter(private val mListener: OnItemClickListener, private val catlist: ArrayList<CategoryDataModel>) : RecyclerView.Adapter<FilterCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_filter_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = catlist[position]

        holder.titleTV.text = item.name

    }

    override fun getItemCount(): Int {
        return catlist.size
    }


    interface OnItemClickListener {
        fun onItemClick(item: CategoryDataModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView

        init {

            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView

            itemView.setOnClickListener {
                mListener.onItemClick(catlist[adapterPosition]) }
        }
    }
}
