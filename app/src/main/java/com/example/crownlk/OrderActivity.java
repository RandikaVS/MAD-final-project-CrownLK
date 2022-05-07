package com.example.crownlk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.crownlk.databinding.ActivityOrderBinding;
import com.google.android.material.tabs.TabLayout;

public class OrderActivity extends DrawerBase {

    ActivityOrderBinding activityOrderBinding;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    OrdersViewpagerAdapter ordersViewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderBinding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(activityOrderBinding.getRoot());
        allocateActivityTitle("Orders");

        tabLayout = findViewById(R.id.orders_tab_layout);
        viewPager2 = findViewById(R.id.orders_viewpager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ordersViewpagerAdapter = new OrdersViewpagerAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(ordersViewpagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}