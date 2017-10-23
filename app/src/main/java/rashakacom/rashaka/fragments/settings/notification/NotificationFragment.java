package rashakacom.rashaka.fragments.settings.notification;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.CustomLayoutManager;
import rashakacom.rashaka.domain.fake_models.FakeDataSource;
import rashakacom.rashaka.domain.fake_models.FakeNotification;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_notification)
public class NotificationFragment extends BaseFragment implements NotificationView {

    private MainRouter myRouter;
    private NotificationPresenter mPresenter;
    List<FakeNotification> list;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new NotificationPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        list = FakeDataSource.getFakeNotificationList();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager mLayoutManager = new CustomLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.hasFixedSize();

        mRecyclerView.setAdapter(new NotificationAdapter(getActivity(), list, pos -> {
            //TODO Notification item Click
            Toast.makeText(getActivity(), "DO NOT TOUCH -> " + pos, Toast.LENGTH_SHORT).show();
        }));
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    //    @BindView(R.id.share_title)
//    TextView share_title;
//
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

}
