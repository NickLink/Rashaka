package rashakacom.rashaka.fragments.main.plus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_main_plus)
public class PlusBaseFragment extends BaseFragment implements PlusBaseView {

    private MainRouter myRouter;
    private PlusBasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new PlusBasePresenter();
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

        mPlusButtonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onDrinkClick();
            }
        });

        mPlusButtonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSleepClick();
            }
        });

        mPlusButtonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onFoodClick();
            }
        });


    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        mFragmentNavigation.pushFragment(fragment);
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setViewsValues() {
        mFoodText.setText(RaApp.getLabel(LangKeys.key_log_food));
        mSleepText.setText(RaApp.getLabel(LangKeys.key_log_sleep));
        mDrinkText.setText(RaApp.getLabel(LangKeys.key_drink_alarm));
    }

    @BindView(R.id.plus_button_drink)
    LinearLayout mPlusButtonDrink;

    @BindView(R.id.plus_button_sleep)
    LinearLayout mPlusButtonSleep;

    @BindView(R.id.plus_button_food)
    LinearLayout mPlusButtonFood;

    @BindView(R.id.drink)
    TextView mDrinkText;

    @BindView(R.id.sleep)
    TextView mSleepText;

    @BindView(R.id.food)
    TextView mFoodText;


}
