package apextechies.starbasket.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import apextechies.starbasket.R;
import apextechies.starbasket.common.Utilz;
import apextechies.starbasket.model.OrderModel;
import apextechies.starbasket.model.UserOrderDataListModel;
import apextechies.starbasket.model.UserOrderListModel;



public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
	private final ArrayList<UserOrderDataListModel> mItemList = new ArrayList<>();
	private final OnItemClickListener mListener;
	
	public OrderAdapter(OnItemClickListener listener) {
		mListener = listener;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.item_order, parent, false);
		return new ViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		UserOrderDataListModel item = mItemList.get(position);
		Resources res = holder.itemView.getResources();
		
		holder.dateTV.setText(item.getOrder_date());
		holder.orderIdTV.setText(res.getString(R.string.format_order_id, item.getTransaction_id()));
		holder.orderStatusTV.setText(""+Utilz.getStatus(item.getOrder_status()));
		holder.totalTV.setText("₹ "+item.getTotal_price());
	}
	
	@Override
	public int getItemCount() {
		return mItemList.size();
	}
	
	public void addItem(UserOrderDataListModel item) {
		mItemList.add(item);
		notifyItemInserted(mItemList.size() - 1);
	}
	
	public interface OnItemClickListener {
		void onItemClick(UserOrderDataListModel item);
	}
	
	class ViewHolder extends RecyclerView.ViewHolder {
		TextView dateTV, orderIdTV, orderStatusTV, totalTV;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			dateTV = (TextView) itemView.findViewById(R.id.tv_date);
			orderIdTV = (TextView) itemView.findViewById(R.id.tv_order_id);
			orderStatusTV = (TextView) itemView.findViewById(R.id.tv_order_status);
			totalTV = (TextView) itemView.findViewById(R.id.tv_total);
			
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mListener.onItemClick(mItemList.get(getAdapterPosition()));
				}
			});
		}
	}
}
