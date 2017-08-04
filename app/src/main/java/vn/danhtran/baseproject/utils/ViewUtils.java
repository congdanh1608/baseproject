package vn.danhtran.baseproject.utils;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by SilverWolf on 04/06/2017.
 */

public class ViewUtils {
    public static void showDialogFragment(DialogFragment dialogFragment, Fragment parent, String tag) {
        FragmentManager fragmentManager = parent.getActivity().getSupportFragmentManager();
        dialogFragment.setTargetFragment(parent, 1);
        dialogFragment.show(fragmentManager, tag);
    }
}
