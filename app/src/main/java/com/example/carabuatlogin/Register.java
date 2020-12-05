package com.example.carabuatlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button pindah,btnRegister;
    private EditText inputUsername, inputPassword, inputEmail, inputConformPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Register.this);


        pindah = findViewById(R.id.Btnbacklogin);
        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });



        btnRegister = findViewById(R.id.Btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });


    }

    private void checkCrededentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confromPassword = inputConformPassword.getText().toString();


        if (username.isEmpty() || username.length()<7)
        {
            showError(inputUsername, " Your username is not valid!");
        }
        else if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail, "Email is not valid");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword, "Password must be 7 character");
        }
        else if (confromPassword.isEmpty() || !confromPassword.equals(password))
        {
            showError(inputConformPassword, "Password no match!");
        }
        else
        {

            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait while check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {


                        Toast.makeText(Register.this, "Successfully Registration", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();

                        Intent intent = new Intent(Register.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else
                    {

                        Toast.makeText(Register.this, task.getException(). toString(), Toast.LENGTH_SHORT).show();


                    }
                }
            });

    }
}

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
    }