package com.rashaka.fragments.main.recipe.latest.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.recipes.RecipeItem;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.Consts;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_recipe_item)
public class RecipeItemFragment extends BaseFragment implements RecipeItemView {

    private MainRouter myRouter;
    private RecipeItemPresenter mPresenter;

    public static RecipeItemFragment newInstanse(String id){
        RecipeItemFragment fragment = new RecipeItemFragment();
        Bundle args = new Bundle();
        args.putString(Consts._ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new RecipeItemPresenter();
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Log.e("TAG ", "onViewCreated");
        if (getArguments() != null) {
            Log.e("TAG ", "bundle != null");
            String id = getArguments().getString(Consts._ID);
            if (!TextUtils.isEmpty(id)) {
                Log.e("TAG ", "isEmpty(id) - > " + id);
                mPresenter.loadData(id);
            }
        }

        mItemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Click on share event
                mPresenter.onShareClick(getActivity());
            }
        });
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewData(RecipeItem item) {
        mItemTitle.setText(item.getTitle());
        Picasso.with(getActivity()).load(item.getImage()).into(mItemImage);
        mIngredientsTitle.setText(RaApp.getLabel(LangKeys.key_ingredients));
        mPresenter.showWebView(mIngredientsText, item.getReceipt());
        mDirectionTitle.setText(RaApp.getLabel(LangKeys.key_directions));
        mPresenter.showWebView(mDirectionText, item.getDirections());
    }

    @BindView(R.id.item_share)
    ImageView mItemShare;

    @BindView(R.id.item_title)
    TextView mItemTitle;

    @BindView(R.id.item_image)
    ImageView mItemImage;

    @BindView(R.id.ingredients_title)
    TextView mIngredientsTitle;

    @BindView(R.id.ingredients_text)
    WebView mIngredientsText;

    @BindView(R.id.direction_title)
    TextView mDirectionTitle;

    @BindView(R.id.direction_text)
    WebView mDirectionText;
}
