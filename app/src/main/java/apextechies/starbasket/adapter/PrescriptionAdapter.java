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
import apextechies.starbasket.model.PrescriptionDataModel;
import apextechies.starbasket.model.UserOrderDataListModel;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private final ArrayList<PrescriptionDataModel> mItemList = new ArrayList<>();
    private final OnPreItemClickListener mListener;

    public PrescriptionAdapter(OnPreItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public PrescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrescriptionAdapter.ViewHolder holder, int position) {
        PrescriptionDataModel item = mItemList.get(position);
        Resources res = holder.itemView.getResources();

        holder.dateTV.setText(item.getOrder_date());
        holder.orderIdTV.setText(res.getString(R.string.format_order_id, item.getTransaction_id()));
        holder.orderStatusTV.setText(""+Utilz.getStatus(item.getOrder_status()));
        holder.totalTV.setText("₹ 00");
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void addItem(PrescriptionDataModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public interface OnPreItemClickListener {
        void onItemClick(PrescriptionDataModel item);
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
