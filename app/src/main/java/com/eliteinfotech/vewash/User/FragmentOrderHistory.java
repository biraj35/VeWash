package com.eliteinfotech.vewash.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentOrderHistory extends Fragment {
    ArrayList<BookInfo> orderLst = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    UserOrderAdapter userOrderAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_user_order_history, container, false);
        mRecyclerView = rootview.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rootview.findViewById(R.id.fab).setVisibility(View.GONE);
        loginPref = new SharedPrefHelper("login", getActivity());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_user_order");


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
                        BookInfo orderInfo = itemSnapshot.getValue(BookInfo.class);
                        if (!itemSnapshot.getValue(BookInfo.class).getBookStatus().equals("0")&& itemSnapshot.getValue(BookInfo.class).getUserID().equals(loginPref.getString("userid")))
                        orderLst.add(orderInfo);
                    }
                    if (orderLst.size() > 0) {
                        userOrderAdapter = new UserOrderAdapter(orderLst, getActivity());
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