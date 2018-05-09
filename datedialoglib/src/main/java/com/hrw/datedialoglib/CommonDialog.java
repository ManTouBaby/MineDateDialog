package com.hrw.datedialoglib;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * @author:ccf008
 * @date:2017/10/13 14:27
 * @desc:
 */

public class CommonDialog extends Dialog {

    public CommonDialog(@NonNull Context context, DialogType updateType, @LayoutRes int layoutRes) {
        this(context, updateType, layoutRes, R.style.Theme_AppCompat_DayNight_Dialog);
    }

    public CommonDialog(@NonNull Context context, DialogType updateType, @NonNull View view) {
        this(context, updateType, view, R.style.Theme_AppCompat_DayNight_Dialog);
    }

    public CommonDialog(@NonNull Context context, DialogType updateType, @NonNull View view, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(view);
        getCurrentFocus();
        initDialog(updateType);
    }

    public CommonDialog(@NonNull Context context, DialogType updateType, @LayoutRes int layoutRes, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(layoutRes);
        getCurrentFocus();
        initDialog(updateType);
    }

    private void initDialog(DialogType updateType) {
        if (updateType == DialogType.TOUCH_OUT_SIDE_CANCELED) {
            setCancelable(true);
            setCanceledOnTouchOutside(true);
        } else {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }

    public TextView text(@IdRes int resId, String label) {
        TextView textView = null;
        if (findViewById(resId) instanceof TextView) {
            textView = findViewById(resId);
            textView.setText(label);
        }
        return textView;
    }

    public TextView textColor(@IdRes int resId, @ColorInt int color) {
        TextView textView = null;
        if (findViewById(resId) instanceof TextView) {
            textView = findViewById(resId);
            textView.setTextColor(color);
        }
        return textView;
    }

    public TextView getText(@IdRes int resId) {
        TextView textView = null;
        if (findViewById(resId) instanceof TextView) {
            textView = findViewById(resId);
        }
        return textView;
    }

    public ProgressBar progressBar(@IdRes int resId, int progressBarSize) {
        ProgressBar progressBar = null;
        if (findViewById(resId) instanceof ProgressBar) {
            progressBar = findViewById(resId);
            progressBar.setProgress(progressBarSize);
        }
        return progressBar;
    }

    public ImageView getImage(@IdRes int resId) {
        ImageView imageView = null;
        if (findViewById(resId) instanceof ImageView) {
            imageView = findViewById(resId);
        }
        return imageView;
    }

    public ProgressBar getProgressBar(@IdRes int resId) {
        ProgressBar progressBar = null;
        if (findViewById(resId) instanceof ProgressBar) {
            progressBar = findViewById(resId);
        }
        return progressBar;
    }

    public Button getButton(@IdRes int resId) {
        Button button = null;
        if (findViewById(resId) instanceof Button) {
            button = findViewById(resId);
        }
        return button;
    }

    public LinearLayout getLinearLayout(@IdRes int resId) {
        LinearLayout linearLayout = null;
        if (findViewById(resId) instanceof LinearLayout) {
            linearLayout = findViewById(resId);
        }
        return linearLayout;
    }

    public RelativeLayout getRelativeLayout(@IdRes int resId) {
        RelativeLayout relativeLayout = null;
        if (findViewById(resId) instanceof RelativeLayout) {
            relativeLayout = findViewById(resId);
        }
        return relativeLayout;
    }


    Map<Integer, View> viewMap = new HashMap<>();//对所有已经查找过的View进行保存，减少每次查找的消耗

    public <T extends View> T getView(@IdRes int resId) {
        View view = viewMap.get(resId);
        if (view == null) {
            view = findViewById(resId);
            viewMap.put(resId, view);
        }
        return (T) view;

    }

    @Override
    public void show() {
        super.show();
    }

}
