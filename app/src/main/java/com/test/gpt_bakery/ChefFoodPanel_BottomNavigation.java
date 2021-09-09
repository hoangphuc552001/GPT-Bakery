package com.test.gpt_bakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.gpt_bakery.chefFoodpanel.ChefHomeFragment;
import com.test.gpt_bakery.chefFoodpanel.ChefOrderFragment;
import com.test.gpt_bakery.chefFoodpanel.ChefPendingOrderFragment;
import com.test.gpt_bakery.chefFoodpanel.ChefProfileFragment;
import com.test.gpt_bakery.customerFoodpanel.CustomHomeFragment;

public class ChefFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Bundle cookieBundle = new Bundle();
    private FloatingActionButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_food_panel_bottom_navigation);
        BottomNavigationView navigationView =(BottomNavigationView)findViewById(R.id.chef_bottom_navigation);
        Fragment fragment = new ChefProfileFragment();
        loadcheffragment(fragment);
        navigationView.setOnNavigationItemSelectedListener(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefFoodPanel_BottomNavigation.this, AdminActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
           /* case R.id.chefHome:
                fragment=new ChefHomeFragment();
                break;
            case R.id.PendingOrders:
                fragment=new ChefPendingOrderFragment();
                break;
            case R.id.Orders:
                fragment=new ChefOrderFragment();
                break;*/
            case R.id.chefProfile:
                fragment=new ChefProfileFragment();
                fragment.setArguments(cookieBundle);
                break;
        }
        return loadcheffragment(fragment);
    }

    private boolean loadcheffragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}