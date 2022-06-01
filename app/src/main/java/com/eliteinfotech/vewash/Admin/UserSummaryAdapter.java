package com.eliteinfotech.vewash.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.ServiceProvider.SummaryInfo;

import java.util.ArrayList;

public class UserSummaryAdapter extends RecyclerView.Adapter<UserSummaryAdapter.ExampleViewHolder> {


    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mCount;
        LinearLayout lyView;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txtLabel);
            mCount = itemView.findViewById(R.id.txtCount);
            lyView = itemView.findViewById(R.id.lyView);
        }
    }

    private ArrayList<SummaryInfo> mCustomObjects;
    Context mContext;

    public UserSummaryAdapter(ArrayList<SummaryInfo> arrayList, Context context) {
        mCustomObjects = arrayList;
        mContext=context;
    }

    @Override
    public int getItemCount() {
        return mCustomObjects.size( );
    }

    @Override
    public UserSummaryAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext( )).inflate(R.layout.ly_item_order_summary, parent, false);
        return new UserSummaryAdapter.ExampleViewHolder(view2);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(UserSummaryAdapter.ExampleViewHolder holder, int position) {
        SummaryInfo object = mCustomObjects.get(position);

        holder.mName.setText(getName(object.getName()));
        holder.mCount.setText(object.getCount());
        holder.lyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, AllAdminActivity.class).putExtra("utype",object.getName()));
            }
        });
    }

    String getName(String type){
        switch (type)
        {
            case "1":
                return "User";
            case "2":
                return "Service Provider";
            case "3":
                return "Admin";
        }
        return "Error";
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}