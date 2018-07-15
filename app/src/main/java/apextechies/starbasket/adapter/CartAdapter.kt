package apextechies.starbasket.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apextechies.starbasket.R
import apextechies.starbasket.listener.OnCartListener
import apextechies.starbasket.model.CartDataModel
import com.squareup.picasso.Picasso
import java.util.ArrayList

class CartAdapter (private val mListener: OnCartListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val mItemList = ArrayList<CartDataModel>()

    val totalPrice: Double
        get() {
            var price = 0.0
            for (item in mItemList) {
                price += Integer.parseInt(item.price) * Integer.parseInt(item.quantity)
            }
            return price
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_cart, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = mItemList[position]

        holder.titleTV.text = item.name
        holder.qtyTV.text = item.quantity+ " kg"
        holder.sizeTV.text = item.quantity.toString()
        holder.priceTV.text = item.price
        if (!item.image!!.isEmpty())
        Picasso.with(context)
                .load(item.image)
                .fit()
                .centerInside()
                .into(holder.imageIV)
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun addItem(item: CartDataModel) {
        mItemList.add(item)
        notifyItemInserted(mItemList.size - 1)
    }

    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageIV: ImageView
        var titleTV: TextView
        var qtyTV: TextView
        var priceTV: TextView
        var sizeTV: TextView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView
            qtyTV = itemView.findViewById<View>(R.id.tv_quantity) as TextView
            priceTV = itemView.findViewById<View>(R.id.tv_price) as TextView
            sizeTV = itemView.findViewById<View>(R.id.tv_size) as TextView
            itemView.findViewById<View>(R.id.tv_inc_quantity).setOnClickListener(this)
            itemView.findViewById<View>(R.id.tv_dec_quantity).setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val item = mItemList[adapterPosition]

            when (v.id) {
                R.id.tv_inc_quantity -> {
                    item.quantity = (Integer.parseInt(item.quantity) + 1).toString()
                    mListener.onCartUpdate(item)
                }

                R.id.tv_dec_quantity -> {
                    item.quantity = (Integer.parseInt(item.quantity) - 1).toString()
                    mListener.onCartUpdate(item)
                }
            }
        }
    }
}
