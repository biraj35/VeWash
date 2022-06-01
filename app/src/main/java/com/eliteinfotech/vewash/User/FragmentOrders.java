package com.eliteinfotech.vewash.User;

import android.content.Intent;
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

import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentOrders extends Fragment {
    FloatingActionButton fab;
    ArrayList<BookInfo> orderLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    UserOrderAdapter userOrderAdapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView mNoData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_user_order, container, false);
        fab = rootview.findViewById(R.id.fab);
        mRecyclerView = rootview.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar=rootview.findViewById(R.id.progressbar);
        loginPref = new SharedPrefHelper("login", getActivity());
        mNoData=rootview.findViewById(R.id.tv_no_data);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_user_order");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserOrderActivity.class));
            }
        });

        populateOrders();
        return rootview;
    }

    void populateOrders() {
        try {
            UtilityHelper.showView(progressBar);
            orderLst.clear();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderLst.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        BookInfo orderInfo = itemSnapshot.getValue(BookInfo.class);
                        if (itemSnapshot.getValue(BookInfo.class).getBookStatus().equals("0") && itemSnapshot.getValue(BookInfo.class).getUserID().equals(loginPref.getString("userid")))
                            orderLst.add(orderInfo);
                    }

                    if (orderLst.size() > 0) {
                        userOrderAdapter = new UserOrderAdapter(orderLst,getActivity());
                        userOrderAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(userOrderAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mNoData.setVisibility(View.GONE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                        mNoData.setText("No order found.");
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
