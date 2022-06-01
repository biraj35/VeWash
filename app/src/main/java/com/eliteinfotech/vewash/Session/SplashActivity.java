package com.eliteinfotech.vewash.Session;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eliteinfotech.vewash.Admin.AdminMainActivity;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.ServiceProvider.ServiceProviderMainActivity;
import com.eliteinfotech.vewash.User.UserMainActivity;
import com.eliteinfotech.vewash.UtilityHelper;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UtilityHelper.checkLogin(SplashActivity.this) && UtilityHelper.checkUserType(SplashActivity.this).equals("admin")) {
                    startActivity(new Intent(SplashActivity.this, AdminMainActivity.class));
                    finish();
                    return;
                }
                if (UtilityHelper.checkLogin(SplashActivity.this) && UtilityHelper.checkUserType(SplashActivity.this).equals("user")) {
                    startActivity(new Intent(SplashActivity.this, UserMainActivity.class));
                    finish();
                    return;
                }
                if (UtilityHelper.checkLogin(SplashActivity.this) && UtilityHelper.checkUserType(SplashActivity.this).equals("serviceprovider")) {
                    startActivity(new Intent(SplashActivity.this, ServiceProviderMainActivity.class));
                    finish();
                    return;
                }
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1500);

    }
}

