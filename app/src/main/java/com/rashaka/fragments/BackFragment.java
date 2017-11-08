package com.rashaka.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.rashaka.utils.helpers.structure.SuperPresenter;

/**
 * Created by User on 12.10.2017.
 */

public abstract class BackFragment extends BaseFragment  implements View.OnKeyListener {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onBackButtonPressed();
                return true;
            }
        }

        return false;
    }

    public abstract void onBackButtonPressed();

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return null;
    }
}
