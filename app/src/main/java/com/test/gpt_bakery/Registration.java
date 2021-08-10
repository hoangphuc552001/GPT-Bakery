package com.test.gpt_bakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.datatransport.runtime.dagger.Reusable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class Registration extends AppCompatActivity {
    String[] HaNoi = {"Hoàn Kiếm","Đống Đa","Ba Đình","Hai Bà Trưng",
            "Hoàng Mai","Thanh Xuân","Long Biên","Nam Từ Liêm","Bắc Từ Liêm","Tây Hồ","Cầu Giấy","Hà Đông"};
    String[] HoChiMinh={"Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6","Quận 7","Quận 8","Quận 9","Quận 10","Quận 11","Quận 12",
            "Quận Thủ Đức","Quận Gò Vấp", "Quận Bình Thạnh", "Quận Tân Bình", "Quận Tân Bình", "Quận Tân Phú",
            "Quận Phú Nhuận","Quận Bình Tân","Huyện Củ Chi","Huyện Hóc Môn","Huyện Bình Chánh","Huyện Nhà Bè","Huyện Cần Giờ"};
    TextInputLayout Namecustomer,Emailcustomer,Passcustomer,cpasscustomer,mobilenocustomer;
    Spinner Statespincustomer,Cityspincustomer;
    Button signupcustomer, Emaillcustomer, Phonecustomer;
    CountryCodePicker Cppcustomer;
    FirebaseAuth FAuthcustomer;
    DatabaseReference databaseReferencecustomer;
    FirebaseDatabase firebaseDatabasecustomer;
    String namecustomer,emailidcustomer,passwordcustomer,confpasswordcustomer,mobilecustomer,stateecustomer,cityycustomer;
    String role="Customer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        Namecustomer = (TextInputLayout)findViewById(R.id.Namecustomer);
        Emailcustomer = (TextInputLayout)findViewById(R.id.Emailcustomer);
        Passcustomer = (TextInputLayout)findViewById(R.id.Pwdcustomer);
        cpasscustomer = (TextInputLayout)findViewById(R.id.Cpasscustomer);
        mobilenocustomer = (TextInputLayout)findViewById(R.id.Mobilenocustomer);
        Statespincustomer = (Spinner) findViewById(R.id.Stateecustomer);
        Cityspincustomer = (Spinner) findViewById(R.id.Cityscustomer);
        signupcustomer = (Button)findViewById(R.id.Signupcustomer);
        Emaillcustomer = (Button)findViewById(R.id.emailcustomer);
        Phonecustomer = (Button)findViewById(R.id.phonecustomer);
        Cppcustomer = (CountryCodePicker)findViewById(R.id.CountryCodecustomer);
        Statespincustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                stateecustomer = value.toString().trim();
                if(stateecustomer.equals("Ha Noi")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : HaNoi){
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_item,list);
                    Cityspincustomer.setAdapter(arrayAdapter);
                }
                if(stateecustomer.equals("Ho Chi Minh")){
                    ArrayList<String> list = new ArrayList<>();
                    for (String cities : HoChiMinh){
                        list.add(cities);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,android.R.layout.simple_spinner_item,list);
                    Cityspincustomer.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Cityspincustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                cityycustomer = value.toString().trim();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        databaseReferencecustomer = firebaseDatabasecustomer.getInstance().getReference("Customer");
        FAuthcustomer = FirebaseAuth.getInstance();
        signupcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namecustomer = Namecustomer.getEditText().getText().toString().trim();
                emailidcustomer = Emailcustomer.getEditText().getText().toString().trim();
                mobilecustomer = mobilenocustomer.getEditText().getText().toString().trim();
                passwordcustomer= Passcustomer.getEditText().getText().toString().trim();
                confpasswordcustomer = cpasscustomer.getEditText().getText().toString().trim();
                if (isValid())
                {
                    Toast.makeText(Registration.this,"OKKK",Toast.LENGTH_SHORT).show();
                    final ProgressDialog mDialog = new ProgressDialog(Registration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress please wait......");
                    mDialog.show();
                    FAuthcustomer.createUserWithEmailAndPassword(emailidcustomer,passwordcustomer).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReferencecustomer = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReferencecustomer.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String , String> hashMap1 = new HashMap<>();
                                        hashMap1.put("MobileNo",mobilecustomer);
                                        hashMap1.put("Name",namecustomer);
                                        hashMap1.put("EmailId",emailidcustomer);
                                        hashMap1.put("City",cityycustomer);
                                        hashMap1.put("Password",passwordcustomer);
                                        hashMap1.put("State",stateecustomer);
                                        hashMap1.put("ConfirmPassword",confpasswordcustomer);
                                        firebaseDatabasecustomer.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();
                                                FAuthcustomer.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                                            builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Okkkkk", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            AlertDialog Alert = builder.create();
                                                            Alert.show();
                                                        }else{
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(Registration.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }

                        }
                    });
                }
            }

        });
        Emaillcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
                finish();
            }
        });
        Phonecustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Loginphone.class));
                finish();
            }
        });

    }
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Emailcustomer.setErrorEnabled(false);
        Emailcustomer.setError("");
        Namecustomer.setErrorEnabled(false);
        Namecustomer.setError("");
        Passcustomer.setErrorEnabled(false);
        Passcustomer.setError("");
        mobilenocustomer.setErrorEnabled(false);
        mobilenocustomer.setError("");
        cpasscustomer.setErrorEnabled(false);
        cpasscustomer.setError("");
        boolean isValid=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false;
        if(TextUtils.isEmpty(namecustomer)){
            Namecustomer.setErrorEnabled(true);
            Namecustomer.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(emailidcustomer)){
            Emailcustomer.setErrorEnabled(true);
            Emailcustomer.setError("Email Is Required");
        }else{
            if(emailidcustomer.matches(emailpattern)){
                isValidemail = true;
            }else{
                Emailcustomer.setErrorEnabled(true);
                Emailcustomer.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(passwordcustomer)){
            Passcustomer.setErrorEnabled(true);
            Passcustomer.setError("Enter Password");
        }else{
            if(passwordcustomer.length()<8){
                Passcustomer.setErrorEnabled(true);
                Passcustomer.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpasswordcustomer)){
            cpasscustomer.setErrorEnabled(true);
            cpasscustomer.setError("Enter Password Again");
        }else{
            if(!passwordcustomer.equals(confpasswordcustomer)){
                cpasscustomer.setErrorEnabled(true);
                cpasscustomer.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobilecustomer)){
            mobilenocustomer.setErrorEnabled(true);
            mobilenocustomer.setError("Mobile Number Is Required");
        }else{
            if(mobilecustomer.length()<10){
                mobilenocustomer.setErrorEnabled(true);
                mobilenocustomer.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        isValid = (isValidconfpassword && isValidpassword  && isValidemail && isValidmobilenum && isValidname ) ? true : false;
        return isValid;

    }
}