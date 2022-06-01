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
import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.User.UserOrderAdapter;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodaySummaryActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ArrayList<BookInfo> orderLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    com.eliteinfotech.vewash.User.UserOrderAdapter userOrderAdapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView mNoData;
    Bundle bb;
    String tdate = DateUtils.getTodayDate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_today_summary);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Today Summary");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressBar = findViewById(R.id.progressbar);
        fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNoData = findViewById(R.id.tv_no_data);
        if (bb != null) {
            tdate = bb.getString("odate");
        }

        loginPref = new SharedPrefHelper("login", this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_user_order");


        populateOrders();
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
                        if (itemSnapshot.getValue(BookInfo.class).getCreatedDate().equals(tdate))
                            orderLst.add(orderInfo);
                    }
                    if (orderLst.size() > 0) {
                        userOrderAdapter = new UserOrderAdapter(orderLst,TodaySummaryActivity.this);
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
                    Toast.makeText(TodaySummaryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    UtilityHelper.hideView(progressBar);
                    mNoData.setText(databaseError.getMessage());
                }
            });
        } catch (Exception ex) {
            Toast.makeText(TodaySummaryActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            UtilityHelper.hideView(progressBar);
            mNoData.setText(ex.getMessage());
        }
    }

}
