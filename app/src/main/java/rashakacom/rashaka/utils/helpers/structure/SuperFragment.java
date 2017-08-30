package rashakacom.rashaka.utils.helpers.structure;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;

/**
 * Created by User on 17.08.2017.
 */

public abstract class SuperFragment extends LifecycleFragment {

    private static final UUID sFragmentId = UUID.randomUUID();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) {
            return null;
        }
        Layout layout = (Layout) cls.getAnnotation(Layout.class);
        return inflater.inflate(layout.id(), null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onStart(getArguments());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        getPresenter().setView(this);
        getPresenter().setRouter(getActivity());
        getPresenter().onViewReady();
    }

    @Override
    public void onStop() {
        getPresenter().onStop();
        super.onStop();

    }

    @Override
    public void onDestroy() {
        getPresenter().onDestroy();
        super.onDestroy();
    }

    @NonNull
    public UUID getFragmentId() {
        return sFragmentId;
    }

    @NonNull
    public abstract SuperPresenter getPresenter();
}