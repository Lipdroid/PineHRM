package app.hkm.pinehrm.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.hkm.pinehrm.R;
import app.hkm.pinehrm.WebViewActivity;
import app.hkm.pinehrm.adapters.holders.ItemHolder;
import app.hkm.pinehrm.constants.Constants;
import app.hkm.pinehrm.fragments.DashboardFragment;
import app.hkm.pinehrm.models.ItemObject;
import app.hkm.pinehrm.utils.MultipleScreen;

/**
 * Created by mdmunirhossain on 5/9/18.
 */

public class ItemAdapter extends BaseAdapter {
    private Context mContext = null;
    private Activity mActivity = null;
    private List<ItemObject> mListData = null;
    private ItemHolder mHolder = null;
    private DashboardFragment fragment = null;

    @Override
    public int getCount() {
        return mListData.size();
    }

    public ItemAdapter(Context mContext, List<ItemObject> mListData, DashboardFragment fragment) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
        this.mListData = mListData;
        this.fragment = fragment;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.list_item, viewGroup, false);
            mHolder = new ItemHolder();
            mHolder.main_root = (RelativeLayout) convertView.findViewById(R.id.main_root);
            mHolder.image = (ImageView) convertView.findViewById(R.id.image);
            mHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            new MultipleScreen(mActivity);
            MultipleScreen.resizeAllView((ViewGroup) convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ItemHolder) convertView.getTag();
        }

        ItemObject itemObject = mListData.get(position);
        mHolder.title.setText(itemObject.getName());
        String icon_name = itemObject.getIcon();
        int id = mContext.getResources().getIdentifier(icon_name, "drawable", mContext.getPackageName());
        mHolder.image.setImageResource(id);
        setListenersForViews(position);
        return convertView;
    }

    private void setListenersForViews(final int position) {
        mHolder.main_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemObject itemObject = mListData.get(position);
                Intent browserIntent = new Intent(mContext, WebViewActivity.class);
                browserIntent.putExtra(Constants.TAG_URL, itemObject.getUrl());
                browserIntent.putExtra(Constants.TAG_TITLE, itemObject.getName());
                mContext.startActivity(browserIntent);
                mActivity.overridePendingTransition(R.anim.right_to_left,R.anim.stand_by);

            }
        });
    }


}
