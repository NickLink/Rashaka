package com.rashaka.fragments.main.home.bmi.web;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.fragments.BaseFragment;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Utility;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_tips)
public class WebBMIFragment extends BaseFragment implements WebBMIView {

    private MainRouter myRouter;
    private WebBMIPresenter mPresenter;

    public static WebBMIFragment newInstance(String link){
        WebBMIFragment fragment = new WebBMIFragment();
        Bundle args = new Bundle();
        args.putString(Consts.KEY_TYPE, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new WebBMIPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null){
            String link = getArguments().getString(Consts.KEY_TYPE);
            if(!TextUtils.isEmpty(link)){
                mPresenter.setLoadLink(link);
            }
        }
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

        mWebView.setWebViewClient(new Utility.MyWebViewClient());
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
            return false;
        });

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    @Override
    public void loadLink(String link) {
        if (!TextUtils.isEmpty(link) && mWebView != null)
            mWebView.loadUrl(link);
    }

    @BindView(R.id.webview)
    WebView mWebView;

//    @BindView(R.id.share_title)
//    TextView share_title;
//
//    @BindView(R.id.share_text)
//    TextView share_text;

}
