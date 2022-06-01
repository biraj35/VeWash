package com.eliteinfotech.vewash.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.eliteinfotech.vewash.R;


public class SummaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_admin_summary, container, false);

        rootview.findViewById(R.id.lyToday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TodaySummaryActivity.class));
            }
        });

        rootview.findViewById(R.id.lyMonthly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MonthlySummaryActivity.class));
            }
        });

        rootview.findViewById(R.id.lyYearly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), YearlySummaryActivity.class));
            }
        });
        return rootview;
    }
}