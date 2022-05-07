package com.example.crownlk;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrdersViewpagerAdapter extends FragmentStateAdapter {

    public OrdersViewpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){

            case 1:
                return new Completed_orders_fragment();
            default:
                return new Open_orders_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
