package com.rashaka.fragments.main.home.steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.domain.steps.StepsListItem;

import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private Context mContext;
    private List<StepsListItem> mList;
    OnClick mListener;

    public StepsAdapter(Context context, List<StepsListItem> list, OnClick listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public interface OnClick {
        void click(int position);
        void clickId(int position, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_home_steps, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mItemStart.setText(mList.get(position).getTime());
        holder.mItemLength.setText(String.valueOf(mList.get(position).getActive_sec()));
        holder.mItemLocation.setText(String.valueOf(mList.get(position).getSteps()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mParentLayout;
        public ImageView mIconRoute;
        public TextView mItemStart;
        public TextView mItemLength;
        public TextView mItemLocation;

        public ViewHolder(View v) {
            super(v);
            mParentLayout = v.findViewById(R.id.parent_layout);
            mIconRoute = v.findViewById(R.id.icon_route);
            mItemStart = v.findViewById(R.id.item_start);
            mItemLength = v.findViewById(R.id.item_length);
            mItemLocation = v.findViewById(R.id.item_location);

            mParentLayout.setOnClickListener(view -> mListener.click(getAdapterPosition()));
            //mMore.setOnClickListener(view -> mListener.clickId(getAdapterPosition(), mList.get(getAdapterPosition()).getId()));
        }
    }

    public void addData(@NonNull List<StepsListItem> mData){
        if(mData != null && mData.size() != 0)
            mList.addAll(mData);
    }
}
