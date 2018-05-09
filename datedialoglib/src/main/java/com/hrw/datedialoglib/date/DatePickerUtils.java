package com.hrw.datedialoglib.date;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author:ccf008
 * @date:2017/11/15 14:45
 * @desc:
 */

public class DatePickerUtils {
    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    public static void resizePicker(FrameLayout tp) {
        float[] size = null;
        //npList size==3 代表 datepicker 年月日宽度对应为 0.25f 0.2f 0.2f
        //npList size==2 代表 timepicker 时分宽度对应为 0.175f 0.175f
        List<NumberPicker> npList = findNumberPicker(tp);
        if (npList.size() == 3) {
            size = new float[]{0.1f, 0.075f, 0.075f};
        } else if (npList.size() == 2) {
            size = new float[]{0.1f, 0.1f};
        }
        for (int i = 0; i < npList.size(); i++) {
            NumberPicker np = npList.get(i);
            resizeNumberPicker(np, size[i]);
        }
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    public static void resizeSinglePicker(FrameLayout tp) {
        float[] size = null;
        //npList size==3 代表 datepicker 年月日宽度对应为 0.25f 0.2f 0.2f
        //npList size==2 代表 timepicker 时分宽度对应为 0.175f 0.175f
        List<NumberPicker> npList = findNumberPicker(tp);
        if (npList.size() == 3) {
            size = new float[]{0.25f, 0.15f, 0.15f};
        } else if (npList.size() == 2) {
            size = new float[]{0.25f, 0.25f};
        }
        for (int i = 0; i < npList.size(); i++) {
            NumberPicker np = npList.get(i);
            resizeNumberPicker(np, size[i]);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {

        List<NumberPicker> npList = new ArrayList<>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /**
     * 调整numberpicker大小
     *
     * @param np
     * @param size 每个numberPicker对应分得屏幕宽度
     */
    private static void resizeNumberPicker(NumberPicker np, float size) {
        int dp5 = DensityUtils.dip2px(np.getContext(), 5);
        //timepicker 时 分 左右各自有8dp空白
        int dp32 = DensityUtils.dip2px(np.getContext(), 32);
        //屏幕宽度 - timepicker左右空白 -自设周边5dp空白
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (getScreenWidth(np.getContext()) *size), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dp5, 0, dp5, 0);
        np.setLayoutParams(params);
    }

    private static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static long getDateMili(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime().getTime();
    }

}
