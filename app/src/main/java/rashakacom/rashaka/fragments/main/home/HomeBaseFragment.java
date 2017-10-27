package rashakacom.rashaka.fragments.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.calendar.OneWeek;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.main_base_frag)
public class HomeBaseFragment extends BaseFragment implements HomeBaseView {

    private static final String TAG = HomeBaseFragment.class.getSimpleName();
    private MainRouter myRouter;
    private HomeBasePresenter mPresenter;

    private long mTimeStamp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new HomeBasePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTimeStamp = mPresenter.startOfDay();
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

        mSwipeLayout.setOnRefreshListener(() -> mPresenter.readData(true, mTimeStamp));

        mButtonExercise.setOnClickListener(view1 -> {
            mPresenter.onExerciseClick();
        });

        mButtonWeight.setOnClickListener(view12 -> {
            mPresenter.onWeightClick();
        });

        mButtonTips.setOnClickListener(view13 -> mPresenter.onTipsClick());

        mButtonBMI.setOnClickListener(view14 -> mPresenter.onBMIClick());

        mOneWeek.setOnSelectListener(startDay -> {
            Log.e(TAG, "onSelect -> " + startDay);
            mTimeStamp = startDay;
            mPresenter.readData(false, mTimeStamp);
        });

        //mStatusSteps.setOnClickListener(view15 -> mPresenter.onStepsClick());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.readData(false, mTimeStamp);
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValue() {
        mProgressTimeTitle.setText(RaApp.getLabel("key_active_time"));
        mProgressCaloriesTitle.setText(RaApp.getLabel("key_calories"));
        mStatusDistanceText.setText(RaApp.getLabel("key_distance"));
        mStatusStepsText.setText(RaApp.getLabel("key_steps"));
        mStatusWeightText.setText(RaApp.getLabel("key_weight"));

        mButtonExerciseText.setText(RaApp.getLabel("key_track_exercise"));
        mButtonWeightText.setText(RaApp.getLabel("key_track_weight"));
        mButtonTipsText.setText(RaApp.getLabel("key_fitness_tips"));
        mButtonBMIText.setText(RaApp.getLabel("key_my_bmi"));

    }

    @Override
    public boolean isRefreshing(){
        return mSwipeLayout.isRefreshing();
    }

    @Override
    public void showDialogSetStepsGoal(String title, String text, String button) {
        myRouter.showDialog(title, text, button);
    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

    @Override
    public void stopRefresh() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void setActiveTime(@NonNull String time, int progress) {
        mProgressTimeValue.setText(Support.getUpperCase(time, RaApp.getLabel(LangKeys.key_short_hours)));
        mProgressTime.setProgress(progress);
    }

    @Override
    public void setCalories(@NonNull String calories, int progress) {
        mProgressCaloriesValue.setText(Support.getUpperCase(calories, RaApp.getLabel(LangKeys.key_short_calories)));
        mProgressCalories.setProgress(progress);
    }

    @Override
    public void setDistance(@NonNull String distance) {
        mStatusDistanceValue.setText(Support.getUpperCase(distance, " " + RaApp.getLabel(LangKeys.key_km)));
    }

    @Override
    public void setSteps(@NonNull String steps) {
        mStatusStepsValue.setText(steps);
    }

    @Override
    public void setWeight(@NonNull String weight) {
        mStatusWeightValue.setText(Support.getUpperCase(weight, " " + RaApp.getLabel(LangKeys.key_kg)));
    }

    @BindView(R.id.one_week)
    OneWeek mOneWeek;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    //TODO Progress bars definition
    @BindView(R.id.progress_time)
    ProgressBar mProgressTime;
    @BindView(R.id.progress_calories)
    ProgressBar mProgressCalories;

    @BindView(R.id.progress_time_title)
    TextView mProgressTimeTitle;
    @BindView(R.id.progress_time_value)
    TextView mProgressTimeValue;

    @BindView(R.id.progress_calories_title)
    TextView mProgressCaloriesTitle;
    @BindView(R.id.progress_calories_value)
    TextView mProgressCaloriesValue;


    //TODO Status button definition
    @BindView(R.id.main_status_distance)
    FrameLayout mStatusDistance;
    @BindView(R.id.main_status_steps)
    FrameLayout mStatusSteps;
    @BindView(R.id.main_status_weight)
    FrameLayout mStatusWeight;

    @BindView(R.id.main_status_distance_title)
    TextView mStatusDistanceText;
    @BindView(R.id.main_status_steps_title)
    TextView mStatusStepsText;
    @BindView(R.id.main_status_weight_title)
    TextView mStatusWeightText;

    @BindView(R.id.main_status_distance_value)
    TextView mStatusDistanceValue;
    @BindView(R.id.main_status_steps_value)
    TextView mStatusStepsValue;
    @BindView(R.id.main_status_weight_value)
    TextView mStatusWeightValue;

    @BindView(R.id.main_status_distance_up)
    ImageView mStatusDistanceUp;
    @BindView(R.id.main_status_steps_up)
    ImageView mStatusStepsUp;
    @BindView(R.id.main_status_weight_up)
    ImageView mStatusWeightUp;


    //TODO Bottom Buttons definition
    @BindView(R.id.main_button_exercise)
    FrameLayout mButtonExercise;
    @BindView(R.id.main_button_weight)
    FrameLayout mButtonWeight;
    @BindView(R.id.main_button_tips)
    FrameLayout mButtonTips;
    @BindView(R.id.main_button_bmi)
    FrameLayout mButtonBMI;

    @BindView(R.id.main_button_exercise_text)
    TextView mButtonExerciseText;
    @BindView(R.id.main_button_weight_text)
    TextView mButtonWeightText;
    @BindView(R.id.main_button_tips_text)
    TextView mButtonTipsText;
    @BindView(R.id.main_button_bmi_text)
    TextView mButtonBMIText;


}
