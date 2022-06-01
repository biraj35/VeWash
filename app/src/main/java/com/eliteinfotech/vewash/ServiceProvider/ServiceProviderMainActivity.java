package com.eliteinfotech.vewash.ServiceProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eliteinfotech.vewash.AboutUsActivity;
import com.eliteinfotech.vewash.Admin.AdminMainActivity;
import com.eliteinfotech.vewash.R;
import com.eliteinfotech.vewash.Session.LoginActivity;
import com.eliteinfotech.vewash.SharedPrefHelper;
import com.eliteinfotech.vewash.User.FragmentProfile;
import com.eliteinfotech.vewash.User.UserMainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_main_activity);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("VeWash");
        setSupportActionBar(mToolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.content_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(ServiceProviderMainActivity.this, AboutUsActivity.class));
                break;
            case R.id.menu_logout:
                SharedPrefHelper.removeShared("login", ServiceProviderMainActivity.this);
                Intent i = new Intent(ServiceProviderMainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.menu_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager( ));
        adapter.addFragment(new FragmentOrders( ), "Booking Request");
        adapter.addFragment(new FragmentService( ), "Services");
        adapter.addFragment(new FragmentSummary( ), "Summary");
        adapter.addFragment(new FragmentProfile( ), "Profile");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>( );
        private final List<String> mFragmentTitleList = new ArrayList<>( );

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size( );
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}