package com.test.gpt_bakery.customerFoodpanel;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.gpt_bakery.Payment;
import com.test.gpt_bakery.Price;
import com.test.gpt_bakery.R;
import com.test.gpt_bakery.UpdateCustomer;
import com.test.gpt_bakery.adapters.CartRecViewAdapter;
import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;

public class CustomCartFragment extends Fragment implements CartRecViewAdapter.changeQuantityListener{
    static ArrayList <Cookie> chosenCookies = new ArrayList<>();
    private RecyclerView cartView;
    private CartRecViewAdapter adapter;
    ImageView pay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customercart, null);
        getActivity().setTitle("Cart");
        chosenCookies = (ArrayList<Cookie>)getArguments().getSerializable("cookie");
        System.out.println(chosenCookies);
        pay=v.findViewById(R.id.cart);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double s=0;
                String cake="";
                for (Cookie c:chosenCookies){
                    s=s+c.getPrice();
                    cake=cake+c.getName()+" ";
                }
                Price.mPrice=String.valueOf(s);
                Price.mCake=String.valueOf(cake);
                Intent Z = new Intent(getActivity(), Payment.class);
                startActivity(Z);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartView = view.findViewById(R.id.cartRecView);
		/// support passing data from homepage to Cart
        adapter = new CartRecViewAdapter(getContext(), this);

        for (Cookie c: chosenCookies
        ) {
            Log.d("AAA", c+"\n");
        }

        if(chosenCookies.size() != 0) {
            adapter.setCart(chosenCookies);
        }

        cartView.setAdapter(adapter);
        cartView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
	/// chang quantity of cookies
    public void onChangeQuantityClick(Cookie cookie, String action, int position) {
        if (action.equals("+")){
            cookie.increaseQuantity();
            adapter.notifyDataSetChanged();
        }else{
            if (cookie.getQuantity() > 0) {
                cookie.decreaseQuantity();
                adapter.notifyDataSetChanged();
            }
            if (cookie.getQuantity() == 0) {
                chosenCookies.remove(cookie);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
