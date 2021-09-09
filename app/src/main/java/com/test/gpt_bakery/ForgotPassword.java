package com.test.gpt_bakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText email;
    private Button rsButton,backButton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=(EditText)findViewById(R.id.emailforgetpw);
        rsButton=(Button)findViewById(R.id.resetpw);
        backButton=(Button)findViewById(R.id.back);
        auth=FirebaseAuth.getInstance();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Z = new Intent(ForgotPassword.this,MainMenu.class);
                startActivity(Z);
            }
        });
        rsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String e=email.getText().toString().trim();
        auth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword.this,"Check your email to reset password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
