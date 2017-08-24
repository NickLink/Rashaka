package rashakacom.rashaka.fragments.main_home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.main_base_frag)
public class HomeBaseFragment extends BaseFragment implements HomeBaseView{

    private MainRouter myRouter;
    private HomeBasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG", "RegisterFragment onAttach");
        myRouter = (MainRouter) getActivity();
        mPresenter = new HomeBasePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            getActivity().setTitle("BARABAKA");

            menu.clear();
            //inflater.inflate(R.menu.shadow, menu);

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

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {
        textView5.setText(one);
        textView4.setText(two);
        textView3.setText(three);
    }

    @BindView(R.id.textView5)
    TextView textView5;

    @BindView(R.id.textView4)
    TextView textView4;

    @BindView(R.id.textView3)
    TextView textView3;
}
