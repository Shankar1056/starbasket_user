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
import apextechies.starbasket.model.WishListDataMode
import com.squareup.picasso.Picasso
import java.util.ArrayList

class WishListAdapter (private val mListener: OnItemClickListener, private val wishlstClick: OnClickListenr) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    private val mCartList = ArrayList<CartDataModel>()
    private val mWishist = ArrayList<WishListDataMode>()
    private var dialog: Dialog? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_product_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mWishist[position]
        val context = holder.itemView.context

        holder.incTV.visibility = View.INVISIBLE
        holder.decTV.visibility = View.INVISIBLE
        for (i in 0 until item.data!!.size) {
            holder.titleTV.text = item.data!![i].name
            holder.priceTV.text = item.data!![i].unitdetails!![0].actual_price
            holder.combinationTV.visibility = View.VISIBLE
            holder.combinationTV.text = item.data!![i].unitdetails!![0].varient
            if (!item.data!![i].unitdetails!![0].discount!!.isEmpty()) {
                holder.offTV.visibility = View.VISIBLE
                holder.offTV.text = item.data!![i].unitdetails!![0].discount
            } else {
                holder.offTV.visibility = View.INVISIBLE
            }


                    holder.wishlist.setBackgroundResource(R.drawable.color_wishlist)

            holder.combinationTV.text = item.data!![i].unitdetails!![item.data!![i].selectedIndes!!].varient


            if (mCartList.size > 0 || holder.quantityTV.text.toString().trim().length > 0) {
                try {
                    if (Integer.parseInt(mCartList!![item.data!![i].selectedIndes!!]!!.quantity!!) > 0) {
                        holder.decTV.setVisibility(View.VISIBLE);
                        holder.quantityTV.setVisibility(View.VISIBLE)
                        holder.quantityTV.setText(mCartList[item.data!![i].selectedIndes!!].quantity)
                    } else {
                        holder.decTV.setVisibility(View.INVISIBLE);
                        holder.quantityTV.setVisibility(View.INVISIBLE);

                    }
                } catch (e: IndexOutOfBoundsException) {

                } catch (e: Exception) {

                }
            }



            if (!TextUtils.isEmpty(item.data!![i].image)) {
                Picasso.with(context)
                        .load(item.data!![i].image)
                        .fit()
                        .centerInside()
                        .into(holder.imageIV);
            }
        }
    }

    override fun getItemCount(): Int {
        return mWishist.size
    }

    fun addItem(item: WishListDataMode) {
        mWishist.add(item)
        notifyItemInserted(mWishist.size - 1)

    }

    fun removeList() {
        mWishist.clear()
        notifyDataSetChanged()

    }



    fun addWishlist(item: WishListDataMode, pos: Int) {
        mWishist.add(item)
        notifyItemChanged(pos)
    }

    interface OnItemClickListener {
        fun onItemClick(item: ArrayList<WishListDataMode>, pos: Int)
        fun onQuantityUpdate(id: String?, quantity: String, name: String?, selling_price: String?, image: String, varientid: String?, seller_id: String, i: Int) {

        }
    }
    interface OnClickListenr {
        fun onClick(prod_id: String, opration: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, WishListAdapter.OnItemClickListener {
        override fun onItemClick(item: ArrayList<WishListDataMode>, pos: Int) {

        }

        var imageIV: ImageView
        var titleTV: TextView
        var offTV: TextView
        var combinationTV: TextView
        var priceTV: TextView
        var decTV: TextView
        var quantityTV: TextView
        var incTV: TextView
        var wishlist: ImageView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView
            offTV = itemView.findViewById<View>(R.id.tv_off) as TextView
            combinationTV = itemView.findViewById<View>(R.id.tv_quantity) as TextView
            priceTV = itemView.findViewById<View>(R.id.tv_price) as TextView
            decTV = itemView.findViewById<View>(R.id.tv_dec_quantity) as TextView
            quantityTV = itemView.findViewById<View>(R.id.tv_size) as TextView
            incTV = itemView.findViewById<View>(R.id.tv_inc_quantity) as TextView
            wishlist = itemView.findViewById<View>(R.id.wishlist) as ImageView

            itemView.setOnClickListener(this)
            wishlist.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.id) {


                R.id.wishlist -> {
                        wishlstClick.onClick(mWishist[adapterPosition].prod_id!!, "remove")

                }


                else -> {
                   // mListener.onItemClick(mWishist, adapterPosition)

                }
            }
        }


    }
}
