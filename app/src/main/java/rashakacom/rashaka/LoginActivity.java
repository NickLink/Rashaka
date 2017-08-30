package rashakacom.rashaka;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.fragments.login.lang.LangFragment;
import rashakacom.rashaka.fragments.login.passw.PassFragment;
import rashakacom.rashaka.fragments.login.register.RegisterFragment;
import rashakacom.rashaka.fragments.login.signin.SignInFragment;
import rashakacom.rashaka.utils.Consts;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.rest.models.PartnersDataItem;

import static rashakacom.rashaka.utils.Consts.ANIMATION_LEFT;
import static rashakacom.rashaka.utils.Consts.ANIMATION_RIGHT;

public class LoginActivity extends AppCompatActivity implements LoginRouter {

    @BindView(R.id.login_container)
    FrameLayout loginContainer;
    @BindView(R.id.login_partner_image_0)
    ImageView loginImage_0;
    @BindView(R.id.login_partner_image_1)
    ImageView loginImage_1;
    @BindView(R.id.login_bottom_text)
    TextView loginBottomText;
    @BindView(R.id.login_parent_layout)
    ConstraintLayout mParentLayout;

    private LoginPresenter mPresenter;
    private FragmentManager fm;

    private LangFragment langFragment;
    private RegisterFragment registerFragment;
    private SignInFragment signInFragment;
    private PassFragment passFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_background);
        ButterKnife.bind(this);

        Support.setStatusBarColor(this, R.color.login_statusbar_color);
        mPresenter = new LoginPresenter(this);
        fm = getSupportFragmentManager();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //TODO Clear database
        RaApp.getBase().clearLabelCache();

        callLang();
    }

    @Override
    public void callLang() {
        if (fm.findFragmentByTag(Consts.TAG_LANG) == null) {
            langFragment = new LangFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.login_container, langFragment, Consts.TAG_LANG);
        transaction.commit();

    }

    @Override
    public void callRegister() {
        if (fm.findFragmentByTag(Consts.TAG_REG) == null) {
            registerFragment = new RegisterFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(R.id.login_container, registerFragment, Consts.TAG_REG);
        transaction.commit();
    }

    @Override
    public void callSignIn(int side) {
        if (fm.findFragmentByTag(Consts.TAG_SIGN) == null) {
            signInFragment = new SignInFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (side == ANIMATION_LEFT)
            transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        if (side == ANIMATION_RIGHT)
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(R.id.login_container, signInFragment, Consts.TAG_SIGN);
        transaction.commit();
    }

    @Override
    public void callForgotPass() {
        if (fm.findFragmentByTag(Consts.TAG_PASS) == null) {
            passFragment = new PassFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out);
        transaction.replace(R.id.login_container, passFragment, Consts.TAG_PASS);
        transaction.commit();
    }

    @Override
    public void setPartnersLogos(List<PartnersDataItem> list) {
        Picasso.with(this).load(list.get(0).getImage()).into(loginImage_0);
        Picasso.with(this).load(list.get(1).getImage()).into(loginImage_1);
    }

    @Override
    public void showError(String error) {
        Snackbar snackbar = Snackbar.make(mParentLayout, error, Snackbar.LENGTH_LONG);
        snackbar.setText(error);
        snackbar.setAction(error, null);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
    }

}
