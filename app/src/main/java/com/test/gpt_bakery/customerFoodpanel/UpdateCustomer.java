package com.test.gpt_bakery.customerFoodpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.gpt_bakery.R;

public class UpdateCustomer extends AppCompatActivity {
    DatabaseReference reference;
    EditText name,phone,state,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        reference= FirebaseDatabase.getInstance().getReference("customers");
        name=findViewById(R.id.updatename);
        phone=findViewById(R.id.updatephone);
        state=findViewById(R.id.updateState);
        city=findViewById(R.id.updateCity);
    }
}