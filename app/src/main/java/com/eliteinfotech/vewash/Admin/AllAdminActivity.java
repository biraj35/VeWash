package com.eliteinfotech.vewash.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.DateUtils;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllAdminActivity extends AppCompatActivity {
    ArrayList<UserInfo> usrLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    AllAdminAdapter userOrderAdapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView mNoData;
    Bundle bb;
    String tdate = DateUtils.getTodayDate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_all_admin);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("User Summary");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressBar = findViewById(R.id.progressbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNoData = findViewById(R.id.tv_no_data);
        bb = getIntent().getExtras();
        loginPref = new SharedPrefHelper("login", this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");


        populateOrders();

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
                        if (orderInfo.getUserType().equals(bb.getString("utype")))
                            usrLst.add(orderInfo);
                    }
                    if (usrLst.size() > 0) {
                        userOrderAdapter = new AllAdminAdapter(usrLst, AllAdminActivity.this);
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
                    Toast.makeText(AllAdminActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    UtilityHelper.hideView(progressBar);
                    mNoData.setText(databaseError.getMessage());
                }
            });
        } catch (Exception ex) {
            Toast.makeText(AllAdminActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            UtilityHelper.hideView(progressBar);
            mNoData.setText(ex.getMessage());
        }
    }

}
