package com.eliteinfotech.vewash.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eliteinfotech.vewash.MapActivity;
import com.eliteinfotech.vewash.Model.ServiceProviderInfo;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;

import java.util.ArrayList;
import java.util.Locale;

public class ServiceProviderAdapter extends BaseAdapter {

    private ArrayList<UserInfo> appreciationlist;
    private Activity context;

    public ServiceProviderAdapter(ArrayList<UserInfo> list, Activity cont) {
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
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.ly_item_service_provider, null);

            holder = new ViewHolder();
            holder.spName = (TextView) convertView.findViewById(R.id.etName);
            holder.spAddress = (TextView) convertView.findViewById(R.id.etAddress);
            holder.spCall = (TextView) convertView.findViewById(R.id.etCall);
            holder.spMap = (TextView) convertView.findViewById(R.id.etMap);
            holder.lyView = convertView.findViewById(R.id.lyView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalHolder = holder;
        UserInfo stu = appreciationlist.get(position);
        holder.spName.setText(stu.getOrgName());
        holder.spAddress.setText(stu.getAddress());


        holder.spCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stu.getUserContact().isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + stu.getUserContact()));//change the number
                    context.startActivity(callIntent);
                } else {
                    Toast.makeText(context, "Number not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.spMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stu.getUserLat().isEmpty() && !stu.getUserLong().isEmpty()) {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("lat", stu.getUserLat());
                    intent.putExtra("long", stu.getUserLong());
                    intent.putExtra("address", stu.getAddress());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Map not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.lyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchServiceActivity.class);
                intent.putExtra("spID", stu.getUserID());
                context.startActivityForResult(intent, 1);
            }
        });


        return convertView;
    }

    public static class ViewHolder {
        public TextView spName, spAddress, spCall, spMap;
        LinearLayout lyView;
    }
}