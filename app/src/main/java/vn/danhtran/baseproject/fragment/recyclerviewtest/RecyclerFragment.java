package vn.danhtran.baseproject.fragment.recyclerviewtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.databinding.RecyclerFragmentBinding;
import vn.danhtran.baseproject.fragment.BaseFragment;

/**
 * Created by SilverWolf on 06/04/2017.
 */
public class RecyclerFragment extends BaseFragment {
    private RecyclerFragmentBinding loginFragmentBinding;
    private RecyclerHandler loginHandler;

    public static RecyclerFragment instance() {
        RecyclerFragment loginFragment = new RecyclerFragment();
        return loginFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.recycler_fragment;
    }

    @Override
    public void initUI() {
        loginFragmentBinding = (RecyclerFragmentBinding) binding;

        loginHandler = new RecyclerHandler(loginFragmentBinding.swipe, this);
        loginFragmentBinding.setHandler(loginHandler);
        loginFragmentBinding.executePendingBindings();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onConfigurationChanged() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
