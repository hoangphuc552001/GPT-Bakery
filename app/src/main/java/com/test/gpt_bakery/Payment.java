package com.test.gpt_bakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Payment extends AppCompatActivity {
    private EditText namecustomerupdate, mobilecustomerupdate, statecustomerupdate, citycustomerupdate;
    private FirebaseUser user;
    private DatabaseReference userRef, cakeRef;
    private TextView prices, cakes, times,codes;
    private String userID;
    private Button checkout;
    private FirebaseAuth auth, firebaseAuth;
    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        prices = findViewById(R.id.Price);
        cakes = findViewById(R.id.Cake);
        times = findViewById(R.id.Time);
        codes=findViewById(R.id.code);
        checkout = findViewById(R.id.Checkout);
        namecustomerupdate = findViewById(R.id.updatename);
        mobilecustomerupdate = findViewById(R.id.updatephone);
        statecustomerupdate = findViewById(R.id.updateState);
        citycustomerupdate = findViewById(R.id.updateCity);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Customer");
        auth = FirebaseAuth.getInstance();
        userID = user.getUid();
        cakeRef = FirebaseDatabase.getInstance().getReference().child("Order");
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                Intent Z = new Intent(Payment.this,Place_Order.class);
                startActivity(Z);            }
        });
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Customer customerProfile = snapshot.getValue(Customer.class);
                if (customerProfile != null) {
                    String city = customerProfile.City;
                    String name = customerProfile.Name;
                    String state = customerProfile.State;
                    String mobileno = customerProfile.MobileNo;
                    namecustomerupdate.setText(name);
                    statecustomerupdate.setText(state);
                    citycustomerupdate.setText(city);
                    mobilecustomerupdate.setText(mobileno);
                    prices.setText("Price:" + Price.mPrice.toString().trim());
                    cakes.setText("Cake:" + Price.mCake.toString().trim());
                    int Min=100,Max=999;
                    Price.mCode=String.valueOf(Min+(int)(Math.random()*((Max-Min)+1)));
                    codes.setText("Mã hóa đơn:"+Price.mCode.toString().trim());
                    times.setText(currentDateTimeString.toString().trim());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void insert() {
        String name = namecustomerupdate.getText().toString();
        String city = citycustomerupdate.getText().toString();
        String state = statecustomerupdate.getText().toString();
        String mobileno = mobilecustomerupdate.getText().toString();
        String price= Price.mPrice.toString().trim();
        String time=Price.mCake.toString().trim();
        String cake=currentDateTimeString.toString().trim();
        String codes=Price.mCode.toString().trim();
        Customer cus=new Customer(city,mobileno,name,state,time,cake,price,codes);
        cakeRef.push().setValue(cus);
        Toast.makeText(Payment.this,"Inserted successfully",Toast.LENGTH_SHORT).show();
    }
}
