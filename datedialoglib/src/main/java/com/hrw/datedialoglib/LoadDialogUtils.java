package com.hrw.datedialoglib;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.hrw.loainganimviewlibrary.ball.BallAnimView;


/**
 * @desc:
 * @author:Hrw
 * @date:2018/04/25 上午 11:40
 * @version:1.0.0
 */
public class LoadDialogUtils {
    private String TAG = "LoadDialogUtils";
    static LoadDialogUtils dialogUtils;
    private static CommonDialog loadDialog;
    private static BallAnimView animView;
    private static TextView tvLoadingLabel;

    private LoadDialogUtils() {
    }

    public static LoadDialogUtils instance() {
        if (dialogUtils == null)
            dialogUtils = new LoadDialogUtils();
        return dialogUtils;
    }

    public void startLoading(Context context) {
        this.startLoading(context, null);
    }

    public void startLoading(Context context, String msg) {
        if (loadDialog == null)
            loadDialog = new CommonDialog(context, DialogType.TOUCH_OUT_SIDE_NO_CANCELED, R.layout.dialog_loading_layout);
        if (!loadDialog.isShowing()) {
            animView = loadDialog.getView(R.id.bav_base_loading_anim);
            if (msg != null) {
                tvLoadingLabel = loadDialog.getView(R.id.tv_base_loading_label);
                tvLoadingLabel.setText(msg);
            }
            animView.startAnimator();
            loadDialog.show();
        } else {
            Log.i(TAG, "加载框已经弹出，无法继续进行弹出操作");
        }
    }

    public void stopLoading() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
            animView.stopAnimator();
        } else {
            Log.i(TAG, "加载框已经关闭，无法继续进行关闭操作");
        }
    }
}
