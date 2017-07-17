package vn.danhtran.baseproject.activity.full;


import vn.danhtran.baseproject.activity.BaseAppCompatActivity;

/**
 * Created by SilverWolf on 13/06/2017.
 */

public class FullActivityHandler {
    private BaseAppCompatActivity baseActivity;
    private String tagFragment;

    public FullActivityHandler(String tagFragment, BaseAppCompatActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.tagFragment = tagFragment;

        init();
    }

    private void init() {
        baseActivity.addMyFragment(tagFragment, null, null);
    }
}
