package rashakacom.rashaka.fragments.settings.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.database.SharedViewModel;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_profile)
public class ProfileFragment extends BaseFragment implements ProfileView {

    private MainRouter myRouter;
    private ProfilePresenter mPresenter;
    private SharedViewModel model;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG", "RegisterFragment onAttach");
        myRouter = (MainRouter) getActivity();
        mPresenter = new ProfilePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);
        //model.select(new UserData());

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

        //TODO User info dialogs click
        mProfileGenderButton.setOnClickListener(view1 -> mPresenter.onGenderClick());
        mProfileDobButton.setOnClickListener(view16 -> mPresenter.onDobClick());
        mProfileHeightButton.setOnClickListener(view12 -> mPresenter.onHeightClick());
        mProfileWeightButton.setOnClickListener(view13 -> mPresenter.onWeightClick());
        mProfileWeightGButton.setOnClickListener(view14 -> mPresenter.onWeightGClick());
        mProfileStepsGButton.setOnClickListener(view15 -> mPresenter.onStepsGClick());


        model.getSelected().observe(this, o -> {
            if(!TextUtils.isEmpty(o.getSex())){
                mProfileGenderText.setText(o.getSex().equals("0")
                        ? RaApp.getLabel("key_male")
                        : RaApp.getLabel("key_female"));
            }
            if(!TextUtils.isEmpty(o.getBirthday())) {
                mProfileDobText.setText(o.getBirthday());
            }

            if (!TextUtils.isEmpty(o.getHight()))
                mProfileHeightText.setText(o.getHight());

            if(!TextUtils.isEmpty(o.getWeight())){
                mProfileWeightText.setText(o.getWeight());
            }

            mProfileWeightGText.setText("123");
            mProfileStepsGText.setText("9999");

        });

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValue() {
        mProfileUserInformation.setText(RaApp.getLabel("key_user_information"));
        mProfileGenderText.setText(RaApp.getLabel("key_gender"));
        mProfileDobText.setText(RaApp.getLabel("key_date_of_birth"));
        mProfileHeightText.setText(RaApp.getLabel("key_height"));
        mProfileWeightText.setText(RaApp.getLabel("key_weight"));
        mProfileWeightGText.setText(RaApp.getLabel("key_set_weight_goal"));
        mProfileStepsGText.setText(RaApp.getLabel("key_set_steps_goal"));

        mProfileWeeklySummary.setText(RaApp.getLabel("key_weekly_summary"));
        mProgressActiveMinsText.setText(RaApp.getLabel("key_average_daily_active_mins"));
        mProgressActiveMinsDef.setText(RaApp.getLabel("key_min"));
        mProgressStepsText.setText(RaApp.getLabel("key_average_daily_steps"));
        mProgressStepsDef.setText(RaApp.getLabel("key_caps_step"));
    }

    @Override
    public void setProfileBackground(String background) {
        Picasso.with(getActivity()).load(background).into(mProfileTopBackground);
    }

    @Override
    public void setProfileImage(String image) {
        Picasso.with(getActivity()).load(image).into(mProfileImage);
    }

    @Override
    public void setProfileInfo(String name, String email) {
        mProfileName.setText(name);
        mProfileEmail.setText(email);
    }

    @Override
    public void setActiveBar(int max, int progress) {

    }

    @Override
    public void setStepsBar(int max, int progress) {

    }

    @Override
    public void setBottomDialog(BottomSheetDialogFragment bottomDialog) {
        bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
    }


    @BindView(R.id.profile_gray_plus)
    ImageView mProfileGrayPlus;

    @BindView(R.id.profile_top_background)
    ImageView mProfileTopBackground;

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.profile_name)
    TextView mProfileName;

    @BindView(R.id.profile_email)
    TextView mProfileEmail;

    @BindView(R.id.profile_user_information)
    TextView mProfileUserInformation;

    //TODO Profile dialogs buttons
    @BindView(R.id.profile_gender_button)
    FrameLayout mProfileGenderButton;

    @BindView(R.id.profile_dob_button)
    FrameLayout mProfileDobButton;

    @BindView(R.id.profile_height_button)
    FrameLayout mProfileHeightButton;

    @BindView(R.id.profile_weight_button)
    FrameLayout mProfileWeightButton;

    @BindView(R.id.profile_weight_g_button)
    FrameLayout mProfileWeightGButton;

    @BindView(R.id.profile_steps_g_button)
    FrameLayout mProfileStepsGButton;

    //TODO Profile dialogs buttons text
    @BindView(R.id.profile_gender_text)
    TextView mProfileGenderText;

    @BindView(R.id.profile_dob_text)
    TextView mProfileDobText;

    @BindView(R.id.profile_height_text)
    TextView mProfileHeightText;

    @BindView(R.id.profile_weight_text)
    TextView mProfileWeightText;

    @BindView(R.id.profile_weight_g_text)
    TextView mProfileWeightGText;

    @BindView(R.id.profile_steps_g_text)
    TextView mProfileStepsGText;

    //TODO Weekly summary with progress
    @BindView(R.id.profile_weekly_summary)
    TextView mProfileWeeklySummary;

    @BindView(R.id.profile_active_mins_progress)
    ProgressBar mProgressActiveMins;

    @BindView(R.id.profile_active_mins_text)
    TextView mProgressActiveMinsText;

    @BindView(R.id.profile_active_mins_def)
    TextView mProgressActiveMinsDef;

    @BindView(R.id.profile_active_mins_value)
    TextView mProgressActiveMinsValue;

    @BindView(R.id.profile_steps_progress)
    ProgressBar mProgressSteps;

    @BindView(R.id.profile_steps_text)
    TextView mProgressStepsText;

    @BindView(R.id.profile_steps_def)
    TextView mProgressStepsDef;

    @BindView(R.id.profile_steps_value)
    TextView mProgressStepsValue;

}
