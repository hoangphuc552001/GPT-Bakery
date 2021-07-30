package com.test.gpt_bakery;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.test.gpt_bakery.customerFoodpanel.CustomHomeFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomCartFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomProfileFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomOrderFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomTrackFragment;



public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food_panel_bottom_navigation);
        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.cust_Home:
                fragment=new CustomHomeFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cart:
                fragment=new CustomCartFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.cust_profile:
                fragment=new CustomProfileFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.Cust_order:
                fragment=new CustomOrderFragment();
                break;
        }
        switch (item.getItemId()){
            case R.id.track:
                fragment=new CustomTrackFragment();
                break;
        }
        return loadcheffragment(fragment);

    }

    private boolean loadcheffragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}