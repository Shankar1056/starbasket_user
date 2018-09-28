package apextechies.starbasket.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.util.ArrayList

import apextechies.starbasket.R
import apextechies.starbasket.model.CartDataModel
import apextechies.starbasket.model.ProductDataModel
import apextechies.starbasket.model.ProductModel
import java.lang.Exception


class ProductListAdapter(private val mListener: OnItemClickListener) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    private val mItemList = ArrayList<ProductDataModel>()
    private val mCartList = ArrayList<CartDataModel>()
    private var dialog: Dialog? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_product_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItemList[position]
        val context = holder.itemView.context

        holder.titleTV.text = item.name
        holder.priceTV.text = item.unitdetails!![item.selectedIndes!!].actual_price
        holder.combinationTV.visibility = View.VISIBLE
        holder.combinationTV.text = item.unitdetails[item.selectedIndes!!].varient
        if (!item.unitdetails[item.selectedIndes!!].discount!!.isEmpty()) {
            holder.offTV.visibility = View.VISIBLE
            holder.offTV.text = item.unitdetails[item.selectedIndes!!].discount
        } else {
            holder.offTV.visibility = View.INVISIBLE
        }


        if (item.selectedIndes == -1) {
            holder.combinationTV.setVisibility(View.INVISIBLE);
        } else {
            holder.combinationTV.setVisibility(View.VISIBLE);
            holder.combinationTV.text = item.unitdetails[item.selectedIndes!!].varient
        }

        if (mCartList.size>0 || holder.quantityTV.text.toString().trim().length>0) {
            try {
                if (Integer.parseInt(mCartList!![item.selectedIndes!!]!!.quantity!!) > 0) {
                    holder.decTV.setVisibility(View.VISIBLE);
                    holder.quantityTV.setVisibility(View.VISIBLE)
                    holder.quantityTV.setText(mCartList[item.selectedIndes!!].quantity)
                } else {
                    holder.decTV.setVisibility(View.INVISIBLE);
                    holder.quantityTV.setVisibility(View.INVISIBLE);

            }
        } catch (e: IndexOutOfBoundsException){

        } catch (e: Exception){

        }
    }



        if (!TextUtils.isEmpty(item.image)) {
			Picasso.with(context)
				.load(item.image)
				.fit()
				.centerInside()
				.into(holder.imageIV);
		}
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun addItem(item: ProductDataModel) {
        mItemList.add(item)
        notifyItemInserted(mItemList.size - 1)
    }
    fun addCart(item: CartDataModel, pos: Int) {
        mCartList.add(item)
//        notifyItemInserted(mCartList.size - 1)
        notifyItemChanged(pos)
    }
    fun updateViews(pos: Int) {
        // notifyItemInserted(mItemList.size - 1)
        notifyItemChanged(pos)
    }
    interface OnItemClickListener {
        fun onItemClick(item: ArrayList<ProductDataModel>, pos: Int)

        //fun onQuantityUpdate(item: ProductDataModel, pos: Int)
        fun onQuantityUpdate(id: String?, quantity: String, name: String?, selling_price: String?, image: String, varientid: String?, seller_id: String,  i: Int) {

        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, CombinationAdapter.OnItemClickListener {
        var imageIV: ImageView
        var titleTV: TextView
        var offTV: TextView
        var combinationTV: TextView
        var priceTV: TextView
        var decTV: TextView
        var quantityTV: TextView
        var incTV: TextView

        init {

            imageIV = itemView.findViewById<View>(R.id.iv_image) as ImageView
            titleTV = itemView.findViewById<View>(R.id.tv_title) as TextView
            offTV = itemView.findViewById<View>(R.id.tv_off) as TextView
            combinationTV = itemView.findViewById<View>(R.id.tv_quantity) as TextView
            priceTV = itemView.findViewById<View>(R.id.tv_price) as TextView
            decTV = itemView.findViewById<View>(R.id.tv_dec_quantity) as TextView
            quantityTV = itemView.findViewById<View>(R.id.tv_size) as TextView
            incTV = itemView.findViewById<View>(R.id.tv_inc_quantity) as TextView

            combinationTV.setOnClickListener(this)
            decTV.setOnClickListener(this)
            incTV.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.tv_quantity -> showCombinationDialog()


                R.id.tv_dec_quantity -> {
                    if (quantityTV.text.toString().trim().equals("") || quantityTV.text.toString().trim().equals("0")){

                    }else{
                        mCartList[adapterPosition].quantity = (Integer.parseInt(mCartList[adapterPosition].quantity) - 1).toString()
                        notifyItemChanged(adapterPosition)
                        mListener.onQuantityUpdate(mItemList[adapterPosition].id, mCartList[adapterPosition].quantity, mItemList[adapterPosition].name, mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].selling_price, "image", mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].varient, mItemList[adapterPosition].seller_id!!, 0)

                    }
                         }

                R.id.tv_inc_quantity -> {
                    if (quantityTV.text.toString().trim().equals("") || quantityTV.text.toString().trim().equals("0")){
                       /* mCartList[adapterPosition].quantity = "1"*/
                        quantityTV.setText("1")
                        notifyItemChanged(adapterPosition)
                        mListener.onQuantityUpdate(mItemList[adapterPosition].id, "1", mItemList[adapterPosition].name, mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].selling_price, "image", mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].varient, mItemList[adapterPosition].seller_id!!,0)

                    }else{
                        mCartList[adapterPosition].quantity = (Integer.parseInt(quantityTV.text.toString()) + 1).toString()
                        notifyItemChanged(adapterPosition)
                        mListener.onQuantityUpdate(mItemList[adapterPosition].id, mCartList[adapterPosition].quantity, mItemList[adapterPosition].name, mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].selling_price, "image", mItemList[adapterPosition].unitdetails!![mItemList[adapterPosition].selectedIndes!!].varient, mItemList[adapterPosition].seller_id!!,0)

                    }
                         }


                else -> {

                        mListener.onItemClick(mItemList, adapterPosition)

                }
            }
        }

        private fun showCombinationDialog() {
            val item = mItemList[adapterPosition]
            dialog = Dialog(itemView.context, R.style.AppThemeDialog)
            dialog!!.setContentView(R.layout.dialog_combination)
            dialog!!.show()
            val combinationRV = dialog!!.findViewById<View>(R.id.rv_combination) as RecyclerView
            combinationRV.layoutManager = LinearLayoutManager(itemView.context)
            combinationRV.setHasFixedSize(true)
            combinationRV.adapter = CombinationAdapter(item, this)
        }

        override fun onItemClick(position: Int) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }

            mItemList.get(getAdapterPosition()).selectedIndes = position
            notifyItemChanged(adapterPosition)
        }
    }
}
