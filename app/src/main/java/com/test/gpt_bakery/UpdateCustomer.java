package com.test.gpt_bakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class UpdateCustomer extends AppCompatActivity {
    private EditText namecustomerupdate,mobilecustomerupdate,statecustomerupdate,citycustomerupdate;
    private FirebaseUser user;
    private DatabaseReference userRef;
    private String userID;
    private Button update;
    private FirebaseAuth auth,firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        namecustomerupdate=findViewById(R.id.updatename);
        mobilecustomerupdate=findViewById(R.id.updatephone);
        statecustomerupdate=findViewById(R.id.updateState);
        citycustomerupdate=findViewById(R.id.updateCity);
        update=findViewById(R.id.updatebtn);
        user=FirebaseAuth.getInstance().getCurrentUser();
        userRef=FirebaseDatabase.getInstance().getReference("Customer");
        auth=FirebaseAuth.getInstance();
        userID=user.getUid();
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Customer customerProfile=snapshot.getValue(Customer.class);
                if (customerProfile != null)
                {
                    String city= customerProfile.City;
                    String name=customerProfile.Name;
                    String state=customerProfile.State;
                    String mobileno=customerProfile.MobileNo;
                    namecustomerupdate.setText(name);
                    statecustomerupdate.setText(state);
                    citycustomerupdate.setText(city);
                    mobilecustomerupdate.setText(mobileno);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namecustomerupdate.getText().toString().isEmpty() || statecustomerupdate.getText().toString().isEmpty() || citycustomerupdate.getText().toString().isEmpty() || mobilecustomerupdate.getText().toString().isEmpty() )
                {
                    Toast.makeText(UpdateCustomer.this,"Please do not leave it blank!!!",Toast.LENGTH_LONG).show();
                }
                else {
                    userRef.child(userID).child("Name").setValue(namecustomerupdate.getText().toString());
                    userRef.child(userID).child("State").setValue(statecustomerupdate.getText().toString());
                    userRef.child(userID).child("City").setValue(citycustomerupdate.getText().toString());
                    userRef.child(userID).child("MobileNo").setValue(mobilecustomerupdate.getText().toString());
                    Toast.makeText(UpdateCustomer.this, "Update successful", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
