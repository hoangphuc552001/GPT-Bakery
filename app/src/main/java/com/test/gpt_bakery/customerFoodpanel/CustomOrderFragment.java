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
        codecheck.setText("M?? h??a ????n:");
        namecheck.setText("T??n ng?????i ?????t:");
        pricecheck.setText("T???ng ti???n:");
        phonecheck.setText("S??? ??i???n tho???i:");
        cakecheck.setText("Th???i gian ?????t:");
        statecheck.setText("Khu v???c:");
        citycheck.setText("Th??nh ph???:");
        timecheck.setText("Lo???i b??nh:");
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Order");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int i=0;
                        if (codeorder.getText().toString().isEmpty())
                        {
                            Toast.makeText(getActivity(),"Please input correct code order!!!",Toast.LENGTH_SHORT).show();
                            codecheck.setText("M?? h??a ????n:");
                            namecheck.setText("T??n ng?????i ?????t:");
                            pricecheck.setText("T???ng ti???n:");
                            phonecheck.setText("S??? ??i???n tho???i:");
                            cakecheck.setText("Th???i gian ?????t:");
                            statecheck.setText("Khu v???c:");
                            citycheck.setText("Th??nh ph???:");
                            timecheck.setText("Lo???i b??nh:");
                        }
                        else{
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


                                if (codes.equals(codeorder.getText().toString())) {
                                    codecheck.setText("M?? h??a ????n:" + codes);
                                    namecheck.setText("T??n ng?????i ?????t:" + name_);
                                    pricecheck.setText("T???ng ti???n:" + price_);
                                    phonecheck.setText("S??? ??i???n tho???i:" + phoness);
                                    cakecheck.setText("Th???i gian ?????t:" + cakes);
                                    statecheck.setText("Khu v???c:" + state_);
                                    citycheck.setText("Th??nh ph???:" + city);
                                    timecheck.setText("Lo???i b??nh:" + times);
                                    break;

                                }
                                i=i+1;
                            }
                            if (i== snapshot.getChildrenCount()) {
                                Toast.makeText(getActivity(), "Code order is not exist in database!!!", Toast.LENGTH_SHORT).show();
                                codecheck.setText("M?? h??a ????n:");
                                namecheck.setText("T??n ng?????i ?????t:");
                                pricecheck.setText("T???ng ti???n:");
                                phonecheck.setText("S??? ??i???n tho???i:");
                                cakecheck.setText("Th???i gian ?????t:");
                                statecheck.setText("Khu v???c:");
                                citycheck.setText("Th??nh ph???:");
                                timecheck.setText("Lo???i b??nh:");
                            }
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
