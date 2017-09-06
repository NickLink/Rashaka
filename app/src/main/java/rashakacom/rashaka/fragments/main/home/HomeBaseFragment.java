package rashakacom.rashaka.fragments.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.main_base_frag)
public class HomeBaseFragment extends BaseFragment implements HomeBaseView {

    private MainRouter myRouter;
    private HomeBasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new HomeBasePresenter();
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

        mButtonExercise.setOnClickListener(view1 -> {
            mPresenter.onExerciseClick();
        });

        mButtonWeight.setOnClickListener(view12 -> {
            mPresenter.onWeightClick();
        });

        mButtonTips.setOnClickListener(view13 -> mPresenter.onTipsClick());

        mButtonBMI.setOnClickListener(view14 -> mPresenter.onBMIClick());
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
        mStatusWeightText.setText(RaApp.getLabel("wwwwwwww"));

        mButtonExerciseText.setText(RaApp.getLabel("key_track_exercise"));
        mButtonWeightText.setText(RaApp.getLabel("key_track_weight"));
        mButtonTipsText.setText(RaApp.getLabel("key_fitness_tips"));
        mButtonBMIText.setText(RaApp.getLabel("key_my_bmi"));

    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

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
