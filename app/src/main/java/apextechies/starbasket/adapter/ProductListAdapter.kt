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
import apextechies.starbasket.model.ProductDataModel
import apextechies.starbasket.model.ProductModel


class ProductListAdapter(private val mListener: OnItemClickListener) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    private val mItemList = ArrayList<ProductDataModel>()
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
        holder.priceTV.text = item.unitdetails!![0].actual_price
        holder.combinationTV.visibility = View.VISIBLE
        holder.combinationTV.text = item.unitdetails[item.selectedIndes!!].unit
        if (!item.unitdetails[0].discount!!.isEmpty()) {
            holder.offTV.visibility = View.VISIBLE
            holder.offTV.text = item.unitdetails[0].discount
        } else {
            holder.offTV.visibility = View.INVISIBLE
        }

        if (!item.unitdetails[0].discount!!.isEmpty()) {
			holder.offTV.setVisibility(View.VISIBLE);
			holder.offTV.setText(item.unitdetails[0].discount);
		} else {
			holder.offTV.setVisibility(View.INVISIBLE);
		}

        if (item.selectedIndes == -1) {
			holder.combinationTV.setVisibility(View.INVISIBLE);
		} else {
			holder.combinationTV.setVisibility(View.VISIBLE);
			holder.combinationTV.text = item.unitdetails[item.selectedIndes!!].unit
		}

        if (Integer.parseInt(item.quantity) > 0) {
			holder.decTV.setVisibility(View.VISIBLE);
			holder.quantityTV.setVisibility(View.VISIBLE)
			holder.quantityTV.setText(item.quantity)
		} else {
			holder.decTV.setVisibility(View.INVISIBLE);
			holder.quantityTV.setVisibility(View.INVISIBLE);
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

    interface OnItemClickListener {
        fun onItemClick(item: ProductDataModel)

        fun onQuantityUpdate(item: ProductDataModel)
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
                    var item = mItemList[adapterPosition]
                    item.quantity = (Integer.parseInt(item.quantity) - 1).toString()
                    notifyItemChanged(adapterPosition)
                    mListener.onQuantityUpdate(item)
                }

                R.id.tv_inc_quantity -> {
                   var item = mItemList[adapterPosition]
                    item.quantity = (Integer.parseInt(item.quantity) + 1).toString()
                    notifyItemChanged(adapterPosition)
                    mListener.onQuantityUpdate(item)
                }

                else -> mListener.onItemClick(mItemList[adapterPosition])
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
