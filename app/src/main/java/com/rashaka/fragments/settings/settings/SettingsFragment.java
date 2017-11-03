package com.rashaka.fragments.settings.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.BackFragment;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_settings)
public class SettingsFragment extends BackFragment implements SettingsView {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private MainRouter myRouter;
    private SettingsPresenter mPresenter;
    Disposable editTextSub;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new SettingsPresenter();
    }

    @Override
    public void onBackButtonPressed() {
        Log.e(TAG, "SettingsFragment ON BACK Pressed -> ");
        myRouter.onBackPressed();
        //mFragmentNavigation.popFragment();
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
        super.onCreateOptionsMenu(menu, inflater);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackButtonPressed());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, " onOptionsItemSelected -> " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackButtonPressed();
                //Toast.makeText(getActivity(),"Back button clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return false;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ActivityCompat.invalidateOptionsMenu(getActivity());
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mNotificationSwitch.setChecked(mPresenter.getNotificationStatus());

        mNotificationSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            //TODO Notification switch
            mPresenter.onNotificationChanged(b);
        });

        mRashakaPassword.setOnClickListener(view1 -> {
            //TODO Password change screen
            mPresenter.onChangePassword();
        });

        mLogoutButton.setOnClickListener(view12 -> {
            mPresenter.LogoutAndRestart(getActivity());
        });

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mGeneralTitle.setText(RaApp.getLabel(LangKeys.key_general));
        mAccountTitle.setText(RaApp.getLabel(LangKeys.key_rashaka_account));
        mPasswordTitle.setText(RaApp.getLabel(LangKeys.key_password));
        mNotificationTitle.setText(RaApp.getLabel(LangKeys.key_caps_notification));
        mNotificationText.setText(RaApp.getLabel(LangKeys.key_notification_desc));
        mProfileTitle.setText(RaApp.getLabel(LangKeys.key_profile));
        mUserinfoTitle.setText(RaApp.getLabel(LangKeys.key_user_info));
        mLogoutButtonText.setText(RaApp.getLabel(LangKeys.key_logout));
    }

    @Override
    public void setAccount(String account) {
        mRashakaEmail.setText(account);
    }

    @Override
    public void setUserInfo(String userInfo) {
        if (editTextSub != null && !editTextSub.isDisposed())
            editTextSub.dispose();
        mUserinfoText.setText(userInfo);
        editTextSub = RxTextView.textChanges(mUserinfoText)
//                .skip(mUserinfoText.length())
//                .filter(charSequence -> charSequence.length() > 3)
                .debounce(800, TimeUnit.MILLISECONDS)
                .skip(1)
                .subscribe(charSequence -> {
                    mPresenter.onUserInfoEditEnd(charSequence.toString());
                });
    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (editTextSub != null && !editTextSub.isDisposed())
            editTextSub.dispose();
    }

    @BindView(R.id.general_title)
    TextView mGeneralTitle;

    @BindView(R.id.account_title)
    TextView mAccountTitle;

    @BindView(R.id.rashaka_email)
    TextView mRashakaEmail;

    @BindView(R.id.password_title)
    TextView mPasswordTitle;

    @BindView(R.id.password_layout)
    LinearLayout mRashakaPassword;

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
    EditText mUserinfoText;

    @BindView(R.id.logout_button)
    FrameLayout mLogoutButton;

    @BindView(R.id.logout_button_text)
    TextView mLogoutButtonText;
}
