package rashakacom.rashaka.fragments.settings.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
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

@Layout(id = R.layout.fr_set_settings)
public class SettingsFragment extends BaseFragment implements SettingsView {

    private MainRouter myRouter;
    private SettingsPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new SettingsPresenter();
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

        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO Notification switch
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
        mGeneralTitle.setText(RaApp.getLabel("key_general"));
        mAccountTitle.setText(RaApp.getLabel("key_rashaka_account"));
        mPasswordTitle.setText(RaApp.getLabel("key_password"));
        mNotificationTitle.setText(RaApp.getLabel("key_caps_notification"));
        mProfileTitle.setText(RaApp.getLabel("key_profile"));
        mUserinfoTitle.setText(RaApp.getLabel("key_user_info"));
    }




    @BindView(R.id.general_title)
    TextView mGeneralTitle;

    @BindView(R.id.account_title)
    TextView mAccountTitle;

    @BindView(R.id.rashaka_email)
    EditText mRashakaEmail;

    @BindView(R.id.password_title)
    TextView mPasswordTitle;

    @BindView(R.id.rashaka_password)
    EditText mRashakaPassword;

    @BindView(R.id.notification_title)
    TextView mNotificationTitle;

    @BindView(R.id.notification_text)
    TextView mNotificationText;

    @BindView(R.id.notification_switch)
    SwitchCompat mNotificationSwitch;

    @BindView(R.id.profile_title)
    TextView mProfileTitle;

    @BindView(R.id.userinfo_title)
    TextView mUserinfoTitle;

    @BindView(R.id.userinfo_text)
    TextView mUserinfoText;


}
