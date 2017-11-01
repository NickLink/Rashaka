package com.rashaka.fragments.main.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.recipe.favorite.FavoriteFragment;
import com.rashaka.fragments.main.recipe.latest.RecipeFragment;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;

import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_recipe)
public class RecipeBaseFragment extends BaseFragment implements RecipeBaseView {

    private MainRouter myRouter;
    private RecipeBasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new RecipeBasePresenter();
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

        mResultTabs.addTab(mResultTabs.newTab().setText("Latest"));
        mResultTabs.addTab(mResultTabs.newTab().setText("Favorites"));
        mResultTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(mViewPager);

        mResultTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mResultTabs.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {
        RecipeAdapter adapter = new RecipeAdapter(getChildFragmentManager());
        adapter.addFragment(new RecipeFragment(), "Latest");
        adapter.addFragment(new FavoriteFragment(), "Favorite");
        mViewPager.setAdapter(adapter);
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {

    }

    @BindView(R.id.result_tabs)
    TabLayout mResultTabs;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

}
