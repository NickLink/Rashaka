package com.rashaka.fragments.main.home.bmi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_bmi)
public class BMIFragment extends BaseFragment implements BMIView {

    private MainRouter myRouter;
    private BMIPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new BMIPresenter();
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mSimulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSimulateClick();
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mPageTitle.setText(RaApp.getLabel(LangKeys.key_my_bmi));
        mCurrentBmi.setText(RaApp.getLabel(LangKeys.key_current_bmi));
        mSimulateButton.setText(RaApp.getLabel(LangKeys.key_simulate_bmi));
    }

    @Override
    public void setBmiValue(String value) {
        mBmiValue.setText(value);
    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

    @Override
    public void setGraphData(List<Double> wList, List<String> dList) {
        mPresenter.setGraph(wList, mBar1, mBar2, mBar3, mBar4, mBar5, mBar6, mBar7, mBar8, mBar9, mBar10, mBar11, mBar12);
        mPresenter.setValues(wList, mVal1, mVal2, mVal3, mVal4, mVal5, mVal6, mVal7, mVal8, mVal9, mVal10, mVal11, mVal12);
        mPresenter.setMonth(dList, mText1, mText2, mText3, mText4, mText5, mText6, mText7, mText8, mText9, mText10, mText11, mText12);

    }

    @BindView(R.id.page_title)
    TextView mPageTitle;

    @BindView(R.id.current_bmi_value)
    TextView mBmiValue;

    @BindView(R.id.current_bmi)
    TextView mCurrentBmi;

    @BindView(R.id.simulate_button)
    TextView mSimulateButton;




    @BindView(R.id.p_bar_1)
    ProgressBar mBar1;
    @BindView(R.id.p_bar_2)
    ProgressBar mBar2;
    @BindView(R.id.p_bar_3)
    ProgressBar mBar3;
    @BindView(R.id.p_bar_4)
    ProgressBar mBar4;
    @BindView(R.id.p_bar_5)
    ProgressBar mBar5;
    @BindView(R.id.p_bar_6)
    ProgressBar mBar6;
    @BindView(R.id.p_bar_7)
    ProgressBar mBar7;
    @BindView(R.id.p_bar_8)
    ProgressBar mBar8;
    @BindView(R.id.p_bar_9)
    ProgressBar mBar9;
    @BindView(R.id.p_bar_10)
    ProgressBar mBar10;
    @BindView(R.id.p_bar_11)
    ProgressBar mBar11;
    @BindView(R.id.p_bar_12)
    ProgressBar mBar12;

    @BindView(R.id.p_text_1)
    TextView mText1;
    @BindView(R.id.p_text_2)
    TextView mText2;
    @BindView(R.id.p_text_3)
    TextView mText3;
    @BindView(R.id.p_text_4)
    TextView mText4;
    @BindView(R.id.p_text_5)
    TextView mText5;
    @BindView(R.id.p_text_6)
    TextView mText6;
    @BindView(R.id.p_text_7)
    TextView mText7;
    @BindView(R.id.p_text_8)
    TextView mText8;
    @BindView(R.id.p_text_9)
    TextView mText9;
    @BindView(R.id.p_text_10)
    TextView mText10;
    @BindView(R.id.p_text_11)
    TextView mText11;
    @BindView(R.id.p_text_12)
    TextView mText12;

    @BindView(R.id.p_val_1)
    TextView mVal1;
    @BindView(R.id.p_val_2)
    TextView mVal2;
    @BindView(R.id.p_val_3)
    TextView mVal3;
    @BindView(R.id.p_val_4)
    TextView mVal4;
    @BindView(R.id.p_val_5)
    TextView mVal5;
    @BindView(R.id.p_val_6)
    TextView mVal6;
    @BindView(R.id.p_val_7)
    TextView mVal7;
    @BindView(R.id.p_val_8)
    TextView mVal8;
    @BindView(R.id.p_val_9)
    TextView mVal9;
    @BindView(R.id.p_val_10)
    TextView mVal10;
    @BindView(R.id.p_val_11)
    TextView mVal11;
    @BindView(R.id.p_val_12)
    TextView mVal12;

}
