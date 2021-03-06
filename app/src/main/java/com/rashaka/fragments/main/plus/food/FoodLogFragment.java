package com.rashaka.fragments.main.plus.food;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.food.LogFoodItem;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.Utility;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.CustomLayoutManager;
import com.rashaka.utils.helpers.views.EmptyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_food)
public class FoodLogFragment extends BaseFragment implements FoodLogView {

    private MainRouter myRouter;
    private FoodLogPresenter mPresenter;
    private AddLayout mAddLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new FoodLogPresenter(myRouter);
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

        mAddLayout = new AddLayout();

        ButterKnife.bind(mAddLayout, mAddFoodLayout);

        mAddLayout.mFoodSpinner.setAdapter(mPresenter.mAdapter(getActivity()));
        mAddLayout.mFoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //mAddLayout.mItemDataText
        //mAddLayout.mItemEditText

        //TODO Click on log food
        mAddLayout.mItemLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideKeyboard(getActivity());
                mPresenter.onLogClick(
                        (String) mAddLayout.mFoodSpinner.getSelectedItem(),
                        mAddLayout.mItemEditText.getText().toString(),
                        Support.GetDateTimeForAPI(Support.DATE_FORMAT_FULL));
            }
        });

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.hasFixedSize();
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

    @Override
    public void setLangValues() {
        mTitle.setText(RaApp.getLabel(LangKeys.key_log_food));
        mAddLayout.mItemLogButton.setText(RaApp.getLabel(LangKeys.key_log));
    }

    @Override
    public void setDate(String date) {
        mAddLayout.mItemDataText.setText(date);
    }

    @Override
    public void clearFoodInput() {
        mAddLayout.mItemEditText.setText("");
        mAddLayout.mItemEditText.clearFocus();
        mPresenter.refreshLogFoodList();
    }

    @Override
    public void enableFoodLogButton(boolean enable) {
        mAddLayout.mItemLogButton.setEnabled(enable);
    }

    @Override
    public void setAdapterData(List<LogFoodItem> list) {
        if (list != null && list.size() > 0) {//
            Utility.setCollapseScroll(mToolbarLayout);
            mEmptyList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            FoodLogAdapter adapter = new FoodLogAdapter(getActivity(),
                    list,
                    new FoodLogAdapter.onClick() {
                        @Override
                        public void onClick(int position) {
                        }

                        @Override
                        public void onClickId(int position, String id) {
                        }
                    });
            mRecyclerView.setAdapter(adapter);
        } else {
            Utility.setSnagScroll(mToolbarLayout);
            mEmptyList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void addAdapterData(List<LogFoodItem> list) {
        if (mRecyclerView.getAdapter() != null)
            ((FoodLogAdapter) mRecyclerView.getAdapter()).addData(list);
    }

    //    @BindView(R.id.log_button)
//    FrameLayout mLogButton;
//
//    @BindView(R.id.log_text)
//    TextView mLogText;
    @BindView(R.id.colapsing_title)
    CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.add_food_layout)
    View mAddFoodLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_list)
    EmptyListView mEmptyList;
//
//    @BindView(R.id.share_text)
//    TextView share_text;

    static class AddLayout {
        @BindView(R.id.item_log_button)
        TextView mItemLogButton;

        @BindView(R.id.food_icon)
        ImageView mFoodIcon;

        @BindView(R.id.food_spinner)
        Spinner mFoodSpinner;

        @BindView(R.id.item_datatext)
        TextView mItemDataText;

        @BindView(R.id.item_edittext)
        EditText mItemEditText;
    }

}
