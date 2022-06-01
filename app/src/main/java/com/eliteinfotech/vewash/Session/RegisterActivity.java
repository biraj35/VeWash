package com.eliteinfotech.vewash.Session;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText mUsername, mFullName, mPassword, mOrgName;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CheckBox ckDel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        mUsername = findViewById(R.id.etUserName);
        mFullName = findViewById(R.id.etFullName);
        mPassword = findViewById(R.id.etPassword);
        mOrgName = findViewById(R.id.etOrgName);
        ckDel = findViewById(R.id.ck_del);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");

        ckDel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if (isChecked) {
                                                     findViewById(R.id.lyOrg).setVisibility(View.VISIBLE);
                                                 } else {
                                                     findViewById(R.id.lyOrg).setVisibility(View.GONE);
                                                 }
                                             }
                                         }
        );

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFullName.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fullname is required.", Toast.LENGTH_SHORT).show();
                    mFullName.requestFocus();
                } else if (mUsername.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Username is required.", Toast.LENGTH_SHORT).show();
                    mUsername.requestFocus();
                } else if (mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password is required.", Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                }else if (!UtilityHelper.isValid(mPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password must contain at least one digit [0-9].\n" +
                            "Password must contain at least one lowercase Latin character [a-z].\n" +
                            "Password must contain at least one uppercase Latin character [A-Z].\n" +
                            "Password must contain at least one special character like ! @ # & ( ).\n" +
                            "Password must contain a length of at least 8 characters and a maximum of 20 characters.", Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                } else if (ckDel.isChecked() && mOrgName.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Organization name is required.", Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                } else {
                    try {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child(mUsername.getText().toString()).exists()) {
                                    Toast.makeText(RegisterActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                String userType = "1";
                                String isVerified = "1";
                                String orgName = "";
                                if (ckDel.isChecked()) {
                                    userType = "2";
                                    isVerified = "0";
                                    orgName = mOrgName.getText().toString();
                                }

                                String uniqueId = databaseReference.push().getKey().toString();
                                UserInfo info = new UserInfo(uniqueId, mUsername.getText().toString(), mPassword.getText().toString(),mFullName.getText().toString(),"", userType, uniqueId, isVerified, orgName,"", "", "");
                                // on below line we are setting data in our firebase database.
                                databaseReference.child(mUsername.getText().toString()).setValue(info);
                                // displaying a toast message.
                                Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                                mFullName.setText("");
                                mUsername.setText("");
                                mPassword.setText("");
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // displaying a failure message on below line.
                                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(LoginActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(RegisterActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
