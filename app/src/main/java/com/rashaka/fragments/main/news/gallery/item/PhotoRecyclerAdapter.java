package com.rashaka.fragments.main.news.gallery.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rashaka.R;
import com.rashaka.domain.gallery.Photo;
import com.rashaka.utils.Support;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 29.08.2017.
 */

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Photo> mList;
    LatestNewsClick mListener;

    public PhotoRecyclerAdapter(Context context, List<Photo> list, LatestNewsClick listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    public interface LatestNewsClick {
        void cShare(int position);

        void cMore(int position, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (Integer.parseInt(mList.get(position).getType()) == 0) {
            //TODO Show photo
            holder.mImage.setVisibility(View.VISIBLE);
            holder.mVideo.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mList.get(position).getImage()))
                Picasso.with(mContext).load(mList.get(position).getImage()).into(holder.mImage);
        } else {
            //TODO Show video
            holder.mImage.setVisibility(View.GONE);
            holder.mVideo.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mList.get(position).getVideo())){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)holder.mParent.getLayoutParams();
                params.height = Support.dpToPx(200);
                holder.mParent.setLayoutParams(params);
                holder.mVideo.getSettings().setJavaScriptEnabled(true);
                holder.mVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
                holder.mVideo.loadUrl(mList.get(position).getVideo());
                holder.mVideo.setWebChromeClient(new WebChromeClient());
            }
        }


        //holder.mText.setText(mList.get(position).getText);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mParent;
        public ImageView mImage;
        public WebView mVideo;
        public TextView mText;
        //public TextView mMore;

        public ViewHolder(View v) {
            super(v);
            mParent = v.findViewById(R.id.parent_item);
            mImage = v.findViewById(R.id.item_image);
            mVideo = v.findViewById(R.id.item_video);
            mText = v.findViewById(R.id.item_text);

            //mMore = v.findViewById(R.id.item_read_more);

//            mShare.setOnClickListener(view -> mListener.cShare(getAdapterPosition()));
//            mMore.setOnClickListener(view -> mListener.cMore(getAdapterPosition(), mList.get(getAdapterPosition()).getId()));
        }
    }

    public void addData(@NonNull List<Photo> mData) {
        if (mData != null && mData.size() != 0) {
            mList.addAll(mData);
            DataChanged();
        }

    }

    @UiThread
    private void DataChanged() {
        notifyDataSetChanged();
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) //throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
