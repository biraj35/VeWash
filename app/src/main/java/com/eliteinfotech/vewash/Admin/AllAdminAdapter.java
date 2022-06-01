package com.eliteinfotech.vewash.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AllAdminAdapter extends RecyclerView.Adapter<AllAdminAdapter.ExampleViewHolder> {


    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mUName,mStatus,mView,mVerify;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName);
            mUName = itemView.findViewById(R.id.tvUName);
            mStatus = itemView.findViewById(R.id.tvStatus);
            mVerify = itemView.findViewById(R.id.mver);
        }
    }

    private ArrayList<UserInfo> mCustomObjects;
    Context mContext;
    SharedPrefHelper loginPref;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public AllAdminAdapter(ArrayList<UserInfo> arrayList, Context context) {
        mCustomObjects = arrayList;
        mContext=context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");
        loginPref = new SharedPrefHelper("login", context);

    }

    @Override
    public int getItemCount() {
        return mCustomObjects.size( );
    }

    @Override
    public AllAdminAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext( )).inflate(R.layout.ly_item_all_users, parent, false);
        return new AllAdminAdapter.ExampleViewHolder(view2);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(AllAdminAdapter.ExampleViewHolder holder, int position) {
        UserInfo object = mCustomObjects.get(position);

        holder.mName.setText(object.getFullName());
        holder.mUName.setText(object.getUserName());

        if (object.getIsVerfied().equals("0")) {
            holder.mStatus.setText("Status : Inactive");
            holder.mVerify.setText("Activate");
        }
        else {
            holder.mStatus.setText("Status : Active");
            holder.mVerify.setText("Deactivate");
        }

        holder.mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object.getIsVerfied().equals("0")) {
                    databaseReference.child(object.getUserName()).child("isVerfied").setValue("1");
                    updateSingleItem(object,position,"1");
                    holder.mVerify.setText("Activate");
                }
                else {
                    databaseReference.child(object.getUserName()).child("isVerfied").setValue("0");
                    updateSingleItem(object,position,"0");
                    holder.mVerify.setText("Deactivate");
                }
            }
        });


    }

    private void updateSingleItem(UserInfo info,int pos,String statu) {
        info.setIsVerfied(statu);
        mCustomObjects.set(pos, info);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}