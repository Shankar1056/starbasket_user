package apextechies.starbasket.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.util.ArrayList

import apextechies.starbasket.R
import apextechies.starbasket.model.OrderItemModel
import apextechies.starbasket.model.ProductVarientModel


class OrderDetailsAdapter : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {
    private var mItemList = ArrayList<ProductVarientModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_order_details, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItemList[position]
        val context = holder.itemView.context

        holder.titleTV.text = item.product_name
        holder.quantityTV.text = "Quantity " + item.quantity!!
        holder.priceTV.text = "₹ " + item.price!!
        holder.varent.text = item.varient!!

        if (item.image!!.toString().trim { it <= ' ' }.length > 0) {
            Picasso.with(context)
                    .load(item.image)
                    .fit()
                    .centerInside()
                    .into(holder.imageIV)
        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun addItems(itemList: ArrayList<ProductVarientModel>) {
        mItemList = itemList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var imageIV: ImageView
        internal var titleTV: TextView
        internal var quantityTV: TextView
        internal var priceTV: TextView
        internal var varent: TextView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView
            quantityTV = itemView.findViewById<View>(R.id.tv_quantity) as TextView
            priceTV = itemView.findViewById<View>(R.id.tv_price) as TextView
            varent = itemView.findViewById<View>(R.id.varent) as TextView
        }
    }
}
