package rashakacom.rashaka.fragments.main.news.latest.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.domain.news.NewsItem;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.Consts;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_news_latest_item)
public class NewsItemFragment extends BaseFragment implements NewsItemView {

    private MainRouter myRouter;
    private NewsItemPresenter mPresenter;

    public static NewsItemFragment newInstanse(String id){
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putString(Consts.NEWS_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new NewsItemPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Log.e("TAG ", "onViewCreated");
        if (getArguments() != null) {
            Log.e("TAG ", "bundle != null");
            String id = getArguments().getString(Consts.NEWS_ID);
            if (!TextUtils.isEmpty(id)) {
                Log.e("TAG ", "isEmpty(id) - > " + id);
                mPresenter.loadData(id);
            }
        }

        mItemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewData(NewsItem item) {
        mItemTitle.setText(item.getTitle());
        Picasso.with(getActivity()).load(item.getImage()).into(mItemImage);
        mItemText.setText(item.getDescription());
    }

    @BindView(R.id.item_share)
    ImageView mItemShare;

    @BindView(R.id.item_title)
    TextView mItemTitle;

    @BindView(R.id.item_image)
    ImageView mItemImage;

    @BindView(R.id.item_text)
    TextView mItemText;

}
