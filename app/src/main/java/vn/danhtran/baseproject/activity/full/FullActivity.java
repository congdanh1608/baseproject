package vn.danhtran.baseproject.activity.full;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.databinding.ActivityFullBinding;

/**
 * Created by SilverWolf on 13/06/2017.
 */

public class FullActivity extends BaseAppCompatActivity {
    public static final String TAG_FRAGMENT = "TAG_FRAGMENT";
    private String tagFragment;
    private ActivityFullBinding activityFullBinding;

    public static Intent instance(Activity activity) {
        return new Intent(activity, FullActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_full;
    }

    @Override
    public void initUI() {
        //load bundle
        tagFragment = getIntent().getExtras().getString(TAG_FRAGMENT);

        activityFullBinding = (ActivityFullBinding) binding;
        tvTitle = activityFullBinding.tvTitle;

        FullActivityHandler fullActivityHandler = new FullActivityHandler(tagFragment, this);
        activityFullBinding.setHandler(fullActivityHandler);
        activityFullBinding.executePendingBindings();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() <= 1)
            this.finish();
        super.onBackPressed();
    }

    @Override
    public void initListener() {
        activityFullBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullActivity.this.finish();
            }
        });
    }
}
