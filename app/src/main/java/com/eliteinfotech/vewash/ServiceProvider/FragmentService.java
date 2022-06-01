package com.eliteinfotech.vewash.ServiceProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.Model.ServiceInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.User.UserOrderActivity;
import com.eliteinfotech.vewash.User.UserOrderAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentService extends Fragment {
    FloatingActionButton fab;
    ArrayList<ServiceInfo> orderLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    AdapterSpService userOrderAdapter;
    ListView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_services, container, false);
        fab = rootview.findViewById(R.id.fab);
        mRecyclerView = rootview.findViewById(R.id.recycler_view);

        loginPref = new SharedPrefHelper("login", getActivity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_sp_services");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddServiceActivity.class));
            }
        });

        populateOrders();
        return rootview;
    }

    void populateOrders() {
        try {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderLst.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        ServiceInfo orderInfo = itemSnapshot.getValue(ServiceInfo.class);
                        if (itemSnapshot.getValue(ServiceInfo.class).getSpID().equals(loginPref.getString("userid")))
                            orderLst.add(orderInfo);
                    }

                    if (orderLst.size() > 0) {
                        userOrderAdapter = new AdapterSpService(orderLst,getActivity());
                        userOrderAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(userOrderAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateOrders();
    }
}