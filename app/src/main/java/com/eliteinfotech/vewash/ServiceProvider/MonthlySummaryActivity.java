package com.eliteinfotech.vewash.ServiceProvider;

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
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonthlySummaryActivity extends AppCompatActivity {
    ArrayList<BookInfo> orderLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    OrderSummaryAdapter userOrderAdapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView mNoData;
    Bundle bb;
    String tdate = DateUtils.getTodayDate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_summary_activity);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Monthly Summary");
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

        loginPref = new SharedPrefHelper("login", this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_user_order");


        populateOrders();
    }

    ArrayList<SummaryInfo> summarize() {
        ArrayList<String> dlist = new ArrayList<>();
        dlist.clear();
        for (int i = 0; i < orderLst.size(); i++) {
            if (orderLst.get(i).getServiceProviderID().equals(loginPref.getString("userid")) && !dlist.contains(DateUtils.convertToString(orderLst.get(i).getCreatedDate(), "MMMM")))
                dlist.add(DateUtils.convertToString(orderLst.get(i).getCreatedDate(), "MMMM"));
        }

        ArrayList<SummaryInfo> slt = new ArrayList<>();
        slt.clear();
        for (int i = 0; i < dlist.size(); i++) {
            slt.add(new SummaryInfo(dlist.get(i), getCount(dlist.get(i))));
        }
        return slt;
    }

    String getCount(String date) {
        ArrayList<BookInfo> dl = new ArrayList<>();
        dl.clear();
        for (int i = 0; i < orderLst.size(); i++) {
            if (orderLst.get(i).getServiceProviderID().equals(loginPref.getString("userid")) && DateUtils.convertToString(orderLst.get(i).getCreatedDate(), "MMMM").equals(date))
                dl.add(orderLst.get(i));
        }
        return String.valueOf(dl.size());
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
                        orderLst.add(orderInfo);
                    }
                    if (orderLst.size() > 0) {
                        userOrderAdapter = new OrderSummaryAdapter(summarize(), MonthlySummaryActivity.this);
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
                    Toast.makeText(MonthlySummaryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    UtilityHelper.hideView(progressBar);
                    mNoData.setText(databaseError.getMessage());
                }
            });
        } catch (Exception ex) {
            Toast.makeText(MonthlySummaryActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            UtilityHelper.hideView(progressBar);
            mNoData.setText(ex.getMessage());
        }
    }

}
