package rashakacom.rashaka.fragments.main.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rashakacom.rashaka.R;
import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 29.08.2017.
 */

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mList;

    public RecipeRecyclerAdapter(Context context, List<Article> list) {
        this.mContext = context;
        this.mList = list;
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
        Picasso.with(mContext).load(mList.get(position).getUrlToImage()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.favorite_item_text);
            mImageView = v.findViewById(R.id.favorite_item_image);
        }
    }
}
