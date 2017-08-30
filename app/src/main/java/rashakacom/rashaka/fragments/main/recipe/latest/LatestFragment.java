package rashakacom.rashaka.fragments.main.recipe.latest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.recipe.RecipeRecyclerAdapter;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.rest.fake_models.Article;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_recipe_favorite)
public class LatestFragment extends BaseFragment implements LatestView {

    private MainRouter myRouter;
    private LatestPresenter mPresenter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG", "RegisterFragment onAttach");
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

        mRecyclerFavorite.setHasFixedSize(true);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerFavorite.setLayoutManager(mLayoutManager);
        mRecyclerFavorite.setItemAnimator(new DefaultItemAnimator());
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @BindView(R.id.recycler_favorite)
    RecyclerView mRecyclerFavorite;


    @Override
    public void setAdapterData(List<Article> list) {
        Log.e("TAG", "setAdapterData(List<Article> list)");
        mRecyclerFavorite.setAdapter(new RecipeRecyclerAdapter(getActivity(), list));
    }

}
