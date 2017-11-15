package com.rashaka.fragments.main.plus.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.domain.sleep.SleepItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class SleepLogAdapter extends RecyclerView.Adapter<SleepLogAdapter.ViewHolder> {

    private Context mContext;
    private List<SleepItem> mList;
    onClick mListener;

    public SleepLogAdapter(Context context, List<SleepItem> list, onClick listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public interface onClick{
        void onClick(int position);
        void onClickId(int position, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_log_sleep_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //TODO Change values to responding one`s
        holder.mFrom.setText(getDateFrom(mList.get(position).getSleepStart()));
        holder.mTo.setText((getDateFrom(mList.get(position).getSleepEnd())));
        //holder.mDecs.setText(mList.get(position).getDescription());
        holder.mTime.setText(getTimeFrom(mList.get(position).getSleepTime()));


    }

    private String getTimeFrom(String dateTime) {
        int hours = 0, minutes = 0;
        try {
            int sec = Integer.parseInt(dateTime);
            if(sec != 0){
                hours = sec / 3600;
                minutes = sec % 3600 / 60;
            }
            return new StringBuilder()
                    .append(String.format("%02d", hours))
                    .append(":")
                    .append(String.format("%02d", minutes))
                    .toString();
        } catch (Exception e){
            return "";
        }
    }

    private String getDateFrom(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = format.parse(dateTime);
            format = new SimpleDateFormat("dd MMM HH:mm");
            String date = format.format(newDate);
            return date;
        } catch (Exception e){
            return "";
        }    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mFrom;
        TextView mTo;
        TextView mDecs;
        TextView mTime;

        public ViewHolder(View v) {
            super(v);
            mFrom = v.findViewById(R.id.item_from);
            mTo = v.findViewById(R.id.item_to);
            //mDecs = v.findViewById(R.id.item_type);
            mTime = v.findViewById(R.id.item_time_text);

//            mShare = v.findViewById(R.id.item_share);
//            mMore = v.findViewById(R.id.item_read_more);
//            mShare.setOnClickListener(view -> mListener.onClick(getAdapterPosition()));
//            mMore.setOnClickListener(view -> mListener.onClickId(getAdapterPosition(), mList.get(getAdapterPosition()).getId()));
        }
    }

    public void addData(@NonNull List<SleepItem> mData){
        if(mData != null && mData.size() != 0)
            mList.addAll(mData);
        notifyDataSetChanged();
    }
}
