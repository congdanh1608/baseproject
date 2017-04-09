package vn.danhtran.baseproject.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by Tam Huynh on 5/29/15.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected View rootView;
    protected FrameLayout frameLayout;
    protected ViewDataBinding binding;
    private View progressBar;

    public abstract int setLayout();

    public abstract void initUI();

    public abstract void initData();

    public abstract void initListener();

    public abstract void onConfigurationChanged();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    protected void setProgressBar(View view) {
        this.progressBar = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());
        int xml = setLayout();
        if (xml != 0) {
            binding = DataBindingUtil.inflate(inflater, xml, container, false);
            rootView = binding.getRoot();
        }

        initUI();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged();
    }

    public void showProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
