package com.test.gpt_bakery.customerFoodpanel;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.test.gpt_bakery.CustomerFoodPanel_BottomNavigation;
import com.test.gpt_bakery.MainActivity;
import com.test.gpt_bakery.databasehelpers.ProductDatabaseHelper;
import com.test.gpt_bakery.MainMenu;
import com.test.gpt_bakery.R;
import com.test.gpt_bakery.adapters.CookieRecViewAdapter;
import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;

public class CustomHomeFragment extends Fragment implements CookieRecViewAdapter.cookieClickListener{
    private RecyclerView cookieRecView;
    private CookieRecViewAdapter adapter;
    private ArrayList<Cookie> cookies;
    private static ProductDatabaseHelper db;
    public static ArrayList<Cookie> chosenCookies = CustomerFoodPanel_BottomNavigation.cookieList;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new ProductDatabaseHelper(getContext());

        cookieRecView = view.findViewById(R.id.recView);
        adapter = new CookieRecViewAdapter(getContext(), this);

        cookies = db.getList();
        for (Cookie c:cookies
        ) {
            Log.d("AAA", c+"\n");
        }

        if(cookies.size() != 0) {
            adapter.setCookies(cookies);
        }

        cookieRecView.setAdapter(adapter);
        cookieRecView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.seach, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((CustomerFoodPanel_BottomNavigation) getActivity()).getSupportActionBar().getThemedContext());
        //SearchView searchView=(SearchView) searchItem.getActionView();
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        searchItem.setActionView(searchView);
        adapter=new CookieRecViewAdapter(getListCakes());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private ArrayList<Cookie> getListCakes() {
        ArrayList<Cookie> list = new ArrayList<>();
        list=db.getList();
        return list;
    }
    @Override
    public void onCookieClick(Cookie cookie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("Add to cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkIfCookieNotInCart(cookie))
                            chosenCookies.add(cookie);
                        else
                            increaseQuantity(cookie);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private boolean checkIfCookieNotInCart(Cookie cookie){
        boolean notInCart=true;
        for (int i = 0; i < chosenCookies.size(); i++)
            if (chosenCookies.get(i).getName().equals(cookie.getName())){
                notInCart=false;
                break;
            }
        return notInCart;
    }

    private void increaseQuantity(Cookie cookie){
        for (int i = 0; i < chosenCookies.size(); i++)
            if (chosenCookies.get(i).getName().equals(cookie.getName())){
                chosenCookies.get(i).increaseQuantity();
                break;
            }
    }

}
