package com.rashaka.fragments.main.home.weight;

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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.pager.WrapContentViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_weight)
public class WeightFragment extends BaseFragment implements WeightView {

    private MainRouter myRouter;
    private WeightPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new WeightPresenter();
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

        mWeightButtonPlus.setOnClickListener(view1 -> mPresenter.onPlusClick());
        mWeightButtonMinus.setOnClickListener(view12 -> mPresenter.onMinusClick());
        mWeightButtonSave.setOnClickListener(view13 -> mPresenter.onSaveClick());


        mResultTabs.addTab(mResultTabs.newTab().setText(RaApp.getLabel(LangKeys.key_week)));
        mResultTabs.addTab(mResultTabs.newTab().setText(RaApp.getLabel(LangKeys.key_month)));
        mResultTabs.addTab(mResultTabs.newTab().setText(RaApp.getLabel(LangKeys.key_years)));
        mResultTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        mPresenter.setupViewPager(mViewPager, getChildFragmentManager()); //getActivity()

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
                mViewPager.reMeasureCurrentPage(mViewPager.getCurrentItem());
                mResultTabs.getTabAt(position).select();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPresenter.setSeekValue(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void recreatePager(){
        mPresenter.setupViewPager(mViewPager, getChildFragmentManager());
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mPageTitle.setText(RaApp.getLabel("key_track_weight"));
        mCurrentWeightText.setText(RaApp.getLabel("key_current_weight"));
    }

    @Override
    public void setSeekBarValue(int value) {
        mSeekBar.setProgress(value);
    }

    @Override
    public void setBigWeightText(String text) {
        mWeightBigText.setText(text);
    }


    @BindView(R.id.page_title)
    TextView mPageTitle;

    @BindView(R.id.current_weight_text)
    TextView mCurrentWeightText;

    @BindView(R.id.weight_button_plus)
    ImageView mWeightButtonPlus;

    @BindView(R.id.weight_button_minus)
    ImageView mWeightButtonMinus;

    @BindView(R.id.weight_big_text)
    TextView mWeightBigText;

    @BindView(R.id.weight_button_save)
    TextView mWeightButtonSave;

    @BindView(R.id.result_tabs)
    TabLayout mResultTabs;

    @BindView(R.id.viewpager)
    WrapContentViewPager mViewPager;

    @BindView(R.id.seekBar)
    SeekBar mSeekBar;


}
