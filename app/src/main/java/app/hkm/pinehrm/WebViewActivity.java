package app.hkm.pinehrm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.utils.CorrectSizeUtil;
import app.hkm.pinehrm.utils.SharedPreferencesUtils;

public class WebViewActivity extends Activity {
    private CorrectSizeUtil mCorrectSize = null;
    private Context mContext = null;
    private TextView header_title = null;
    private WebView mWv = null;
    private ProgressBar mPbLoading = null;
    private String mUrl = null;
    private String title = null;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mContext = this;
        header_title = (TextView) findViewById(R.id.header_title);
        mWv = (WebView) findViewById(R.id.webview_wv);
        mPbLoading = (ProgressBar) findViewById(R.id.webview_pb_loading);
        try {
            mUrl = getIntent().getStringExtra(Constants.TAG_URL);
            title = getIntent().getStringExtra(Constants.TAG_TITLE);
            header_title.setText(title);
            token = SharedPreferencesUtils.getString(mContext, Constants.PREF_TOKEN, null);
            if (token != null) {
                mUrl = mUrl + token;
                Log.e("loadUrl: ",mUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadUrl();

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void loadUrl() {
        if (mUrl == null) {
            return;
        }

        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.getSettings().setLoadWithOverviewMode(true);
        mWv.getSettings().setUseWideViewPort(true);
        mWv.clearCache(true);
        mWv.clearHistory();
        mWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        mWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100) {
                    mPbLoading.setProgress(newProgress);
                } else {
                    mPbLoading.setVisibility(View.GONE);
                }
            }
        });

        mWv.loadUrl(mUrl);

    }

    public void afterClickBack(View view) {
        finish();
        overridePendingTransition(R.anim.stand_by, R.anim.left_to_right);

    }
}
