package rashakacom.rashaka.fragments.main_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rashakacom.rashaka.R;
import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 28.08.2017.
 */

public class NewsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article> mList;

    public NewsGridAdapter(Context context, List<Article> list) {
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
