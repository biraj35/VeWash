package com.eliteinfotech.vewash.ServiceProvider;

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
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.User.ServiceAdapter;

import java.util.ArrayList;

public class AdapterSpService extends BaseAdapter {

    private ArrayList<ServiceInfo> appreciationlist;
    private Activity context;

    public AdapterSpService(ArrayList<ServiceInfo> list, Activity cont) {
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
        AdapterSpService.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.ly_item_service, null);

            holder = new AdapterSpService.ViewHolder();
            holder.sName = (TextView) convertView.findViewById(R.id.etName);
            holder.sAmt = (TextView) convertView.findViewById(R.id.etAddress);
            holder.lyView =  convertView.findViewById(R.id.lyView);


            convertView.setTag(holder);
        } else {
            holder = (AdapterSpService.ViewHolder) convertView.getTag();
        }
        final AdapterSpService.ViewHolder finalHolder = holder;
        ServiceInfo stu = appreciationlist.get(position);
        holder.sName.setText(stu.getServiceName());
        holder.sAmt.setText("Rs "+stu.getServiceAmt());


        return convertView;
    }

    public static class ViewHolder {
        public TextView sName,sAmt;
        LinearLayout lyView;
    }
}