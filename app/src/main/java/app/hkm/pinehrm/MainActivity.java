package app.hkm.pinehrm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.fragments.DashboardFragment;
import app.hkm.pinehrm.utils.CorrectSizeUtil;
import app.hkm.pinehrm.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private LinearLayout btn_my_profile = null;
    private LinearLayout btn_logout = null;
    private Toolbar mToolBar = null;
    private DrawerLayout mDrawerLayout = null;
    private DrawerLayout.DrawerListener mDrawerListener = null;
    private Context mContext = null;
    private ImageView btn_left_drawer = null;
    private RelativeLayout drawer_left_layout = null;
    private RelativeLayout main_container = null;
    private TextView header_title;

    private FragmentTransaction fragTransaction = null;
    private FragmentManager mFragManager;
    private TextView name = null;
    private TextView email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findViews();
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        setListenersForViews();
        addFragment(Constants.FRAG_HOME, true);
        String name_str = SharedPreferencesUtils.getString(mContext, Constants.PREF_USER_NAME, null);
        String email_str = SharedPreferencesUtils.getString(mContext, Constants.PREF_USER_EMAIL, null);

        name.setText(name_str);
        email.setText(email_str);

        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void findViews() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //appbar items
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        drawer_left_layout = (RelativeLayout) findViewById(R.id.drawer_left);
        header_title = (TextView) findViewById(R.id.header_title);
        btn_left_drawer = (ImageView) findViewById(R.id.btn_left_drawer);
        //left drawer items
        btn_logout = (LinearLayout) findViewById(R.id.btn_logout);
        btn_my_profile = (LinearLayout) findViewById(R.id.btn_my_profile);
        name = (TextView) findViewById(R.id.tv_username);
        email = (TextView) findViewById(R.id.tv_email);


        //main layout
        main_container = (RelativeLayout) findViewById(R.id.main_container);
    }

    private void setListenersForViews() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        btn_left_drawer.setOnClickListener(this);
        //left drawer listener
        btn_my_profile.setOnClickListener(this);
        btn_logout.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left_drawer:
                afterClickLeftDrawerBtn();
                break;
            case R.id.btn_my_profile:
                closeLeftDrawer();
                afterClickMyhProfileBtn();
                break;
            case R.id.btn_logout:
                closeLeftDrawer();
                afterClickLogoutBtn();
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void afterClickLogoutBtn() {
        SharedPreferencesUtils.clearPreference(mContext);
        startActivity(new Intent(mContext, LoginActivity.class));
        finish();
    }

    private void afterClickMyhProfileBtn() {
        Intent browserIntent = new Intent(mContext, WebViewActivity.class);
        browserIntent.putExtra(Constants.TAG_URL, "https://www.pinehrm.com/cna/client/employee/profile/accesstoken/");
        browserIntent.putExtra(Constants.TAG_TITLE, "Profile");
        startActivity(browserIntent);
        overridePendingTransition(R.anim.right_to_left, R.anim.stand_by);
    }

    private void afterClickLeftDrawerBtn() {
        if (mDrawerLayout.isDrawerOpen(drawer_left_layout)) {
            closeLeftDrawer();
        } else if (!mDrawerLayout.isDrawerOpen(drawer_left_layout)) {
            openLeftDrawer();
        }
    }

    private void openLeftDrawer() {
        mDrawerLayout.openDrawer(drawer_left_layout);
        //focus on the left layout
        drawer_left_layout.bringToFront();
        drawer_left_layout.requestLayout();
    }

    private void closeLeftDrawer() {
        mDrawerLayout.closeDrawer(drawer_left_layout);
    }

    public void addFragment(int fragId, boolean isHasAnimation) {
        // init fragment manager
        mFragManager = getSupportFragmentManager();
        // create transaction
        fragTransaction = mFragManager.beginTransaction();

        // init argument
        Bundle args = new Bundle();

        //check if there is any backstack if yes then remove it
        int count = mFragManager.getBackStackEntryCount();
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            mFragManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        Fragment newFrag = null;
        // identify which fragment will be called
        switch (fragId) {
            case Constants.FRAG_HOME:
                newFrag = new DashboardFragment();
                break;
            default:
                break;
        }


        // set animation
        if (isHasAnimation) {
            fragTransaction.setCustomAnimations(
                    R.anim.view_transition_in_left,
                    R.anim.view_transition_out_right,
                    R.anim.view_transition_in_left,
                    R.anim.view_transition_out_right);
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id
        fragTransaction.replace(R.id.main_container, newFrag, String.valueOf(fragId));
        // prevent showed when user press back fabReview
        //fragTransaction.addToBackStack(String.valueOf(fragId));
        fragTransaction.commit();
    }
}
