package com.eliteinfotech.vewash.Session;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eliteinfotech.vewash.Admin.AdminMainActivity;
import com.eliteinfotech.vewash.Model.UserInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.ServiceProvider.ServiceProviderMainActivity;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.User.UserMainActivity;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText mUsername, mPassword;
    SharedPrefHelper loginPref;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    ArrayList<UserInfo> usrLst = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        mUsername = findViewById(R.id.etUserName);
        mPassword = findViewById(R.id.etPassword);
        loginPref = new SharedPrefHelper("login", this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");
        progressDialog = UtilityHelper.loadingHelper(LoginActivity.this);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsername.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username is required.", Toast.LENGTH_SHORT).show();
                    mUsername.requestFocus();
                } else if (mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password is required.", Toast.LENGTH_SHORT).show();
                    mPassword.requestFocus();
                }  else {
                    try {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot!=null) {
                                    usrLst.clear();
                                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                        UserInfo orderInfo = itemSnapshot.getValue(UserInfo.class);
                                        usrLst.add(orderInfo);
                                    }
                                    checkUser(mUsername.getText().toString(),mPassword.getText().toString());
                                    progressDialog.dismiss();

                                }else {
                                    Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // displaying a failure message on below line.
                                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(LoginActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    void checkUser(String userName, String pwd) {
        boolean fal=false;
        for (int i = 0; i < usrLst.size(); i++) {

            if (usrLst.get(i).getUserName().equals(userName) && usrLst.get(i).getIsVerfied().equals("0")) {
                fal=true;
                Toast.makeText(this, "User not verified.", Toast.LENGTH_SHORT).show();
                break;
            }

            if (usrLst.get(i).getUserName().equals(userName) && usrLst.get(i).getPassword().equals(pwd)) {
                fal=true;
                loginPref.setString("userid", usrLst.get(i).getUserID());
                loginPref.setString("username", mUsername.getText().toString());
                loginPref.setString("fullname", usrLst.get(i).getFullName());
                loginPref.setString("contact", usrLst.get(i).getUserContact());
                loginPref.setString("nottoken", usrLst.get(i).getNotToken());
                loginPref.setString("isVerfied", usrLst.get(i).getIsVerfied());
                loginPref.setString("orgName", usrLst.get(i).getOrgName());
                loginPref.setString("userLat", usrLst.get(i).getUserLat());
                loginPref.setString("userLong", usrLst.get(i).getUserLong());
                loginPref.setString("address", usrLst.get(i).getAddress());
                loginPref.setBool("session", true);


                switch (usrLst.get(i).getUserType()) {
                    case "1":
                        loginPref.setString("usertype", "user");
                        startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                        finish();
                        break;
                    case "2":
                        loginPref.setString("usertype", "serviceprovider");
                        startActivity(new Intent(LoginActivity.this, ServiceProviderMainActivity.class));
                        finish();
                        break;
                    case "3":
                        loginPref.setString("usertype", "admin");
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        finish();
                        break;
                }
                break;
            }
        }

        if (fal==false)
        {
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
