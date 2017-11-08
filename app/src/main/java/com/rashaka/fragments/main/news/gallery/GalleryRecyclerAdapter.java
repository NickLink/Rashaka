package com.rashaka.fragments.main.news.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.gallery.GalleryItem;
import com.rashaka.system.lang.LangKeys;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<GalleryItem> mList;
    LatestNewsClick mListener;

    public GalleryRecyclerAdapter(Context context, List<GalleryItem> list, LatestNewsClick listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public interface LatestNewsClick{
        void cShare(int position);
        void cMore(int position, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_news_latest, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mText.setText(mList.get(position).getDescription());
        Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.mImage);
        holder.mMore.setText(RaApp.getLabel(LangKeys.key_read_more));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mImage;
        public TextView mText;
        public ImageView mShare;
        public TextView mMore;

        public ViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.item_title);
            mImage = v.findViewById(R.id.item_image);
            mText = v.findViewById(R.id.item_text);
            mShare = v.findViewById(R.id.item_share);
            mMore = v.findViewById(R.id.item_read_more);

            mShare.setOnClickListener(view -> mListener.cShare(getAdapterPosition()));
            mMore.setOnClickListener(view -> mListener.cMore(getAdapterPosition(), mList.get(getAdapterPosition()).getId()));
        }
    }

    public void addData(@NonNull List<GalleryItem> mData){
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
