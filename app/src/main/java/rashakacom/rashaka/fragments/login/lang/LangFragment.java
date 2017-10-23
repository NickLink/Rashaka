package rashakacom.rashaka.fragments.login.lang;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.utils.helpers.structure.SuperFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

import static rashakacom.rashaka.utils.Consts.ANIMATION_LEFT;
import static rashakacom.rashaka.utils.Consts.LANG_AR;
import static rashakacom.rashaka.utils.Consts.LANG_EN;

/**
 * Created by User on 17.08.2017.
 */

@Layout(id = R.layout.log_item_lang)
public class LangFragment extends SuperFragment implements LangView {
    private static final String TAG = LangFragment.class.getSimpleName();

    @BindView(R.id.lang_ar_button)
    FrameLayout mArLang;

    @BindView(R.id.lang_en_button)
    FrameLayout mEnLang;

    private LoginRouter myRouter;
    private LangPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "LangFragment onAttach ");
        myRouter = (LoginRouter) getActivity();
        mPresenter = new LangPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "LangFragment onActivityCreated ");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mArLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onLangSelected(LANG_AR);
            }
        });

        mEnLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onLangSelected(LANG_EN);
            }
        });
        Log.e(TAG, "LangFragment onViewCreated");
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goReg() {
        myRouter.callRegister();
    }

    @Override
    public void goSign() {
        myRouter.callSignIn(ANIMATION_LEFT);
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG, "LangFragment onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e(TAG, "LangFragment onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e(TAG, "LangFragment onStop");
        mPresenter.onStop();
    }
}
