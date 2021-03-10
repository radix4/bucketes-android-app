package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {
    private static String TAG = "RegistrationActivity";

    private EditText edtTxtName, edtTxtPassword1, edtTxtPassword2, txtWarnPassword1, txtWarnPassword2;
    private Button btnRegister;
    private TextView txtWarnName, loginLink;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRegistration();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRegistration() {
        Log.d(TAG, "initRegistration: Started");

        if (validateData()) {
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Log.d(TAG, "showSnackBar: Started");
        txtWarnName.setVisibility(View.GONE);
        txtWarnPassword1.setVisibility(View.GONE);
        txtWarnPassword2.setVisibility(View.GONE);

        Snackbar.make(parent, "Registration Successful", 1000);
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: Started");

        // Check if name is empty
        if (edtTxtName.getText().toString().equals("")) {
            txtWarnName.setVisibility(View.VISIBLE);
            txtWarnName.setText("Enter Name");
            return false;
        }

        // Check if password is empty
        if (edtTxtPassword1.getText().toString().equals("")) {
            txtWarnPassword1.setVisibility(View.VISIBLE);
            txtWarnPassword1.setText("Enter Password");
            return false;
        }

        // Check if password confirmation is empty
        if (edtTxtPassword2.getText().toString().equals("")) {
            txtWarnPassword2.setVisibility(View.VISIBLE);
            txtWarnPassword2.setText("Confirm Password");
            return false;
        }

        // Check if passwords are the same
        if (!edtTxtPassword1.getText().toString().equals(edtTxtPassword2.getText().toString())) {
            txtWarnPassword1.setText("Passwords don't match");
            txtWarnPassword2.setText("Passwords don't match");
            txtWarnPassword1.setVisibility(View.VISIBLE);
            txtWarnPassword2.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");

        edtTxtName = findViewById(R.id.editTextNameS);
        edtTxtPassword1 = findViewById(R.id.editTextPassword1R);
        edtTxtPassword2 = findViewById(R.id.editTextPassword2R);

        btnRegister = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        txtWarnName = findViewById(R.id.textWarnNameR);
        txtWarnPassword1 = findViewById(R.id.textWarnPassword1R);
        txtWarnPassword2 = findViewById(R.id.textWarnPassword2R);

        parent = findViewById(R.id.parent1);
    }

}