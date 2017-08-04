package vn.danhtran.baseproject.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.cocosw.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverWolf on 08/07/2017.
 */

public class BottomSheetUtils {
    public static void getBottomSheet(Activity activity, List<String> menus,
                                      final OnMenuItemClick onMenuItemClick) {
        getBottomSheet(activity, menus, null, onMenuItemClick);
    }

    public static void getBottomSheet(Activity activity, List<String> menus,
                                      String title, final OnMenuItemClick onMenuItemClick) {
        BottomSheet.Builder builder = new BottomSheet.Builder(activity);
        if (!TextUtils.isEmpty(title)) builder.title(title);
        for (int i = 0; i < menus.size(); i++) {
            builder.sheet(i, menus.get(i));
        }
        builder.listener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuItemClick.onMenuItemClick(item);
                return true;
            }
        });
        builder.show();
    }

    public interface OnMenuItemClick {
        void onMenuItemClick(MenuItem item);
    }


    //Example for using
    //sub event click on item filter Movie or TV Show
   /* private void onClickFilterMovieOrTV(final MovieListItemData itemData) {
        List<String> menus = new ArrayList<>();
        subApi = (TypeMovieOrTVShow) itemData.getValue();
        menus.add(0, context.getString(R.string.movies));
        menus.add(1, context.getString(R.string.tv_shows));
        BottomSheetUtils.getBottomSheet(fragment.getBaseActivity(), menus, new BottomSheetUtils.OnMenuItemClick() {
            @Override
            public void onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        subApi = TypeMovieOrTVShow.MOVIE;
                        break;
                    case 1:
                        subApi = TypeMovieOrTVShow.TV_SHOW;
                        break;
                }
                itemData.setText(getNameMovieOrTV(subApi));
                itemData.setValue(subApi);
                notifyChangeFilter();
            }
        });
    }*/

}
