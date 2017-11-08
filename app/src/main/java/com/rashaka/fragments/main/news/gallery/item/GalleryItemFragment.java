package com.rashaka.fragments.main.news.gallery.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rashaka.domain.gallery.GalleryItemFull;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;

import com.rashaka.domain.gallery.Photo;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.Consts;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.views.CustomLayoutManager;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_news_gallery_item)
public class GalleryItemFragment extends BaseFragment implements GalleryItemView {

    private MainRouter myRouter;
    private GalleryItemPresenter mPresenter;

    public static GalleryItemFragment newInstanse(String id){
        GalleryItemFragment fragment = new GalleryItemFragment();
        Bundle args = new Bundle();
        args.putString(Consts._ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new GalleryItemPresenter();
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

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewData(GalleryItemFull item) {
        mItemTitle.setText(item.getTitle());
        mItemText.setText(item.getDescription());
        setAdapterData(item.getPhotos());

//        mItemTitle.setText(item.getTitle());
//        Picasso.with(getActivity())
//                .load(item.getImage())
//                .into(mItemImage);
//
//        mItemText.setText(item.getDescription());
    }

    public void setAdapterData(List<Photo> list) {
        PhotoRecyclerAdapter adapter = new PhotoRecyclerAdapter(
                getActivity(),
                list,
                new PhotoRecyclerAdapter.LatestNewsClick() {
                    @Override
                    public void cShare(int position) {
                        Toast.makeText(getActivity(), "Share -> " + position, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void cMore(int position, String id) {
                        mFragmentNavigation.pushFragment(GalleryItemFragment.newInstanse(id));
                        Toast.makeText(getActivity(), "More ID -> " + id, Toast.LENGTH_SHORT).show();
                    }
                });
        mRecyclerView.setAdapter(adapter);
    }


    @BindView(R.id.item_share)
    ImageView mItemShare;

    @BindView(R.id.item_title)
    TextView mItemTitle;

    @BindView(R.id.item_image)
    ImageView mItemImage;

    @BindView(R.id.item_text)
    TextView mItemText;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

}
