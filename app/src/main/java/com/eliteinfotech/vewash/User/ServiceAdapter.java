package com.eliteinfotech.vewash.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eliteinfotech.vewash.Model.ServiceInfo;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;

import java.util.ArrayList;

public class ServiceAdapter extends BaseAdapter {

    private ArrayList<ServiceInfo> appreciationlist;
    private Activity context;

    public ServiceAdapter(ArrayList<ServiceInfo> list, Activity cont) {
        this.appreciationlist = list;
        this.context = cont;
    }


    @Override
    public int getCount() {
        return this.appreciationlist.size();
    }

    @Override
    public Object getItem(int position) {
        return this.appreciationlist.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceAdapter.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.ly_item_service, null);

            holder = new ServiceAdapter.ViewHolder();
            holder.sName = (TextView) convertView.findViewById(R.id.etName);
            holder.sAmt = (TextView) convertView.findViewById(R.id.etAddress);
            holder.lyView =  convertView.findViewById(R.id.lyView);


            convertView.setTag(holder);
        } else {
            holder = (ServiceAdapter.ViewHolder) convertView.getTag();
        }
        final ServiceAdapter.ViewHolder finalHolder = holder;
        ServiceInfo stu = appreciationlist.get(position);
        holder.sName.setText(stu.getServiceName());
        holder.sAmt.setText("Rs "+stu.getServiceAmt());

        holder.lyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = context.getIntent();
                intent.putExtra("ser_id", stu.getServiceID());
                intent.putExtra("ser_name", stu.getServiceName());
                intent.putExtra("ser_amt", stu.getServiceAmt());
                intent.putExtra("spid", stu.getSpID());
                intent.putExtra("spname", stu.getSpName());
                context.setResult(1, intent);
                context.finish();
            }
        });


        return convertView;
    }

    public static class ViewHolder {
        public TextView sName,sAmt;
        LinearLayout lyView;
    }
}