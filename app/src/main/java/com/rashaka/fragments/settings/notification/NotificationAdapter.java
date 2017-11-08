package com.rashaka.fragments.settings.notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.domain.notif.NotificationItem;
import com.rashaka.utils.Support;

import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public interface ItemClick {
        void itemClick(int pos);
    }

    private Context mContext;
    private List<NotificationItem> mList;
    private ItemClick mItemClick;

    public NotificationAdapter(Context context, List<NotificationItem> list, ItemClick click) {
        this.mContext = context;
        this.mList = list;
        this.mItemClick = click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mItemTitle.setText(mList.get(position).getTitle());
        holder.mItemDate.setText(Support.getNotyDateFromDate(mList.get(position).getDateTime()));
        holder.mItemTime.setText(Support.getNotyTimeFromDate(mList.get(position).getDateTime()));
        holder.mItemText.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mClick;
        public TextView mItemTitle;
        public TextView mItemDate;
        public TextView mItemTime;
        public TextView mItemText;

        public ViewHolder(View v) {
            super(v);

            mClick = v.findViewById(R.id.click);
            mItemTitle = v.findViewById(R.id.title);
            mItemDate = v.findViewById(R.id.date);
            mItemTime = v.findViewById(R.id.time);
            mItemText = v.findViewById(R.id.text);

            mClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClick.itemClick(getAdapterPosition());
                }
            });
        }
    }

    public void addData(@NonNull List<NotificationItem> mData){
        if(mData != null && mData.size() != 0){
            mList.addAll(mData);
            DataChanged();
        }
    }

    @UiThread
    private void DataChanged(){
        notifyDataSetChanged();
    }
}
