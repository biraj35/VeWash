package com.eliteinfotech.vewash.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.DateUtils;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.ServiceProvider.SummaryInfo;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentUsers extends Fragment {
    ArrayList<UserInfo> usrLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    UserSummaryAdapter userOrderAdapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView mNoData;
    Bundle bb;
    String tdate = DateUtils.getTodayDate();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_user_orders, container, false);
        progressBar = rootview.findViewById(R.id.progressbar);
        mRecyclerView = rootview.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoData = rootview.findViewById(R.id.tv_no_data);

        loginPref = new SharedPrefHelper("login", getActivity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");


        rootview.findViewById(R.id.fab).setVisibility(View.GONE);
        populateOrders();

        return rootview;
    }


    ArrayList<SummaryInfo> summarize() {
        ArrayList<String> dlist = new ArrayList<>();
        dlist.clear();
        for (int i = 0; i < usrLst.size(); i++) {
            if (!dlist.contains(usrLst.get(i).getUserType()))
                dlist.add(usrLst.get(i).getUserType());
        }

        ArrayList<SummaryInfo> slt = new ArrayList<>();
        slt.clear();
        for (int i = 0; i < dlist.size(); i++) {
            slt.add(new SummaryInfo(dlist.get(i), getCount(dlist.get(i))));
        }
        return slt;
    }

    String getCount(String userType) {
        ArrayList<UserInfo> dl = new ArrayList<>();
        dl.clear();
        if (usrLst.size()>0) {
            for (int i = 0; i < usrLst.size(); i++) {
                if (usrLst.get(i).getUserType().equals(userType))
                    dl.add(usrLst.get(i));
            }
        }
        return String.valueOf(dl.size());
    }



    void populateOrders() {
        try {
            UtilityHelper.showView(progressBar);
            usrLst.clear();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usrLst.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        UserInfo orderInfo = itemSnapshot.getValue(UserInfo.class);
                        usrLst.add(orderInfo);
                    }
                    if (usrLst.size() > 0) {
                        userOrderAdapter = new UserSummaryAdapter(summarize(), getActivity());
                        userOrderAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(userOrderAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                        mNoData.setText("No user found.");
                    }
                    UtilityHelper.hideView(progressBar);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    UtilityHelper.hideView(progressBar);
                    mNoData.setText(databaseError.getMessage());
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            UtilityHelper.hideView(progressBar);
            mNoData.setText(ex.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateOrders();
    }
}
