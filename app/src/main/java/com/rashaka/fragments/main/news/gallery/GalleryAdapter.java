package com.rashaka.fragments.main.news.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashaka.domain.fake_models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.rashaka.R;

/**
 * Created by User on 28.08.2017.
 */

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article> mList;

    public GalleryAdapter(Context context, List<Article> list) {
        this.mContext = context;
        this.mList = list;
    }

    protected class Holder {
        public TextView mTitle;
        public ImageView mImage;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Article getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_main_news, viewGroup, false
            );
            holder = new Holder();
            holder.mTitle = (TextView) view.findViewById(R.id.news_item_text);
            holder.mImage = (ImageView) view.findViewById(R.id.news_item_image);
            view.setTag(holder);
//            button = new Button(parent.getContext()); //context
//            button.setText(redeemList.get(position));
        } else {
            holder = (Holder) view.getTag();
        }

        Picasso.with(mContext).load(getItem(i).getUrlToImage()).into(holder.mImage);
        holder.mTitle.setText(getItem(i).getTitle());

        return view;
    }
}
