package rashakacom.rashaka.fragments.main.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.news.gallery.GalleryFragment;
import rashakacom.rashaka.fragments.main.news.latest.LatestFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_news)
public class NewsBaseFragment extends BaseFragment implements NewsBaseView {

    private MainRouter myRouter;
    private NewsBasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new NewsBasePresenter();
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_top);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mResultTabs.addTab(mResultTabs.newTab().setText(RaApp.getLabel(LangKeys.key_news)));
        mResultTabs.addTab(mResultTabs.newTab().setText(RaApp.getLabel(LangKeys.key_gallery)));
        mResultTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(mViewPager);

        mResultTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mResultTabs.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {
        NewsBaseAdapter adapter = new NewsBaseAdapter(getChildFragmentManager());
        adapter.addFragment(new LatestFragment(), LatestFragment.class.getSimpleName());
        adapter.addFragment(new GalleryFragment(), GalleryFragment.class.getSimpleName());
        mViewPager.setAdapter(adapter);
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {

    }

    @BindView(R.id.result_tabs)
    TabLayout mResultTabs;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

}
