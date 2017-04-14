
package vn.danhtran.baseproject.binding;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import vn.danhtran.baseproject.customview.DividerItemDecoration;
import vn.danhtran.baseproject.utils.DimensionUtils;
import vn.danhtran.customglide.GlideImageLoader;


/**
 * Created by vitcon on 2/20/16.
 */
public class CustomBinding {
    //size in dp
    @BindingAdapter({"bind:absParams", "bind:absWidth", "bind:absHeight"})
    public static void absParams(ImageView imageView, boolean absParams, int width, int height) {
        width = DimensionUtils.dpToPx(width);
        height = DimensionUtils.dpToPx(height);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
    }

    //size in dp
    @BindingAdapter({"bind:linearParams", "bind:linearWidth", "bind:linearHeight"})
    public static void linearParams(ImageView imageView, boolean linearParams, int width, int height) {
        width = DimensionUtils.dpToPx(width);
        height = DimensionUtils.dpToPx(height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
    }

    //size in dp
    @BindingAdapter({"bind:relativeParams", "bind:relativeWidth", "bind:relativeHeight"})
    public static void relativeParams(ImageView imageView, boolean relativeParams, int width, int height) {
        width = DimensionUtils.dpToPx(width);
        height = DimensionUtils.dpToPx(height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
    }

    //size in dp
    @BindingAdapter({"bind:setImage", "bind:width", "bind:height"})
    public static void setImage(ImageView imageView, String url, int width, int height) {
        //set default value
        width = DimensionUtils.dpToPx(width);
        height = DimensionUtils.dpToPx(height);
        if (url != null) {
            GlideImageLoader.getInstance().displayImage(url, imageView, width, height);
        }
    }

    //size in dp
    @BindingAdapter({"bind:setImage", "bind:width", "bind:height", "bind:isCircle"})
    public static void setImage(ImageView imageView, String url, int width, int height, boolean isCircle) {
        //set default value
        width = DimensionUtils.dpToPx(width);
        height = DimensionUtils.dpToPx(height);
        if (url != null) {
            if (isCircle)
                GlideImageLoader.getInstance().displayImageCircle(url, imageView, width, height);
            else GlideImageLoader.getInstance().displayImage(url, imageView, width, height);
        }
    }

    @BindingAdapter({"bind:setImage", "bind:radius"})
    public static void radiusImage(ImageView imageView, String url, int raidus) {
        if (url != null) {
            GlideImageLoader.getInstance().displayImageRounder(url, imageView, raidus);
        }
    }

    //height in DP
    @BindingAdapter("bind:heightOfView")
    public static void heightOfView(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = DimensionUtils.dpToPx(height);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("bind:linearManager")
    public static void linearManager(RecyclerView recyclerView, int _int) {
        switch (_int) {
            case 0:     //0 - horizontal
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                break;
            case 1:     //1 - vertical
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL, false));
                break;
            case 2:     //2 - no scroll vertical
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                break;
            case 3:     //2 - no scroll horizontal
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                });
                break;
            default:
                break;
        }
    }

    @BindingAdapter("bind:dividerItem")
    public static void dividerItemDecoration(RecyclerView recyclerView, int _int) {
        Context context = recyclerView.getContext();
        switch (_int) {
            case 0:
                recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));
                break;
            case 1:
                recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                break;
            default:
                break;
        }
    }

    @BindingAdapter("bind:hideKeyboadLostFocus")
    public static void closeKeyboard(EditText editText, boolean id) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    static private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}