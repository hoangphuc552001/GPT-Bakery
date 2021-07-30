package com.test.gpt_bakery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;


public class Login extends AppCompatActivity {

    TextInputLayout emailcustomer,passcustomer;
    Button Signincustomer,Signinphonecustomer;
    TextView signupcustomer;
    FirebaseAuth Fauthcustomer;
    String emailidcustomer,pwdcustomer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{

            emailcustomer = (TextInputLayout)findViewById(R.id.Lemailcustomer);
            passcustomer = (TextInputLayout)findViewById(R.id.Lpasswordcustomer);
            Signincustomer = (Button)findViewById(R.id.button4customer);
            signupcustomer = (TextView) findViewById(R.id.textView3customer);
            Signinphonecustomer = (Button)findViewById(R.id.btnphonecustomer);

            Fauthcustomer = FirebaseAuth.getInstance();

            Signincustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailidcustomer = emailcustomer.getEditText().getText().toString().trim();
                    pwdcustomer = passcustomer.getEditText().getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog mDialog = new ProgressDialog(Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();

                        Fauthcustomer.signInWithEmailAndPassword(emailidcustomer,pwdcustomer).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(Fauthcustomer.getCurrentUser().isEmailVerified()){
                                        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()+"/Role");
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String role = snapshot.getValue(String.class);
                                                if(role.equals("Customer")){
                                                    mDialog.dismiss();
                                                    Toast.makeText(Login.this, "Congratulation! You Have Successfully Login", Toast.LENGTH_SHORT).show();
                                                    Intent Z = new Intent(Login.this,ChefFoodPanel_BottomNavigation.class);
                                                    startActivity(Z);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(Login.this, "Wrong role account", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(Login.this,error.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }else{
                                        ReusableCodeForAll.ShowAlert(Login.this,"Verification Failed","You Have Not Verified Your Email");

                                    }
                                }else{
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Login.this,"Error",task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
            signupcustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,Registration.class));
                    finish();
                }
            });
            Signinphonecustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,Loginphone.class));
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    String emailpattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){

        emailcustomer.setErrorEnabled(false);
        emailcustomer.setError("");
        passcustomer.setErrorEnabled(false);
        passcustomer.setError("");

        boolean isvalid=false,isvalidemail=false,isvalidpassword=false;
        if(TextUtils.isEmpty(emailidcustomer)){
            emailcustomer.setErrorEnabled(true);
            emailcustomer.setError("Email is required");
        }else{
            if(emailidcustomer.matches(emailpattern)){
                isvalidemail=true;
            }else{
                emailcustomer.setErrorEnabled(true);
                emailcustomer.setError("Invalid Email Address");
            }
        }
        if(TextUtils.isEmpty(pwdcustomer)){

            passcustomer.setErrorEnabled(true);
            passcustomer.setError("Password is Required");
        }else{
            isvalidpassword=true;
        }
        isvalid=(isvalidemail && isvalidpassword)?true:false;
        return isvalid;
    }
}