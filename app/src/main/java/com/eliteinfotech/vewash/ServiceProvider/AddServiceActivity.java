package com.eliteinfotech.vewash.ServiceProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eliteinfotech.vewash.Model.ServiceInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.User.SearchServiceActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddServiceActivity extends AppCompatActivity {
    EditText mName, mAmt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_service);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Add Service");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mName = findViewById(R.id.et_fullname);
        mAmt = findViewById(R.id.etServiceProvider);
        loginPref = new SharedPrefHelper("login", this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_sp_services");
        findViewById(R.id.btnSumbit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().isEmpty())
                {
                    Toast.makeText(AddServiceActivity.this, "Service name is required.", Toast.LENGTH_SHORT).show();
                }else if (mAmt.getText().toString().isEmpty())
                {
                    Toast.makeText(AddServiceActivity.this, "Service amount is required.", Toast.LENGTH_SHORT).show();
                }else
                {
                    insertService();
                }
            }
        });
    }

    void insertService() {
        String uniqueId = databaseReference.push().getKey();
        ServiceInfo info = new ServiceInfo(uniqueId, mName.getText().toString(), mAmt.getText().toString(), loginPref.getString("userid"), loginPref.getString("fullname"), loginPref.getString("orgName"));
        databaseReference.child(uniqueId).setValue(info);
        finish();
    }

}
