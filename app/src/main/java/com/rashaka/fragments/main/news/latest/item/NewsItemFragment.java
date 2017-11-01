package com.rashaka.fragments.main.news.latest.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashaka.domain.news.NewsItem;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.Consts;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;

import com.rashaka.utils.helpers.structure.SuperPresenter;

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
        args.putString(Consts._ID, id);
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Log.e("TAG ", "onViewCreated");
        if (getArguments() != null) {
            Log.e("TAG ", "bundle != null");
            String id = getArguments().getString(Consts._ID);
            if (!TextUtils.isEmpty(id)) {
                Log.e("TAG ", "isEmpty(id) - > " + id);
                mPresenter.loadData(id);
            }
        }

        mItemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Click on share event
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
