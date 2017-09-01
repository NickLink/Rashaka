package rashakacom.rashaka;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.home.HomeBaseFragment;
import rashakacom.rashaka.fragments.main.news.NewsBaseFragment;
import rashakacom.rashaka.fragments.main.recipe.RecipeBaseFragment;
import rashakacom.rashaka.fragments.main.share.ShareBaseFragment;
import rashakacom.rashaka.fragments.settings.notification.NotificationFragment;
import rashakacom.rashaka.fragments.settings.profile.ProfileFragment;
import rashakacom.rashaka.fragments.settings.settings.SettingsFragment;
import rashakacom.rashaka.utils.Support;

public class MainActivity extends AppCompatActivity implements MainRouter, BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private BottomBar mBottomBar;
    private FragNavController mNavController;

    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private NotificationFragment notificationFragment;

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

        if (RaApp.getBase().getLangType().equals("ar")) {
            ViewCompat.setLayoutDirection(findViewById(R.id.main_parent_layout), ViewCompat.LAYOUT_DIRECTION_RTL);
        }

        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_abar_back);
        myToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_abar_settings));
        myToolbar.setNavigationOnClickListener(view -> onBackPressed());
        getSupportActionBar().setHomeButtonEnabled(true);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.selectTabAtPosition(INDEX_PLUS);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_profile:
                mNavController.switchTab(INDEX_PROFILE);
                ;
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_notification:
                mNavController.switchTab(INDEX_NOTIFICATION);
                Toast.makeText(this, "Notification selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                mNavController.switchTab(INDEX_SETTINGS);
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            if (mNavController.getCurrentFrag() instanceof ProfileFragment ||
                    mNavController.getCurrentFrag() instanceof SettingsFragment ||
                    mNavController.getCurrentFrag() instanceof NotificationFragment) {
                mBottomBar.selectTabAtPosition(INDEX_HOME);
            } else
                finish();
            //super.onBackPressed();
        }
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
                return new HomeBaseFragment();
            case INDEX_NEWS:
                return new NewsBaseFragment();
            case INDEX_PLUS:
                return new HomeBaseFragment();
            case INDEX_SHARE:
                return new ShareBaseFragment();
            case INDEX_RECIPE:
                return new RecipeBaseFragment();
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
        Snackbar snackbar = Snackbar.make(mParentLayout, error, Snackbar.LENGTH_LONG);
        snackbar.setText(error);
        snackbar.setAction(error, null);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
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

}
