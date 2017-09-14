package rashakacom.rashaka.fragments.main.news.latest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.domain.news.NewsItem;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.news.latest.item.NewsItemFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.CustomLayoutManager;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_news_latest)
public class LatestFragment extends BaseFragment implements LatestView {

    private MainRouter myRouter;
    private LatestPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new LatestPresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mNewsRecyclerView.setLayoutManager(mLayoutManager);
        mNewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mNewsRecyclerView.hasFixedSize();
        mNewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mPresenter.onScrolled(recyclerView);
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void setAdapterData(List<NewsItem> list) {
        LatestRecyclerAdapter adapter = new LatestRecyclerAdapter(
                getActivity(),
                list,
                new LatestRecyclerAdapter.LatestNewsClick() {
                    @Override
                    public void cShare(int position) {
                        Toast.makeText(getActivity(), "Share -> " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void cMore(int position, String id) {
                        mFragmentNavigation.pushFragment(NewsItemFragment.newInstanse(id));
                        Toast.makeText(getActivity(), "More ID -> " + id, Toast.LENGTH_SHORT).show();
                    }
                });
        mNewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void addAdapterData(List<NewsItem> mData) {
        ((LatestRecyclerAdapter)mNewsRecyclerView.getAdapter()).addData(mData);
    }

    @BindView(R.id.latest_recycler_view)
    RecyclerView mNewsRecyclerView;

//
//    @BindView(R.id.textView4)
//    TextView textView4;
//
//    @BindView(R.id.textView3)
//    TextView textView3;
//    @BindView(R.id.news_grid_view)
//    GridView mNewsGridview;
}
