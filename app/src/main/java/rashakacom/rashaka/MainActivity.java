package rashakacom.rashaka;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.ButterKnife;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main_home.HomeBaseFragment;
import rashakacom.rashaka.utils.Support;

public class MainActivity extends AppCompatActivity implements MainRouter, BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private BottomBar mBottomBar;
    private FragNavController mNavController;

    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_NEWS = FragNavController.TAB2;
    private final int INDEX_PLUS = FragNavController.TAB3;
    private final int INDEX_SHARE = FragNavController.TAB4;
    private final int INDEX_RECIPE = FragNavController.TAB5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Support.setStatusBarColor(this, R.color.main_statusbar_color);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.selectTabAtPosition(INDEX_PLUS);
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_content)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .build();

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
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
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
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return new HomeBaseFragment();
            case INDEX_NEWS:
                return new HomeBaseFragment();
                //return FavoritesFragment.newInstance(0);
            case INDEX_PLUS:
                return new HomeBaseFragment();
                //return NearbyFragment.newInstance(0);
            case INDEX_SHARE:
                return new HomeBaseFragment();
                //return FriendsFragment.newInstance(0);
            case INDEX_RECIPE:
                return new HomeBaseFragment();
                //return FoodFragment.newInstance(0);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }
}
