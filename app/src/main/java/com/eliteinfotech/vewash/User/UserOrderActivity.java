package com.eliteinfotech.vewash.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eliteinfotech.vewash.DateUtils;
import com.eliteinfotech.vewash.Model.BookInfo;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UserOrderActivity extends AppCompatActivity {
    EditText mCustomerName, mServiceProvider, mDate, mTime, mContact;
    RadioButton rdBike, rdCar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPrefHelper loginPref;
    String serviceID = "", serviceName = "", serviceAmt = "", serviceProviderID = "", serviceProviderName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_order_activity);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("Book Service");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish( );
            }
        });

        mCustomerName = findViewById(R.id.et_fullname);
        mServiceProvider = findViewById(R.id.etServiceProvider);
        mDate = findViewById(R.id.etServiceDate);
        mTime = findViewById(R.id.etTime);
        mContact = findViewById(R.id.etContact);
        rdBike = findViewById(R.id.rdBike);
        rdCar = findViewById(R.id.rdCar);
        rdBike.setChecked(true);
        loginPref = new SharedPrefHelper("login", this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tbl_user_order");

        mCustomerName.setText(loginPref.getString("fullname"));

        mServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserOrderActivity.this, SearchProviderActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        findViewById(R.id.btnSumbit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomerName.getText().toString().isEmpty())
                {
                    Toast.makeText(UserOrderActivity.this, "Customer name is required.", Toast.LENGTH_SHORT).show();
                }
                else if(mServiceProvider.getText().toString().isEmpty()){
                    Toast.makeText(UserOrderActivity.this, "Customer name is required.", Toast.LENGTH_SHORT).show();
                } else if(mDate.getText().toString().isEmpty()){
                    Toast.makeText(UserOrderActivity.this, "Date is required.", Toast.LENGTH_SHORT).show();
                } else if(mTime.getText().toString().isEmpty()){
                    Toast.makeText(UserOrderActivity.this, "Time is required.", Toast.LENGTH_SHORT).show();
                } else if(mContact.getText().toString().isEmpty()){
                    Toast.makeText(UserOrderActivity.this, "Contact is required.", Toast.LENGTH_SHORT).show();
                }else {
                    insertOrder();
                }
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(UserOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String month = "";
                        String day = "";
                        if (selectedmonth < 10) {
                            month = "0" + String.valueOf(selectedmonth + 1);
                        } else {
                            month = String.valueOf(selectedmonth + 1);
                        }
                        if (selectedday < 10) {
                            day = "0" + String.valueOf(selectedday);
                        } else {
                            day = String.valueOf(selectedday);
                        }
                        String date = String.valueOf(selectedyear + "/" + month + "/" + day);
                        mDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                int AMPM = cldr.get(Calendar.AM_PM);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(UserOrderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }
                                mTime.setText(sHour + ":" + sMinute + " " + AM_PM);
                            }
                        }, hour, minutes, false);
                picker.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            serviceID = data.getStringExtra("ser_id");
            serviceName = data.getStringExtra("ser_name");
            serviceAmt = data.getStringExtra("ser_amt");
            serviceProviderID = data.getStringExtra("spid");
            serviceProviderName = data.getStringExtra("spname");
            mServiceProvider.setText(serviceName);

        }
    }

    void insertOrder() {
        String uniqueId = databaseReference.push().getKey();
        String vehicle = "2";
        if (rdBike.isChecked())
            vehicle = "1";
        BookInfo info = new BookInfo(uniqueId, loginPref.getString("userid"), loginPref.getString("fullname"), vehicle, serviceID, serviceName, serviceAmt, serviceProviderID, serviceProviderName, "0", mDate.getText().toString(), mTime.getText().toString(), "0", DateUtils.getTodayDate());
        databaseReference.child(uniqueId).setValue(info);
        Toast.makeText(UserOrderActivity.this, "User ordered successfully.", Toast.LENGTH_SHORT).show();
    }
}
