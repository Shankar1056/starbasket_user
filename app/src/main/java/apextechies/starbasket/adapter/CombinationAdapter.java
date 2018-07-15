package apextechies.starbasket.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apextechies.starbasket.R;
import apextechies.starbasket.model.ProductDataModel;
import apextechies.starbasket.model.ProductModel;
import apextechies.starbasket.model.UnitDetailsModel;


public class CombinationAdapter extends RecyclerView.Adapter<CombinationAdapter.ViewHolder> {
	private final ArrayList<UnitDetailsModel> mItemList;
	private final ProductDataModel mProduct;
	private final OnItemClickListener mListener;

	public CombinationAdapter(ProductDataModel product, OnItemClickListener listener) {
		mProduct = product;
		mItemList = mProduct.getUnitdetails();
		mListener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.item_combination, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		UnitDetailsModel item = mItemList.get(position);

		holder.combinationTV.setSelected(position == mProduct.getSelectedIndes());
		holder.combinationTV.setText(item.getUnit());
	}

	@Override
	public int getItemCount() {
		return mItemList.size();
	}

	public interface OnItemClickListener {
		void onItemClick(int position);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView combinationTV;

		ViewHolder(View itemView) {
			super(itemView);

			combinationTV = (TextView) itemView;
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mListener.onItemClick(getAdapterPosition());
				}
			});
		}
	}
}
