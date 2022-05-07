package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){

            case R.id.nav_menu_item_dashboard:
                startActivity(new Intent(this,DashBoardActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_admin:
                startActivity(new Intent(this,AdminActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_shop:
                startActivity(new Intent(this,ShopActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_orders:
                startActivity(new Intent(this,OrderActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_messages:
                startActivity(new Intent(this,MessagesActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_listings:
                startActivity(new Intent(this,ListingsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_notification:
                startActivity(new Intent(this,NotificationActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_menu_item_disputes:
                startActivity(new Intent(this,DisputeActivity.class));
                overridePendingTransition(0,0);
                break;

        }
        return false;
    }

    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar() !=null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}