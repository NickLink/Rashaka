package com.rashaka.fragments.main.home.weight.year;

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

@Layout(id = R.layout.view_year)
public class YearFragment extends BaseFragment implements YearView {

    private MainRouter myRouter;
    private YearPresenter mPresenter;

    public static YearFragment newInstance(int pos){
        YearFragment fragment = new YearFragment();
        return fragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new YearPresenter();
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
        mPresenter.setGraph(wList, mBar1, mBar2, mBar3, mBar4, mBar5, mBar6, mBar7, mBar8, mBar9, mBar10, mBar11, mBar12);
        mPresenter.setDays(dList, mText1, mText2, mText3, mText4, mText5, mText6, mText7, mText8, mText9, mText10, mText11, mText12);
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
//    @BindView(R.id.share_text)
//    TextView share_text;

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

    @BindView(R.id.p_bar_8) ProgressBar mBar8;

    @BindView(R.id.p_text_8) TextView mText8;

    @BindView(R.id.p_bar_9) ProgressBar mBar9;

    @BindView(R.id.p_text_9) TextView mText9;

    @BindView(R.id.p_bar_10) ProgressBar mBar10;

    @BindView(R.id.p_text_10) TextView mText10;

    @BindView(R.id.p_bar_11) ProgressBar mBar11;

    @BindView(R.id.p_text_11)  TextView mText11;

    @BindView(R.id.p_bar_12) ProgressBar mBar12;

    @BindView(R.id.p_text_12) TextView mText12;
}
