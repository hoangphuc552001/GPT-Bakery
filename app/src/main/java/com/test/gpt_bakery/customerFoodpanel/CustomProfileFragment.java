package com.test.gpt_bakery.customerFoodpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.gpt_bakery.Customer;
import com.test.gpt_bakery.MainMenu;
import com.test.gpt_bakery.R;
import com.test.gpt_bakery.UpdateCustomer;

public class CustomProfileFragment extends Fragment{
    private TextView emailcustomer,namecustomer,mobilecustomer,statecustomer,citycustomer;
    private FirebaseUser user;
    private DatabaseReference userRef,databaseReference;
    private String userID;
    private Button logout,rspww,update;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerprofile, null);
        getActivity().setTitle("Profile");
        emailcustomer=v.findViewById(R.id.emailcustomer);
        namecustomer=v.findViewById(R.id.namecustomer);
        mobilecustomer=v.findViewById(R.id.mobilecustomer);
        statecustomer=v.findViewById(R.id.statecustomer);
        citycustomer=v.findViewById(R.id.citycustomer);
        rspww=v.findViewById(R.id.rspww);
        update=v.findViewById(R.id.updatedata);
        user=FirebaseAuth.getInstance().getCurrentUser();
        userRef=FirebaseDatabase.getInstance().getReference("Customer");
        logout=(Button)v.findViewById(R.id.signOut);
        auth=FirebaseAuth.getInstance();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Z = new Intent(getActivity(), UpdateCustomer.class);
                startActivity(Z);
            }
        });
        rspww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(FirebaseAuth.getInstance().getUid()+"/EmailId");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String e = snapshot.getValue(String.class);
                        auth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getActivity(),"Check your email to reset password!!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),MainMenu.class));
            }
        });
        userID=user.getUid();
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Customer customerProfile=snapshot.getValue(Customer.class);
                if (customerProfile != null)
                {
                    String city= customerProfile.City;
                    String emailid=customerProfile.EmailId;
                    String name=customerProfile.Name;
                    String state=customerProfile.State;
                    String mobileno=customerProfile.MobileNo;
                    emailcustomer.setText(emailid);
                    namecustomer.setText(name);
                    statecustomer.setText(state);
                    citycustomer.setText(city);
                    mobilecustomer.setText(mobileno);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return v;
    }
}
