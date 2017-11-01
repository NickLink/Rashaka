package com.rashaka.fragments.main.home.exercise;

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
import com.rashaka.RaApp;
import com.rashaka.domain.routes.RouteInfo;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.helpers.SelectAdapterListener;

import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<RouteInfo> mList;
    private SelectAdapterListener mListener;

    public ExerciseRecyclerAdapter(Context context, List<RouteInfo> list, SelectAdapterListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_exercise, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_start_text.setText(mList.get(position).getDesc());
        holder.item_date_text.setText(
                Support.getStringDateByDate(mList.get(position).getStart(), true));

        holder.item_start_button.setText(RaApp.getLabel(LangKeys.key_view));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_parent;
        public ImageView item_pin_button;
        public TextView item_start_text;
        public TextView item_date_text;
        public TextView item_start_button;

        public ViewHolder(View v) {
            super(v);
            item_parent = v.findViewById(R.id.item_exercise_parent);
            item_pin_button = v.findViewById(R.id.item_pin_button);
            item_start_text = v.findViewById(R.id.item_start_text);
            item_date_text = v.findViewById(R.id.item_date_text);
            item_start_button = v.findViewById(R.id.item_start_button);

            item_start_button.setOnClickListener(v1 -> mListener.onItemClick(getAdapterPosition()));
        }
    }

    public void addData(@NonNull List<RouteInfo> mData){
        if(mData != null && mData.size() != 0)
            mList.addAll(mData);
    }
}
