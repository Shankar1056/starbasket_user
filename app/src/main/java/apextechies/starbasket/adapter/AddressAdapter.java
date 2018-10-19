package apextechies.starbasket.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;

import apextechies.starbasket.R;
import apextechies.starbasket.activity.MainActivity;
import apextechies.starbasket.common.ClsGeneral;
import apextechies.starbasket.model.AddressDataModel;
import apextechies.starbasket.model.AddressModel;
import apextechies.starbasketseller.common.AppConstants;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
	private final ArrayList<AddressDataModel> mItemList = new ArrayList<>();
	private final OnItemClickListener mListener;
	private int mSelectedPosition = 0;
	private Context context;
	
	public AddressAdapter(OnItemClickListener listener) {
		mListener = listener;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
		return new ViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		AddressDataModel item = mItemList.get(position);
		holder.chosenRB.setChecked(mSelectedPosition == position);
		holder.addressTV.setText(item.getAddress1());
		holder.tv_username.setText(item.getName());
		String a = ClsGeneral.getStrPreferences(context, AppConstants.INSTANCE.getMOBILE());
		holder.tv_mobile.setText(ClsGeneral.getStrPreferences(context, AppConstants.INSTANCE.getMOBILE()));
	}
	
	@Override
	public int getItemCount() {
		return mItemList.size();
	}
	
	public void addItem(AddressDataModel item,Context context ) {
		mItemList.add(item);
		this.context = context;
		notifyItemInserted(mItemList.size() - 1);
	}
	
	public void updateItem(AddressDataModel address, int position) {
		mItemList.set(position, address);
		notifyItemChanged(position);
	}
	
	public void deleteItem(int position) {
		mItemList.remove(position);
		mSelectedPosition = mSelectedPosition >= mItemList.size() ? mItemList.size() - 1 : mSelectedPosition;
		notifyDataSetChanged();
	}
	
	public AddressDataModel getSelectedAddress() {
		return mItemList.get(mSelectedPosition);
	}
	
	public interface OnItemClickListener {
		void onUpdate(AddressDataModel item, int position);
		void onDelete(AddressDataModel item, int position);
	}
	
	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final RadioButton chosenRB;
		private final TextView addressTV;
		private final TextView tv_username;
		private final TextView tv_mobile;
		private final ImageView deleteIV;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			chosenRB = (RadioButton) itemView.findViewById(R.id.rb_address);
			addressTV = (TextView) itemView.findViewById(R.id.tv_address);
			tv_username = (TextView) itemView.findViewById(R.id.tv_username);
			tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
			deleteIV = (ImageView) itemView.findViewById(R.id.iv_delete);
			chosenRB.setOnClickListener(this);
			deleteIV.setOnClickListener(this);
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.rb_address:
					mSelectedPosition = getAdapterPosition();
					notifyDataSetChanged();
					break;
				
				case R.id.iv_delete:
					/*new AlertDialog.Builder(itemView.getContext())
						.setTitle(R.string.title_delete_address)
						.setMessage(R.string.delete_address)
						.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mListener.onDelete(mItemList.get(getAdapterPosition()), getAdapterPosition());
							}
						})
						.setNegativeButton(android.R.string.cancel, null)
						.create()
						.show();*/
					PopupMenu popup = new PopupMenu(context,v);
					popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
					popup.show();
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							int id = item.getItemId();
							if(id==R.id.action_edit){
								//show_toast("Install Clicked");
							}else if(id==R.id.action_delete){
								mListener.onDelete(mItemList.get(getAdapterPosition()), getAdapterPosition());
							}
							return true;
						}
					});
					break;
				
				default:
					//mListener.onUpdate(mItemList.get(getAdapterPosition()), getAdapterPosition());
			}
		}
	}
}
