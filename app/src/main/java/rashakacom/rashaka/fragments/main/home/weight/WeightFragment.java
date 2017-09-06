package rashakacom.rashaka.fragments.main.home.weight;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_home_weight)
public class WeightFragment extends BaseFragment implements WeightView {

    private MainRouter myRouter;
    private WeightPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new WeightPresenter();
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

        mWeightButtonPlus.setOnClickListener(view1 -> mPresenter.onPlusClick());

        mWeightButtonMinus.setOnClickListener(view12 -> mPresenter.onMinusClick());

        mWeightButtonSave.setOnClickListener(view13 -> mPresenter.onSaveClick());
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mPageTitle.setText(RaApp.getLabel("key_track_weight"));
        mCurrentWeightText.setText(RaApp.getLabel("key_current_weight"));
    }

    @Override
    public void setBigWeightText(String text) {
        mWeightBigText.setText(text);
    }


    @BindView(R.id.page_title)
    TextView mPageTitle;

    @BindView(R.id.current_weight_text)
    TextView mCurrentWeightText;

    @BindView(R.id.weight_button_plus)
    ImageView mWeightButtonPlus;

    @BindView(R.id.weight_button_minus)
    ImageView mWeightButtonMinus;

    @BindView(R.id.weight_big_text)
    TextView mWeightBigText;

    @BindView(R.id.weight_button_save)
    TextView mWeightButtonSave;



}
