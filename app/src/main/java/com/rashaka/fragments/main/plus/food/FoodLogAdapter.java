package com.rashaka.fragments.main.plus.food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rashaka.RaApp;
import com.rashaka.domain.food.LogFoodItem;
import com.rashaka.system.lang.LangKeys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.rashaka.R;

/**
 * Created by User on 29.08.2017.
 */

public class FoodLogAdapter extends RecyclerView.Adapter<FoodLogAdapter.ViewHolder> {

    private Context mContext;
    private List<LogFoodItem> mList;
    onClick mListener;

    public FoodLogAdapter(Context context, List<LogFoodItem> list, onClick listener) {
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_log_food, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mDate.setText(getDateFrom(mList.get(position).getDateTime()));
        holder.mTime.setText(getTimeFrom(mList.get(position).getDateTime()));
        holder.mText.setText(mList.get(position).getDescription());
        holder.mType.setText(mList.get(position).getFoodType());
        holder.mButton.setText(RaApp.getLabel(LangKeys.key_edit));
    }

    private String getTimeFrom(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = format.parse(dateTime);
            format = new SimpleDateFormat("hh:mm");
            String date = format.format(newDate);
            return date;
        } catch (Exception e){
            return "";
        }
    }

    private String getDateFrom(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = format.parse(dateTime);
            format = new SimpleDateFormat("dd MMM");
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
        TextView mDate;
        TextView mTime;
        TextView mType;
        TextView mText;
        TextView mButton;

        public ViewHolder(View v) {
            super(v);
            mDate = v.findViewById(R.id.item_date);
            mTime = v.findViewById(R.id.item_time);
            mType = v.findViewById(R.id.item_type);
            mText = v.findViewById(R.id.item_text);
            mButton = v.findViewById(R.id.item_start_button);

//            mShare = v.findViewById(R.id.item_share);
//            mMore = v.findViewById(R.id.item_read_more);
//            mShare.setOnClickListener(view -> mListener.onClick(getAdapterPosition()));
//            mMore.setOnClickListener(view -> mListener.onClickId(getAdapterPosition(), mList.get(getAdapterPosition()).getId()));
        }
    }

    public void addData(@NonNull List<LogFoodItem> mData){
        if(mData != null && mData.size() != 0)
            mList.addAll(mData);
        notifyDataSetChanged();
    }
}
