package com.test.gpt_bakery.customerFoodpanel;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.gpt_bakery.Customer;
import com.test.gpt_bakery.R;

import org.w3c.dom.Text;

public class CustomOrderFragment extends Fragment {
    EditText codeorder;
    TextView codecheck,namecheck,pricecheck,cakecheck,phonecheck,statecheck,citycheck,timecheck;
    Button check;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerorder,null);
        getActivity().setTitle("Orders");
        codeorder=v.findViewById(R.id.codeOrder);
        codecheck=v.findViewById(R.id.codeCheck);
        namecheck=v.findViewById(R.id.nameCheck);
        pricecheck=v.findViewById(R.id.PriceCheck);
        cakecheck=v.findViewById(R.id.CakeCheck);
        phonecheck=v.findViewById(R.id.phoneCheck);
        statecheck=v.findViewById(R.id.Statecheck);
        citycheck=v.findViewById(R.id.cityCheck);
        timecheck=v.findViewById(R.id.TimeCheck);
        check=v.findViewById(R.id.Check);
        user=FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        codecheck.setText("Mã hóa đơn:");
        namecheck.setText("Tên người đặt:");
        pricecheck.setText("Tổng tiền:");
        phonecheck.setText("Số điện thoại:");
        cakecheck.setText("Thời gian đặt:");
        statecheck.setText("Khu vực:");
        citycheck.setText("Thành phố:");
        timecheck.setText("Loại bánh:");
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Order");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int i=0;
                      for (DataSnapshot snapshot1:snapshot.getChildren())
                      {
                          Customer c=snapshot1.getValue(Customer.class);
                          String codes=c.codes;
                          String name_=c.Name;
                          String price_=c.prices;
                          String phoness= c.MobileNo;
                          String cakes=c.cakes;
                          String state_=c.State;
                          String city=c.City;
                          String times=c.times;
                          if (codeorder.getText().toString().isEmpty())
                          {
                              Toast.makeText(getActivity(),"Please input correct code order!!!",Toast.LENGTH_LONG).show();
                              codecheck.setText("Mã hóa đơn:");
                              namecheck.setText("Tên người đặt:");
                              pricecheck.setText("Tổng tiền:");
                              phonecheck.setText("Số điện thoại:");
                              cakecheck.setText("Thời gian đặt:");
                              statecheck.setText("Khu vực:");
                              citycheck.setText("Thành phố:");
                              timecheck.setText("Loại bánh:");
                          }
                          else {
                              if (codes.equals(codeorder.getText().toString())) {
                                  codecheck.setText("Mã hóa đơn:" + codes);
                                  namecheck.setText("Tên người đặt:" + name_);
                                  pricecheck.setText("Tổng tiền:" + price_);
                                  phonecheck.setText("Số điện thoại:" + phoness);
                                  cakecheck.setText("Thời gian đặt:" + cakes);
                                  statecheck.setText("Khu vực:" + state_);
                                  citycheck.setText("Thành phố:" + city);
                                  timecheck.setText("Loại bánh:" + times);
                                  break;
                              }
                          }
                          i=i+1;
                      }
                      if (i== snapshot.getChildrenCount()) {
                          Toast.makeText(getActivity(), "Code order is not exist in database!!!", Toast.LENGTH_LONG).show();
                          codecheck.setText("Mã hóa đơn:");
                          namecheck.setText("Tên người đặt:");
                          pricecheck.setText("Tổng tiền:");
                          phonecheck.setText("Số điện thoại:");
                          cakecheck.setText("Thời gian đặt:");
                          statecheck.setText("Khu vực:");
                          citycheck.setText("Thành phố:");
                          timecheck.setText("Loại bánh:");
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        return v;
    }
}
