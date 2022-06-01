package com.eliteinfotech.vewash.ServiceProvider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eliteinfotech.vewash.R;

import java.util.ArrayList;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ExampleViewHolder> {


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

    public OrderSummaryAdapter(ArrayList<SummaryInfo> arrayList, Context context) {
        mCustomObjects = arrayList;
        mContext=context;
    }

    @Override
    public int getItemCount() {
        return mCustomObjects.size( );
    }

    @Override
    public OrderSummaryAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext( )).inflate(R.layout.ly_item_order_summary, parent, false);
        return new OrderSummaryAdapter.ExampleViewHolder(view2);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(OrderSummaryAdapter.ExampleViewHolder holder, int position) {
        SummaryInfo object = mCustomObjects.get(position);

        holder.mName.setText(object.getName());
        holder.mCount.setText(object.getCount());
//        holder.lyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, DailySummaryActivity.class).putExtra("monthid",object.getName()));
//            }
//        });


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}