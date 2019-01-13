package app.hkm.pinehrm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.hkm.pinehrm.PineHRMApplication;
import app.hkm.pinehrm.R;
import app.hkm.pinehrm.customViews.CustomDialog;
import app.hkm.pinehrm.customViews.CustomProgressDialog;
import app.hkm.pinehrm.interfaces.DialogCallback;

/**
 * Created by mdmunirhossain on 5/4/18.
 */

public class GlobalUtils {
    public static Boolean addAditionalHeader = false;
    public static String additionalHeaderTag = null;
    public static String additionalHeaderValue = null;
    private static CustomProgressDialog sPdLoading = null;

    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) PineHRMApplication.PineHRMApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public static void showInfoDialog(Context context, String title, String body, String action, final DialogCallback dialogCallback) {
        final CustomDialog infoDialog = new CustomDialog(context, R.style.CustomDialogTheme);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.layout_show_info_dialog, null);

        new MultipleScreen(context);
        MultipleScreen.resizeAllView((ViewGroup) v);

        infoDialog.setContentView(v);

        Button btnOK = (Button) infoDialog.findViewById(R.id.dialog_btn_positive);
        TextView tvTitle = (TextView) infoDialog.findViewById(R.id.dialog_tv_title);
        TextView tvBody = (TextView) infoDialog.findViewById(R.id.dialog_tv_body);

        if (title == null) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (body == null) {
            tvBody.setVisibility(View.GONE);
        } else {
            tvBody.setText(body);
        }

        if (action != null) {
            btnOK.setText(action);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //your business logic
                if (dialogCallback != null) {
                    dialogCallback.onAction1();
                }
                infoDialog.dismiss();
            }
        });

        infoDialog.show();
    }

    public static void showLoadingProgress(Context context) {
        if (CustomProgressDialog.sPdCount <= 0) {
            CustomProgressDialog.sPdCount = 0;
            sPdLoading = null;
            sPdLoading = new CustomProgressDialog(context, R.style.CustomDialogTheme);
            sPdLoading.show();
            if (Build.VERSION.SDK_INT > 10) {
                LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View loadingV = inflator.inflate(R.layout.layout_pd_loading, null);
                new MultipleScreen(context);
                MultipleScreen.resizeAllView((ViewGroup) loadingV);
                sPdLoading.setContentView(loadingV);
            } else {
                String message = context.getResources().getString(R.string.common_loading);
            }
            CustomProgressDialog.sPdCount++;
        } else {
            CustomProgressDialog.sPdCount++;
        }
    }

    public static void dismissLoadingProgress() {
        if (CustomProgressDialog.sPdCount <= 1) {
            if (sPdLoading != null && sPdLoading.isShowing())
                sPdLoading.dismiss();
            CustomProgressDialog.sPdCount--;
        } else {
            CustomProgressDialog.sPdCount--;
        }
    }
}
