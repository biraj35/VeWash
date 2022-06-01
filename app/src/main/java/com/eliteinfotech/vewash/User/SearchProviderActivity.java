package com.eliteinfotech.vewash.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.Model.ServiceProviderInfo;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchProviderActivity extends AppCompatActivity {
    ListView mlist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    ArrayList<UserInfo> slst = new ArrayList<>();
    ServiceProviderAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_provider);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Service Provider");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish( );
            }
        });

        mlist = findViewById(R.id.mlist);
        loginPref = new SharedPrefHelper("login", SearchProviderActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");

        populateOrders();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String serviceID = data.getStringExtra("ser_id");
            String serviceName = data.getStringExtra("ser_name");
            String serviceAmt = data.getStringExtra("ser_amt");
            String spID = data.getStringExtra("spid");
            String spName = data.getStringExtra("spname");
            Intent intent = getIntent();
            intent.putExtra("ser_id", serviceID);
            intent.putExtra("ser_name", serviceName);
            intent.putExtra("ser_amt", serviceAmt);
            intent.putExtra("spid", spID);
            intent.putExtra("spname", spName);
            setResult(1, intent);
            finish();
        }
    }

    void populateOrders() {
        try {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    slst.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        UserInfo orderInfo = itemSnapshot.getValue(UserInfo.class);
                        if (itemSnapshot.getValue(UserInfo.class).getUserType().equals("2") && itemSnapshot.getValue(UserInfo.class).getIsVerfied().equals("1"))
                            slst.add(orderInfo);
                    }

                    if (slst.size() > 0) {
                        adapter = new ServiceProviderAdapter(slst, SearchProviderActivity.this);
                        adapter.notifyDataSetChanged();
                        mlist.setAdapter(adapter);
                        mlist.setVisibility(View.VISIBLE);
                    } else {
                        mlist.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchProviderActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(SearchProviderActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
