package com.rashaka.fragments.main.home.weight.week;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.EmptyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.view_week)
public class WeekFragment extends BaseFragment implements WeekView {
    public static final String TAG = WeekFragment.class.getSimpleName();

    private MainRouter myRouter;
    private WeekPresenter mPresenter;

    public static WeekFragment newInstance(int pos) {
        WeekFragment fragment = new WeekFragment();
        return fragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new WeekPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            getActivity().setTitle("BARABAKA");
//
//            menu.clear();
//            //inflater.inflate(R.menu.shadow, menu);
//
//            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setHomeButtonEnabled(false);
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
//            }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    @Override
    public void setGraphData(List<Double> wList, List<String> dList) {
        mEmptyList.setVisibility(View.GONE);
        mWeightBack.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.VISIBLE);
        mPresenter.setGraph(wList, mBar1, mBar2, mBar3, mBar4, mBar5, mBar6, mBar7);
        mPresenter.setDays(dList, mText1, mText2, mText3, mText4, mText5, mText6, mText7);
    }

    @Override
    public void setEmptyView() {
        mWeightBack.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.GONE);
        mEmptyList.setVisibility(View.VISIBLE);
    }

    //    @BindView(R.id.share_title)
//    TextView share_title;
//
//    @BindView(R.id.progress_layout)
//    LinearLayout mProgressLayout;

    @BindView(R.id.weight_back)
    View mWeightBack;

    @BindView(R.id.progress_layout)
    LinearLayout mProgressLayout;

    @BindView(R.id.empty_list)
    EmptyListView mEmptyList;

    @BindView(R.id.p_bar_1) ProgressBar mBar1;

    @BindView(R.id.p_text_1) TextView mText1;

    @BindView(R.id.p_bar_2) ProgressBar mBar2;

    @BindView(R.id.p_text_2) TextView mText2;

    @BindView(R.id.p_bar_3) ProgressBar mBar3;

    @BindView(R.id.p_text_3) TextView mText3;

    @BindView(R.id.p_bar_4) ProgressBar mBar4;

    @BindView(R.id.p_text_4) TextView mText4;

    @BindView(R.id.p_bar_5) ProgressBar mBar5;

    @BindView(R.id.p_text_5)  TextView mText5;

    @BindView(R.id.p_bar_6) ProgressBar mBar6;

    @BindView(R.id.p_text_6) TextView mText6;

    @BindView(R.id.p_bar_7) ProgressBar mBar7;

    @BindView(R.id.p_text_7) TextView mText7;


}
