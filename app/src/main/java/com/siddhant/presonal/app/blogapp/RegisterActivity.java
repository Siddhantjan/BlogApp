package com.siddhant.presonal.app.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
private EditText reg_email_field;
private EditText reg_pass_field;
private EditText reg_confirm_pass_field;
private Button reg_login_btn;
private Button reg_btn;
private ProgressBar reg_progress;

private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        reg_email_field=findViewById(R.id.reg_email);
        reg_pass_field=findViewById(R.id.reg_password);
        reg_confirm_pass_field=findViewById(R.id.reg_confirm_password);
        reg_btn=findViewById(R.id.reg_button);
        reg_login_btn=findViewById(R.id.reg_login_button);
        reg_progress=findViewById(R.id.reg_progress);

        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=reg_email_field.getText().toString();
                String password=reg_pass_field.getText().toString();
                String confirmPassword=reg_confirm_pass_field.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {

                    if (password.equals(confirmPassword)) {
                        reg_progress.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email,confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Intent setupIntent = new Intent(RegisterActivity.this,SetupActivity.class);
                                    startActivity(setupIntent);
                                    finish();
                                }
                                else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : "+errorMessage, Toast.LENGTH_LONG).show();
                                }
                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Password didn't match",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendtoMain();
        }
    }

    private void sendtoMain(){
        Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
