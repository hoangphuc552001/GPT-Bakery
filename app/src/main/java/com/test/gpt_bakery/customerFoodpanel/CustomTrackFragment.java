package com.test.gpt_bakery.customerFoodpanel;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.gpt_bakery.Customer;
import com.test.gpt_bakery.Feeback;
import com.test.gpt_bakery.Payment;
import com.test.gpt_bakery.Price;
import com.test.gpt_bakery.R;
public class CustomTrackFragment extends Fragment{
    TextView titlerate,resultrate;
    Button btnfb;
    ImageView charplace;
    RatingBar ratestart;
    String answerValue;
    Animation charnim;
    EditText feedback;
    DatabaseReference fbref,useref;
    FirebaseUser user;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customertrack, null);
        getActivity().setTitle("Track Order");
        titlerate=v.findViewById(R.id.titlerate);
        resultrate=v.findViewById(R.id.resultrate);
        btnfb=v.findViewById(R.id.btnfb);
        charplace=v.findViewById(R.id.charPlace);
        ratestart=v.findViewById(R.id.rateStarts);
        charnim=AnimationUtils.loadAnimation(getActivity(),R.anim.charanim);
        feedback=v.findViewById(R.id.feedback);
        fbref = FirebaseDatabase.getInstance().getReference().child("Feedback");
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        ratestart.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                answerValue=String.valueOf((int)(ratestart.getRating()));
                if(answerValue.equals("1")){
                    charplace.setImageResource(R.drawable.bg2);
                    charplace.startAnimation(charnim);
                    resultrate.setText("Bad");
                }
                else if(answerValue.equals("2")){
                    charplace.setImageResource(R.drawable.img4);
                    charplace.startAnimation(charnim);
                    resultrate.setText("So so");
                }
                else if(answerValue.equals("3")){
                    charplace.setImageResource(R.drawable.img3);
                    charplace.startAnimation(charnim);
                    resultrate.setText("Good");
                }
                else if(answerValue.equals("4")){
                    charplace.setImageResource(R.drawable.img5);
                    charplace.startAnimation(charnim);
                    resultrate.setText("Great");
                }
                else if(answerValue.equals("5")){
                    charplace.setImageResource(R.drawable.img6);
                    charplace.startAnimation(charnim);
                    resultrate.setText("Awesome");
                }
                else{
                    Toast.makeText(getContext(),"No point",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void insert() {
        useref = FirebaseDatabase.getInstance().getReference("Customer").child(FirebaseAuth.getInstance().getUid()+"/EmailId");
        useref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String email = snapshot.getValue(String.class);
                String feedback_ = feedback.getText().toString();
                String start_ =String.valueOf((int)(ratestart.getRating()));
                Feeback f=new Feeback(start_,feedback_,email);
                fbref.push().setValue(f);
                Toast.makeText(getActivity(),"Received successful",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}
