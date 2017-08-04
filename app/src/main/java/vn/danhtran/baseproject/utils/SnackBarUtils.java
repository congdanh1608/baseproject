package vn.danhtran.baseproject.utils;

import android.app.Activity;
import android.view.View;

import de.mateware.snacky.Snacky;
import vn.danhtran.baseproject.enums.SnackbarType;

/**
 * Created by SilverWolf on 04/08/2017.
 */

public class SnackBarUtils {
    //use view
    public static void showSnackBar(View view, String text, int textColor, boolean isCenterText, SnackbarType snackbarType) {
        Snacky.Builder builder = Snacky.builder();
        builder.setView(view)
                .setText(text)
                .setTextColor(textColor)
                .setDuration(Snacky.LENGTH_SHORT);
        if (isCenterText) builder.centerText();
        setTypeTemplate(builder, snackbarType);
        builder.build().show();
    }

    //use activity
    public static void showSnackBar(Activity activity, String text, int textColor, boolean isCenterText, SnackbarType snackbarType) {
        Snacky.Builder builder = Snacky.builder();
        builder.setActivty(activity)
                .setText(text)
                .setTextColor(textColor)
                .setDuration(Snacky.LENGTH_SHORT);
        if (isCenterText) builder.centerText();
        setTypeTemplate(builder, snackbarType);
        builder.build().show();
    }

    //add action text
    public static void showSnackBarWithAction(View view, String text, int textColor, String actionText, int actionColor,
                                              View.OnClickListener onClickListener, SnackbarType snackbarType) {
        Snacky.Builder builder = Snacky.builder();
        builder.setView(view)
                .setText(text)
                .setTextColor(textColor)
                .setActionText(actionText)
                .setActionTextColor(actionColor)
                .setActionClickListener(onClickListener)
                .setDuration(Snacky.LENGTH_SHORT);
        setTypeTemplate(builder, snackbarType);
        builder.build().show();
    }

    public static void showSnackBarWithAction(Activity activity, String text, int textColor, String actionText, int actionColor,
                                              View.OnClickListener onClickListener, SnackbarType snackbarType) {
        Snacky.Builder builder = Snacky.builder();
        builder.setActivty(activity)
                .setText(text)
                .setTextColor(textColor)
                .setActionText(actionText)
                .setActionTextColor(actionColor)
                .setActionClickListener(onClickListener)
                .setDuration(Snacky.LENGTH_SHORT);
        setTypeTemplate(builder, snackbarType);
        builder.build().show();
    }

    //set template for snackbar
    private static void setTypeTemplate(Snacky.Builder builder, SnackbarType snackbarType) {
        switch (snackbarType) {
            case ERROR:
                builder.error();
                break;
            case INFO:
                builder.info();
                break;
            case NORMAL:
                break;
            case SUCCESS:
                builder.success();
                break;
            case WARNING:
                builder.warning();
                break;
        }
    }
}
