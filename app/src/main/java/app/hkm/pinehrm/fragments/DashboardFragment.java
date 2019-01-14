package app.hkm.pinehrm.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.hkm.pinehrm.R;
import app.hkm.pinehrm.adapters.ItemAdapter;
import app.hkm.pinehrm.models.ItemObject;
import app.hkm.pinehrm.utils.MultipleScreen;

public class DashboardFragment extends Fragment {
    private GridView gridview = null;
    private List<ItemObject> mListItem = null;
    private ItemAdapter adapter = null;
    private Context mContext = null;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dash, container, false);
        mContext = getActivity();
        gridview = (GridView) root.findViewById(R.id.gridview);
        mListItem = new ArrayList<>();
        mListItem.add(new ItemObject("Attendance","icon_attendence","http://www.google.com"));
        mListItem.add(new ItemObject("PaySlip","icon_payslip",""));
        mListItem.add(new ItemObject("Leave","icon_leave",""));
        mListItem.add(new ItemObject("Investment Scheme","icon_investment",""));
        mListItem.add(new ItemObject("Documents","icon_document",""));
        mListItem.add(new ItemObject("Outpatient","icon_outpatient",""));
        mListItem.add(new ItemObject("Salary Card","icon_card",""));
        mListItem.add(new ItemObject("Tax","icon_tax",""));

        populateList(mListItem);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void populateList(List<ItemObject> mListItem) {
        adapter = new ItemAdapter(mContext, mListItem, this);
        gridview.setAdapter(adapter);
    }
}
