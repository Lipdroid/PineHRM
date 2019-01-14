package app.hkm.pinehrm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.hkm.pinehrm.apis.RequestAsyncTask;
import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.interfaces.AsyncCallback;
import app.hkm.pinehrm.utils.CorrectSizeUtil;
import app.hkm.pinehrm.utils.GlobalUtils;
import app.hkm.pinehrm.utils.SharedPreferencesUtils;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private Handler handler;
    private Runnable runnable;
    private Context mContext;
    private static final String TAG = "SplashActivity";
    private CorrectSizeUtil mCorrectSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        String token = SharedPreferencesUtils.getString(this, Constants.PREF_TOKEN, null);
        if (token != null) {
            checkTokenValidity(token);
        } else {
            handler = new Handler(Looper.getMainLooper());
            runnable = new Runnable() {
                @Override
                public void run() {
                    // Do what ever you want
                    // This method will be executed once the timer is over
                    // Start your app activity
                    goToLoginPage();

                }
            };
            handler.postDelayed(runnable, SPLASH_TIME_OUT);
        }

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();

    }

    private void checkTokenValidity(String token) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_ACCESS_TOKEN, token);
        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_CHECK_TOKEN, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("result")) {
                            String success = jsonObject.getString("result");
                            if (success.equals("true")) {
                                goToMainPage();
                            } else {
                                goToLoginPage();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }
            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();
                goToLoginPage();

            }

            @Override
            public void onException(Exception e) {
                GlobalUtils.dismissLoadingProgress();
                goToLoginPage();

            }
        });

        mRequestAsync.execute();
    }

    private void goToMainPage() {
        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        // close this activity
        finish();
    }

    private void goToLoginPage() {
        startActivity(new Intent(mContext, LoginActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        // close this activity
        finish();
    }
}

