package com.rashaka.fragments.main.share;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_share)
public class ShareBaseFragment extends BaseFragment implements ShareBaseView {

    private MainRouter myRouter;
    private ShareBasePresenter mPresenter;
    private ShareLayout mLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ShareBasePresenter();
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

        mLayout = new ShareLayout();
        ButterKnife.bind(mLayout, mShareItem);

        mLayout.mItemShareButton.setOnClickListener(view1 -> mPresenter.onShareClick(getActivity()));

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValue() {
        share_title.setText(RaApp.getLabel(LangKeys.key_share_app));
        mLayout.mItemShareTitle.setText(RaApp.getLabel(LangKeys.key_share_app_with_friends));
        mLayout.mItemShareText.setText(RaApp.getLabel(LangKeys.key_allows_to_share));
        mLayout.mItemShareButton.setText(RaApp.getLabel(LangKeys.key_share));
    }

    @BindView(R.id.share_title)
    TextView share_title;

    @BindView(R.id.share_item)
    View mShareItem;
//
//    @BindView(R.id.share_text)
//    TextView share_text;

    static class ShareLayout {

        @BindView(R.id.item_plus_button)
        ImageView mPlusIcon;

        @BindView(R.id.item_share_title)
        TextView mItemShareTitle;

        @BindView(R.id.item_share_text)
        TextView mItemShareText;

        @BindView(R.id.item_add_button)
        TextView mItemShareButton;
    }

}
