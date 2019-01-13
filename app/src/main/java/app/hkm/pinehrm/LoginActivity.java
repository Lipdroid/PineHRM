package app.hkm.pinehrm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.hkm.pinehrm.apis.RequestAsyncTask;
import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.interfaces.AsyncCallback;
import app.hkm.pinehrm.utils.CorrectSizeUtil;
import app.hkm.pinehrm.utils.GlobalUtils;
import app.hkm.pinehrm.utils.SharedPreferencesUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private EditText et_mail = null;
    private EditText et_password = null;
    private Button btn_go = null;
    private TextView btn_forget_pass = null;
    private TextView btn_sign_up = null;
    private Context mContext = null;
    private String password = null;
    private String email = null;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        findViews();
        initListenersForViews();
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        afterClickBack();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_scale_to_center, R.anim.anim_slide_out_bottom);
    }

    private void initListenersForViews() {
        btn_go.setOnClickListener(this);
        btn_forget_pass.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

    }

    private void findViews() {
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_forget_pass = (TextView) findViewById(R.id.btn_forget_pass);
        btn_sign_up = (TextView) findViewById(R.id.btn_sign_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                afterClickSumbit();
                break;
            case R.id.btn_forget_pass:
                afterClickForgetPassword();
                break;
            case R.id.btn_sign_up:
                afterClickSignUp();
                break;
        }
    }

    private void afterClickSignUp() {
        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        finish();
    }

    private void afterClickForgetPassword() {
        startActivity(new Intent(mContext,MainActivity.class));
    }

    private void afterClickSumbit() {
        //goToMainPage();
        checkFieldValidation();
    }

    private void goToMainPage() {

        startActivity(new Intent(mContext, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }


    private void checkFieldValidation() {
        email = et_mail.getText().toString();
        password = et_password.getText().toString();

        if (email == null || email.equals("")) {
            Log.e(TAG, "password input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password input is empty", "OK", null);
            return;
        } else if (password == null || password.equals("")) {
            Log.e(TAG, "contact no input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "contact no input is empty", "OK", null);
            return;
        }

        requestToLogin();

    }

    private void requestToLogin() {

        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_EMAIL, email);
        params.put(Constants.PARAM_PASSWORD, password);

        goToMainPage();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_LOGIN, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                Log.e(TAG, result);
                GlobalUtils.dismissLoadingProgress();
                if (result != null) {

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

            }

            @Override
            public void onException(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

    }


}
