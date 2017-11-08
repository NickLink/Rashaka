package com.rashaka.fragments.main.plus.drink;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.rashaka.R;
import com.rashaka.RaApp;

/**
 * Created by User on 29.08.2017.
 */

public class DrinkAlarmAdapter extends RecyclerView.Adapter<DrinkAlarmAdapter.ViewHolder> {

    private static final String TAG = DrinkAlarmAdapter.class.getSimpleName();

    public interface AlarmClick{
        void editClick(int pos);
        void switchClick(int pos, boolean b);
    }

    private Context mContext;
    private List<DrinkAlarmItem> mList;
    private AlarmClick mClick;

    public DrinkAlarmAdapter(Context context, List<DrinkAlarmItem> list, AlarmClick click) {
        this.mContext = context;
        this.mList = list;
        this.mClick = click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_drink_alarm, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e(TAG, "RECYCLER ADAPTER POSITION -> " + position);

        holder.mItemSwitch.setChecked(mList.get(position).isEnabled());

        holder.mItemTime.setText(String.format("%02d", mList.get(position).getHours())
                + ":"
                + String.format("%02d", mList.get(position).getMinutes()));

        if(mList.get(position).getAm() == 0){
            holder.mItemTimeAm.setText(RaApp.getLabel("key_am"));
        } else {
            holder.mItemTimeAm.setText(RaApp.getLabel("key_pm"));
        }

        if (mList.get(position).isEnMonday())
            holder.mItemDayMon.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnTuesday())
            holder.mItemDayTue.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnWednesday())
            holder.mItemDayWed.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnThursday())
            holder.mItemDayThu.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnFriday())
            holder.mItemDayFri.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnSaturday())
            holder.mItemDaySat.setBackgroundResource(R.drawable.time_circle_green);
        if (mList.get(position).isEnSunday())
            holder.mItemDaySun.setBackgroundResource(R.drawable.time_circle_green);
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

        public LinearLayout mClickEdit;

        public TextView mItemTime;
        public TextView mItemTimeAm;

        public TextView mItemDayMon;
        public TextView mItemDayTue;
        public TextView mItemDayWed;
        public TextView mItemDayThu;
        public TextView mItemDayFri;
        public TextView mItemDaySat;
        public TextView mItemDaySun;

        public SwitchCompat mItemSwitch;

        public ViewHolder(View v) {
            super(v);

            mClickEdit = v.findViewById(R.id.item_click_main);
            mItemTime = v.findViewById(R.id.item_time);
            mItemTimeAm = v.findViewById(R.id.item_time_am);
            mItemDayMon = v.findViewById(R.id.item_day_Mon);
            mItemDayTue = v.findViewById(R.id.item_day_Tue);
            mItemDayWed = v.findViewById(R.id.item_day_Wed);
            mItemDayThu = v.findViewById(R.id.item_day_Thu);
            mItemDayFri = v.findViewById(R.id.item_day_Fri);
            mItemDaySat = v.findViewById(R.id.item_day_Sat);
            mItemDaySun = v.findViewById(R.id.item_day_Sun);

            mItemSwitch = v.findViewById(R.id.item_switch);

            mClickEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClick.editClick(getAdapterPosition());
                }
            });

            mItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mClick.switchClick(getAdapterPosition(), b);
                }
            });
        }
    }
}
