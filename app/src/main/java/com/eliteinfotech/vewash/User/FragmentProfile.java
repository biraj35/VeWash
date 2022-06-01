package com.eliteinfotech.vewash.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.Session.LoginActivity;
import com.eliteinfotech.vewash.Setting.ChangePasswordActivity;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.UtilityHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentProfile extends Fragment {
    EditText mName, mUsername, mContact, mAddress, mLat, mLong;
    SharedPrefHelper loginPref;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_frg_user_profile, container, false);
        mName = rootview.findViewById(R.id.et_fullname);
        mUsername = rootview.findViewById(R.id.et_username);
        mContact = rootview.findViewById(R.id.et_contact);
        mLat = rootview.findViewById(R.id.et_latitude);
        mAddress = rootview.findViewById(R.id.etAddress);
        mLong = rootview.findViewById(R.id.et_longitude);
        loginPref = new SharedPrefHelper("login", getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_users");

        if (!UtilityHelper.checkUserType(getActivity()).equals("serviceprovider"))
        {
            rootview.findViewById(R.id.lyMap).setVisibility(View.GONE);
        }

        popFromSession();

        rootview.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mName.getText().toString().isEmpty())
                    {
                        Toast.makeText(getActivity(), "name is required.", Toast.LENGTH_SHORT).show();
                    }else if (mContact.getText().toString().isEmpty())
                    {
                        Toast.makeText(getActivity(), "Contact is required.", Toast.LENGTH_SHORT).show();
                    }else {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                databaseReference.child(mUsername.getText().toString()).child("fullName").setValue(mName.getText().toString());
                                databaseReference.child(mUsername.getText().toString()).child("userLat").setValue(mLat.getText().toString());
                                databaseReference.child(mUsername.getText().toString()).child("userLong").setValue(mLong.getText().toString());
                                databaseReference.child(mUsername.getText().toString()).child("userContact").setValue(mContact.getText().toString());
                                databaseReference.child(mUsername.getText().toString()).child("address").setValue(mAddress.getText().toString());

                                Toast.makeText(getActivity(), "User updated successfully.", Toast.LENGTH_SHORT).show();
                                loginPref.setString("fullname", mName.getText().toString());
                                loginPref.setString("contact", mContact.getText().toString());
                                loginPref.setString("userLat", mLat.getText().toString());
                                loginPref.setString("userLong", mLong.getText().toString());
                                loginPref.setString("address", mAddress.getText().toString());
                                popFromSession();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // displaying a failure message on below line.
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //Toast.makeText(LoginActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        rootview.findViewById(R.id.btn_change_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        rootview.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefHelper.removeShared("login", getActivity());
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        return rootview;
    }

    void popFromSession() {
        mName.setText(loginPref.getString("fullname"));
        mUsername.setText(loginPref.getString("username"));
        mContact.setText(loginPref.getString("contact"));
        mLat.setText(loginPref.getString("userLat"));
        mLong.setText(loginPref.getString("userLong"));
        mAddress.setText(loginPref.getString("address"));
    }
}