package com.rashaka.fragments.main.recipe.latest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rashaka.domain.recipes.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.rashaka.R;

/**
 * Created by User on 29.08.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context mContext;
    private List<RecipeItem> mList;
    onClick mListener;

    public RecipeAdapter(Context context, List<RecipeItem> list, onClick listener) {
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(mList.get(position).getTitle());
        if (!TextUtils.isEmpty(mList.get(position).getImage()))
            Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mParentItem;
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mParentItem = v.findViewById(R.id.parent_item);
            mTextView = v.findViewById(R.id.favorite_item_text);
            mImageView = v.findViewById(R.id.favorite_item_image);

            mParentItem.setOnClickListener(view -> mListener.onClickId(
                    getAdapterPosition(),
                    mList.get(getAdapterPosition()).getId())
            );
        }
    }

    public void addData(@NonNull List<RecipeItem> mData) {
        if (mData != null && mData.size() != 0) {
            mList.addAll(mData);
            DataChanged();
        }
    }

    @UiThread
    private void DataChanged() {
        notifyDataSetChanged();
    }
}
