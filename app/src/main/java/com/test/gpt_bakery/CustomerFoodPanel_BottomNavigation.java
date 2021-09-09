package com.test.gpt_bakery;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.gpt_bakery.customerFoodpanel.CustomHomeFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomCartFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomProfileFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomOrderFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomTrackFragment;
import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;


public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public static ArrayList<Cookie> cookieList = new ArrayList<>();
    Bundle cookieBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food_panel_bottom_navigation);
        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        Fragment fragment = new CustomHomeFragment();
        loadcheffragment(fragment);
        navigationView.setOnNavigationItemSelectedListener(this);

      /*  btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerFoodPanel_BottomNavigation.this, AdminActivity.class));
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.cust_Home:
               cookieBundle.putSerializable("cookie",cookieList);
                fragment=new CustomHomeFragment();
                fragment.setArguments(cookieBundle);
                break;
        }
        switch (item.getItemId()){
            case R.id.cart:
                cookieBundle.putSerializable("cookie",cookieList);
                fragment=new CustomCartFragment();
                fragment.setArguments(cookieBundle);
                break;
        }
        switch (item.getItemId()){
            case R.id.cust_profile:
                fragment=new CustomProfileFragment();
                fragment.setArguments(cookieBundle);
                break;
        }
        switch (item.getItemId()){
            case R.id.Cust_order:
                fragment=new CustomOrderFragment();
                fragment.setArguments(cookieBundle);
                break;
        }
        switch (item.getItemId()){
            case R.id.track:
                fragment=new CustomTrackFragment();
                fragment.setArguments(cookieBundle);
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