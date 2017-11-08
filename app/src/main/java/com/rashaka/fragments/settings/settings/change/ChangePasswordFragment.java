package com.rashaka.fragments.settings.settings.change;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;

import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_password)
public class ChangePasswordFragment extends BaseFragment implements ChangePasswordView {

    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private MainRouter myRouter;
    private ChangePasswordPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ChangePasswordPresenter();
        mPresenter.setView(this);
        mPresenter.setRouter(myRouter);
    }

    public void onBackButtonPressed() {
        Log.e(TAG, "ChangePasswordFragment ON BACK Pressed -> ");
        myRouter.onBackPressed();
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackButtonPressed());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPasswordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onChangePassword(
                        mOldPassEdittext.getText().toString(),
                        mNewPassEdittext.getText().toString(),
                        mRetypePassEdittext.getText().toString()
                );
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues() {
//        mTitle.setText(RaApp.getLabel(RestKeys.KEY_change_password));
//        mPasswordChangeText.setText(RaApp.getLabel(RestKeys.KEY_change_password));
    }

    @Override
    public void showErrorDialod(String s) {

    }

    @BindView(R.id.general_title)
    TextView mTitle;

    @BindView(R.id.new_pass_title)
    TextView mNewPassTitle;

    @BindView(R.id.old_pass_title)
    TextView mOldPassTitle;

    @BindView(R.id.retype_pass_title)
    TextView mRetypePassTitle;

    @BindView(R.id.old_pass_edittext)
    EditText mOldPassEdittext;

    @BindView(R.id.new_pass_edittext)
    EditText mNewPassEdittext;

    @BindView(R.id.retype_pass_edittext)
    EditText mRetypePassEdittext;

    @BindView(R.id.password_change_button)
    FrameLayout mPasswordChangeButton;

    @BindView(R.id.password_change_text)
    TextView mPasswordChangeText;

}
