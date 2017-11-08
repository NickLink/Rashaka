package com.rashaka.fragments.main.recipe.latest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.domain.recipes.RecipeItem;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.recipe.latest.item.RecipeItemFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.EmptyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_recipe_favorite)
public class RecipeFragment extends BaseFragment implements RecipeView {

    private MainRouter myRouter;
    private RecipePresenter mPresenter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new RecipePresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_top);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mPresenter.onScrolled(recyclerView);
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @BindView(R.id.empty_list)
    EmptyListView mEmptyList;

    @BindView(R.id.recycler_favorite)
    RecyclerView mRecyclerView;

    @Override
    public void setAdapterData(List<RecipeItem> list) {
        if (list != null && list.size() > 0) {
            mEmptyList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            RecipeAdapter adapter = new RecipeAdapter(getActivity(), list, new RecipeAdapter.onClick() {
                @Override
                public void onClick(int position) {
                }
                @Override
                public void onClickId(int position, String id) {
                    mFragmentNavigation.pushFragment(RecipeItemFragment.newInstanse(id));
                }
            });
            mRecyclerView.setAdapter(adapter);
        } else {
            mEmptyList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void addAdapterData(List<RecipeItem> mData) {
        if (mRecyclerView.getAdapter() != null)
            ((RecipeAdapter) mRecyclerView.getAdapter()).addData(mData);
    }
}
