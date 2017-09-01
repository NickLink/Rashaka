package rashakacom.rashaka.fragments.main.home.exercise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rashakacom.rashaka.R;
import rashakacom.rashaka.utils.helpers.structure.helpers.SelectAdapterListener;
import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 29.08.2017.
 */

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> mList;
    private SelectAdapterListener mListener;

    public ExerciseRecyclerAdapter(Context context, List<Article> list, SelectAdapterListener listener) {
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

        holder.item_start_text.setText(mList.get(position).getTitle());
        holder.item_date_text.setText(mList.get(position).getAuthor());
        holder.item_start_button.setText("Start");

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

            item_parent.setOnClickListener(v1 -> mListener.onItemClick(getAdapterPosition()));
        }
    }
}
