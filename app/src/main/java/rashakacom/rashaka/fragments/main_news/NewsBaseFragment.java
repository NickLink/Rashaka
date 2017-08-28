package rashakacom.rashaka.fragments.main_news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main_news_item.NewsItemFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.rest.fake_models.Article;

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
        Log.e("TAG", "RegisterFragment onAttach");
        myRouter = (MainRouter) getActivity();
        mPresenter = new NewsBasePresenter();
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

        mNewsGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mFragmentNavigation.pushFragment(new NewsItemFragment());
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }



    @BindView(R.id.news_grid_view)
    GridView mNewsGridview;

    @Override
    public void setAdapterData(List<Article> list) {
        NewsGridAdapter adapter = new NewsGridAdapter(getActivity(), list);
        mNewsGridview.setAdapter(adapter);
    }


//
//    @BindView(R.id.textView4)
//    TextView textView4;
//
//    @BindView(R.id.textView3)
//    TextView textView3;
}
