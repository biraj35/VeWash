package com.eliteinfotech.vewash.User;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eliteinfotech.vewash.DateUtils;
import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.Model.ServiceInfo;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchServiceActivity extends AppCompatActivity {
    Bundle bb;
    ListView mlist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    ArrayList<ServiceInfo> slst=new ArrayList<>();
    ServiceAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_provider);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Service");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish( );
            }
        });

        bb=getIntent().getExtras();
        mlist=findViewById(R.id.mlist);
        loginPref = new SharedPrefHelper("login", SearchServiceActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_sp_services");
        populateOrders();
    }


    void populateOrders() {
        try {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    slst.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        ServiceInfo orderInfo = itemSnapshot.getValue(ServiceInfo.class);
                        if (itemSnapshot.getValue(ServiceInfo.class).getSpID().equals(bb.getString("spID")))
                            slst.add(orderInfo);
                    }

                    if (slst.size() > 0) {
                        adapter = new ServiceAdapter(slst,SearchServiceActivity.this);
                        adapter.notifyDataSetChanged();
                        mlist.setAdapter(adapter);
                        mlist.setVisibility(View.VISIBLE);
                    } else {
                        mlist.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SearchServiceActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(SearchServiceActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

}