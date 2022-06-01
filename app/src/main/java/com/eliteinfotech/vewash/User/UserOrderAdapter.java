package com.eliteinfotech.vewash.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ExampleViewHolder> {

    public class ExampleViewHolder extends RecyclerView.ViewHolder {



        TextView mOrderID, mService, mAmount, mVehicle, mServiceProvider, mDate, mStatus,mcancel,mapprove,mservStatus;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mOrderID = itemView.findViewById(R.id.etBookID);
            mService = itemView.findViewById(R.id.etService);
            mAmount = itemView.findViewById(R.id.etAmount);
            mVehicle = itemView.findViewById(R.id.etVehicle);
            mServiceProvider = itemView.findViewById(R.id.etServiceProvider);
            mDate = itemView.findViewById(R.id.etOrderDate);
            mStatus = itemView.findViewById(R.id.etBookStatus);
            mapprove = itemView.findViewById(R.id.mapprove);
            mcancel = itemView.findViewById(R.id.etExtend);
            mservStatus = itemView.findViewById(R.id.etservstatus);
        }
    }

    private ArrayList<BookInfo> mCustomObjects;
    SharedPrefHelper loginPref;
    FirebaseDatabase firebaseDatabase;
    Context context;
    DatabaseReference databaseReference;
    public UserOrderAdapter(ArrayList<BookInfo> arrayList, Context context) {
        mCustomObjects = arrayList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        this.context=context;
        databaseReference = firebaseDatabase.getReference("tbl_user_order");
        loginPref = new SharedPrefHelper("login", context);

    }

    @Override
    public int getItemCount() {
        return mCustomObjects.size();
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.ly_item_user_orders, parent, false);
        return new ExampleViewHolder(view2);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder,int position) {
        BookInfo object = mCustomObjects.get(position);

        holder.mOrderID.setText("#" + object.getBookID());
        holder.mService.setText("Service : " + object.getServiceName());
        holder.mAmount.setText("Amount : Rs " + object.getServiceAmt());

        if(object.getServiceStatus().equals("0"))
        {
            holder.mservStatus.setText("Service Status : Pending");
        }else  holder.mservStatus.setText("Service Status : Completed.");

        String v = "Car";
        if (object.getVehicle().equals("1"))
            v = "Bike";

        holder.mVehicle.setText("Vehicle : " + v);
        holder.mServiceProvider.setText("Service Provider : " + object.getServiceProviderName());
        holder.mDate.setText("Date : " + object.getServiceDate() + "," + object.getServiceTime());


        if (UtilityHelper.checkUserType(context).equals("serviceprovider")) {
            holder.mapprove.setText("Approve");
            holder.mapprove.setVisibility(View.VISIBLE);
            holder.mapprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSingleItem(object,position,"1");
                    databaseReference.child(object.getBookID()).child("bookStatus").setValue("1");
                }
            });
        }else if (UtilityHelper.checkUserType(context).equals("admin")) {
            holder.mapprove.setVisibility(View.GONE);
            holder.mcancel.setVisibility(View.GONE);
        }

        if (object.getBookStatus().equals("0")) {
            holder.mcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSingleItem(object,position,"-1");
                    databaseReference.child(object.getBookID()).child("bookStatus").setValue("-1");
                }
            });
            holder.mStatus.setText("Status : Pending");
        }

        if (object.getBookStatus().equals("1")) {
            holder.mcancel.setVisibility(View.GONE);
            holder.mStatus.setText("Status : Approved");
            holder.mapprove.setVisibility(View.GONE);
        }

        if (object.getBookStatus().equals("-1")) {
            holder.mcancel.setVisibility(View.GONE);
            holder.mapprove.setVisibility(View.GONE);
            holder.mStatus.setText("Status : Cancelled");
        }

    }
    private void updateSingleItem(BookInfo info,int pos,String status) {
        info.setBookStatus(status);
        mCustomObjects.set(pos, info);
        notifyDataSetChanged();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}