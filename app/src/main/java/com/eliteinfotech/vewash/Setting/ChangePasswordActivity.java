package com.eliteinfotech.vewash.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText mOPassword, mNPassword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    ArrayList<UserInfo> usrLst = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_password);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mOPassword = findViewById(R.id.et_old_pwd);
        mNPassword = findViewById(R.id.et_new_pwd);
        loginPref = new SharedPrefHelper("login", this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");

        findViewById(R.id.btn_change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOPassword.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Old password is required.", Toast.LENGTH_SHORT).show();
                } else if (mNPassword.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "New password is required.", Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null) {
                                    usrLst.clear();
                                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                        UserInfo orderInfo = itemSnapshot.getValue(UserInfo.class);
                                        usrLst.add(orderInfo);
                                    }
                                    checkUser();

                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // displaying a failure message on below line.
                                Toast.makeText(ChangePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception ex) {
                        Toast.makeText(ChangePasswordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void checkUser() {
        boolean fal = false;
        for (int i = 0; i < usrLst.size(); i++) {

            if (usrLst.get(i).getUserName().equals(loginPref.getString("username")) && usrLst.get(i).getPassword().equals(mOPassword.getText().toString())) {
                fal = true;
                databaseReference.child(loginPref.getString("username")).child("password").setValue(mNPassword.getText().toString());
                Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if (fal == false) {
            Toast.makeText(this, "Old password does not match.", Toast.LENGTH_SHORT).show();
        }
    }
}
