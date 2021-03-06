package com.rashaka.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.rashaka.utils.helpers.structure.SuperFragment;

/**
 * Created by User on 21.08.2017.
 */

public abstract class BaseFragment extends SuperFragment{
    public static final String ARGS_INSTANCE = "com.ncapdevi.sample.argsInstance";

    //    Button mButton;
    protected FragmentNavigation mFragmentNavigation;
//    int mInt = 0;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        if (args != null) {
//            mInt = args.getInt(ARGS_INSTANCE);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        mButton = (Button) view.findViewById(R.id.button);
//        return view;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
        void popFragment();
    }
}