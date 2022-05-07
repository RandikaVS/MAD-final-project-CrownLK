// Generated by view binder compiler. Do not edit!
package com.example.crownlk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.example.crownlk.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOrderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TabLayout ordersTabLayout;

  @NonNull
  public final ViewPager2 ordersViewpager;

  private ActivityOrderBinding(@NonNull LinearLayout rootView, @NonNull TabLayout ordersTabLayout,
      @NonNull ViewPager2 ordersViewpager) {
    this.rootView = rootView;
    this.ordersTabLayout = ordersTabLayout;
    this.ordersViewpager = ordersViewpager;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOrderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOrderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_order, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOrderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.orders_tab_layout;
      TabLayout ordersTabLayout = ViewBindings.findChildViewById(rootView, id);
      if (ordersTabLayout == null) {
        break missingId;
      }

      id = R.id.orders_viewpager;
      ViewPager2 ordersViewpager = ViewBindings.findChildViewById(rootView, id);
      if (ordersViewpager == null) {
        break missingId;
      }

      return new ActivityOrderBinding((LinearLayout) rootView, ordersTabLayout, ordersViewpager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}