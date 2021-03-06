package com.rashaka;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.fragments.main.home.HomeBaseFragment;
import com.rashaka.fragments.main.news.NewsBaseFragment;
import com.rashaka.fragments.main.plus.PlusBaseFragment;
import com.rashaka.fragments.main.recipe.latest.RecipeFragment;
import com.rashaka.fragments.main.share.ShareBaseFragment;
import com.rashaka.fragments.settings.notification.NotificationFragment;
import com.rashaka.fragments.settings.profile.ProfileFragment;
import com.rashaka.fragments.settings.settings.SettingsFragment;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.database.DatabaseTask;
import com.rashaka.utils.database.SyncResult;
import com.rashaka.utils.dialogs.DialogBlueButton;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainRouter, BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomBar mBottomBar;
    private FragNavController mNavController;

    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private NotificationFragment notificationFragment;
    private HomeBaseFragment homeBaseFragment;

    private MainPresenter mPresenter;

    private Dialog loader;
    private boolean isKeyVisible, isVisible;

    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_NEWS = FragNavController.TAB2;
    private final int INDEX_PLUS = FragNavController.TAB3;
    private final int INDEX_SHARE = FragNavController.TAB4;
    private final int INDEX_RECIPE = FragNavController.TAB5;
    private final int INDEX_PROFILE = FragNavController.TAB6; //TODO Not exist
    private final int INDEX_SETTINGS = FragNavController.TAB7; //TODO Not exist
    private final int INDEX_NOTIFICATION = FragNavController.TAB8; //TODO Not exist

    @BindView(R.id.main_parent_layout)
    ConstraintLayout mParentLayout;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Support.setStatusBarColor(this, R.color.main_statusbar_color);
        mPresenter = new MainPresenter(this);

        if (RaApp.getBase().getLangType().equals(LangKeys.key_ar)) {
            ViewCompat.setLayoutDirection(findViewById(R.id.main_parent_layout), ViewCompat.LAYOUT_DIRECTION_RTL);
        }

        //TODO Database preparation part
        DatabaseTask dTask = new DatabaseTask(new SyncResult() {
            @Override
            public void SyncSuccess() {
                Log.e(TAG, "SyncSuccess");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getHomeBaseFragment() != null
                                && getHomeBaseFragment().isAdded()
                                && getHomeBaseFragment().isVisible()
                                ) {
                            getHomeBaseFragment().SyncRefresh();
                        }
                    }
                }, 1000);
            }

            @Override
            public void SyncError() {
                //TODO Need to show info that Synchronization Failed!
                Log.e(TAG, "SyncError");
            }
        });
        dTask.Prepare();

        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_abar_back);
        myToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_abar_settings));
        myToolbar.setNavigationOnClickListener(view -> onBackPressed());
        getSupportActionBar().setHomeButtonEnabled(true);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.selectTabAtPosition(INDEX_HOME);
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_content)
                .transactionListener(this)
                .rootFragmentListener(this, 8) //TODO WAS 5
                .build();

        mBottomBar.getTabAtPosition(INDEX_HOME).setTitle(RaApp.getLabel("key_home"));
        mBottomBar.getTabAtPosition(INDEX_NEWS).setTitle(RaApp.getLabel("key_news"));
        mBottomBar.getTabAtPosition(INDEX_SHARE).setTitle(RaApp.getLabel("key_share_app"));
        mBottomBar.getTabAtPosition(INDEX_RECIPE).setTitle(RaApp.getLabel("key_recipes"));

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        mNavController.switchTab(INDEX_HOME);
                        break;
                    case R.id.tab_news:
                        mNavController.switchTab(INDEX_NEWS);
                        break;
                    case R.id.tab_plus:
                        mNavController.switchTab(INDEX_PLUS);
                        break;
                    case R.id.tab_share:
                        mNavController.switchTab(INDEX_SHARE);
                        break;
                    case R.id.tab_recipes:
                        mNavController.switchTab(INDEX_RECIPE);
                        break;
                }
            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                mNavController.clearStack();
            }
        });
        mPresenter.startService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_main, menu);
        if (RaApp.getBase().getLangType().equals("ar")) {
            menu.findItem(R.id.action_profile).setTitle(Support.getArMenu("key_profile"));
            menu.findItem(R.id.action_notification).setTitle(Support.getArMenu("key_notifications"));
            menu.findItem(R.id.action_settings).setTitle(Support.getArMenu("key_settings"));
        } else {
            menu.findItem(R.id.action_profile).setTitle(RaApp.getLabel("key_profile"));
            menu.findItem(R.id.action_notification).setTitle(RaApp.getLabel("key_notifications"));
            menu.findItem(R.id.action_settings).setTitle(RaApp.getLabel("key_settings"));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_profile:
                mNavController.switchTab(INDEX_PROFILE);
                //Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                return true;
            // action with ID action_settings was selected
            case R.id.action_notification:
                mNavController.switchTab(INDEX_NOTIFICATION);
                //Toast.makeText(this, "Notification selected", Toast.LENGTH_SHORT).show();
                return true;
            // action with ID action_settings was selected
            case R.id.action_settings:
                mNavController.switchTab(INDEX_SETTINGS);
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            Log.e(TAG, "mNavController.popFragment()");
            mNavController.popFragment();
        } else {
            if (mNavController.getCurrentFrag() instanceof ProfileFragment ||
                    mNavController.getCurrentFrag() instanceof SettingsFragment ||
                    mNavController.getCurrentFrag() instanceof NotificationFragment) {
                Log.e(TAG, "SETTINGS FRAGMENT INSTANCE!!!!");
                mBottomBar.selectTabAtPosition(INDEX_HOME);
                mNavController.switchTab(INDEX_HOME);
            } else {
                Log.e(TAG, "Just finish()");
                //finish();
            }

            //super.onBackPressed();
        }
    }

    @Override
    public void RestartService() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void popFragment() {
        if (mNavController != null) {
            mNavController.popFragment();
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            //TODO Replace with Pashaka back icon
            //getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            //TODO Replace with Pashaka back icon
            //getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return getHomeBaseFragment(); //new HomeBaseFragment();
            case INDEX_NEWS:
                return new NewsBaseFragment();
            case INDEX_PLUS:
                return new PlusBaseFragment();
            case INDEX_SHARE:
                return new ShareBaseFragment();
            case INDEX_RECIPE:
                return new RecipeFragment(); //RecipeBaseFragment();
            case INDEX_PROFILE:
                return getProfileFragment();
            case INDEX_NOTIFICATION:
                return getNotificationFragment();
            case INDEX_SETTINGS:
                return getSettingsFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void showError(String error) {
        IsKeyboardVisible(mParentLayout);
        Log.e(TAG, "IsKeyboardVisible -> " + isKeyVisible);
        if (isKeyVisible) {
            showToast(error);
        } else {
            showSnackBar(error);
        }
    }

    private void showSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(mParentLayout, error, Snackbar.LENGTH_LONG);
        snackbar.setText(error);
        snackbar.setAction(error, null);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

    private void showToast(String error) {
        Log.e(TAG, "IsVisible -> " + isVisible);
        if (isVisible) {
            Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void showLoader() {
        loader = new Dialog(this);
        loader.setContentView(R.layout.dlg_loading);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loader.show();
    }

    @Override
    public void hideLoader() {
        if (loader != null && loader.isShowing()) {
            loader.dismiss();
        }
    }

    @Override
    public void showDialog(String title, String text, String button) {
        DialogBlueButton dialog = new DialogBlueButton(this, title, text, button);
        dialog.show();
    }

    private HomeBaseFragment getHomeBaseFragment() {
        if (homeBaseFragment == null) {
            homeBaseFragment = new HomeBaseFragment();
        }
        return homeBaseFragment;
    }

    private ProfileFragment getProfileFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    private SettingsFragment getSettingsFragment() {
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        return settingsFragment;
    }

    private NotificationFragment getNotificationFragment() {
        if (notificationFragment == null) {
            notificationFragment = new NotificationFragment();
        }
        return notificationFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isVisible = false;
    }

    @Override
    protected void onDestroy() {
        isVisible = false;
        mPresenter.onDestroy();
        mPresenter.stopService();
        Log.e(TAG, "onDestroy!");
        startActivity(new Intent(this, DummyActivity.class));
        super.onDestroy();
    }

    void IsKeyboardVisible(View contentView) {
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    isKeyVisible = true;
                } else {
                    // keyboard is closed
                    isKeyVisible = false;
                }
            }
        });
    }

}
