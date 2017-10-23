package rashakacom.rashaka.fragments.main.plus.sleep;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.fragments.main.plus.sleep.dialogs.DateDialog;
import rashakacom.rashaka.fragments.main.plus.sleep.dialogs.TimeDialog;
import rashakacom.rashaka.system.lang.LangKeys;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_plus_sleep)
public class SleepLogFragment extends BaseFragment implements SleepLogView {

    private MainRouter myRouter;
    private SleepLogPresenter mPresenter;
    private IncludedLayout mLayoutStart, mLayoutEnd;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new SleepLogPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
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

        mLayoutStart = new IncludedLayout();
        mLayoutEnd = new IncludedLayout();

        ButterKnife.bind(mLayoutStart, mSleepStart );
        ButterKnife.bind(mLayoutEnd, mSleepEnd );

        mLayoutStart.mItemTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomDialog = new TimeDialog();
                bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
            }
        });
        mLayoutEnd.mItemTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomDialog = new TimeDialog();
                bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
            }
        });

        mLayoutStart.mItemDateParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomDialog = new DateDialog();
                bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
            }
        });

        mLayoutEnd.mItemDateParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomDialog = new DateDialog();
                bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
            }
        });

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValues() {
        mLayoutStart.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_start));
        mLayoutEnd.mItemTopText.setText(RaApp.getLabel(LangKeys.key_sleep_end));
        mLogText.setText(RaApp.getLabel(LangKeys.key_log_sleep));

    }

    @Override
    public void setStartSleep(String startSleep) {
        mLayoutStart.mItemBottomText.setText(startSleep);
    }

    @Override
    public void setEndSleep(String endSleep) {
        mLayoutEnd.mItemBottomText.setText(endSleep);
    }

    @BindView(R.id.log_sleep_start)
    View mSleepStart;

    @BindView(R.id.log_sleep_end)
    View mSleepEnd;

    @BindView(R.id.log_button)
    FrameLayout mLogButton;

    @BindView(R.id.log_text)
    TextView mLogText;


    static class IncludedLayout {

        @BindView( R.id.item_date_parent )
        LinearLayout mItemDateParent;

        @BindView( R.id.item_icon )
        ImageView mItemIcon;

        @BindView( R.id.item_top_text )
        TextView mItemTopText;

        @BindView( R.id.item_bottom_text )
        TextView mItemBottomText;

        @BindView( R.id.item_time_text )
        TextView mItemTimeText;
    }

}
