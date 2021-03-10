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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {
    private static String TAG = "RegistrationActivity";

    private EditText edtTxtName, edtTxtPassword1, edtTxtPassword2, txtWarnPassword1, txtWarnPassword2;
    private Button btnRegister;
    private TextView txtWarnName, loginLink;
    private ConstraintLayout parent;
    String name;
    String password1;
    String password2;
    DBHelper DB;

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

        if (DB.checkusername(name))
            showSnackBar("User already exists! Please sign in");

        else if (validateData() && DB.insertData(name, password1)) {
                showSnackBar("Registration Successful");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        else
            showSnackBar("Registration failed");
    }

    private void showSnackBar(String text) {
        Log.d(TAG, "showSnackBar: Started");
        txtWarnName.setVisibility(View.GONE);
        txtWarnPassword1.setVisibility(View.GONE);
        txtWarnPassword2.setVisibility(View.GONE);

        Snackbar.make(parent, text, 1000);
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: Started");

        // Check if any fields are empty
        if (edtTxtName.getText().toString().equals("") || edtTxtPassword1.getText().toString().equals("")
                || edtTxtPassword2.getText().toString().equals("")) {
            if (edtTxtName.getText().toString().equals("")) {
                txtWarnName.setVisibility(View.VISIBLE);
                txtWarnName.setText("Enter Name");
            }
            if (edtTxtPassword1.getText().toString().equals("")) {
                txtWarnPassword1.setVisibility(View.VISIBLE);
                txtWarnPassword1.setText("Enter Password");
            }
            if (edtTxtPassword2.getText().toString().equals("")) {
                txtWarnPassword2.setVisibility(View.VISIBLE);
                txtWarnPassword2.setText("Enter Password");
            }
            return false;
        }

        // Check if passwords match
        if (!password1.equals(password2)) {
            Toast.makeText(RegistrationActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            txtWarnPassword1.setVisibility(View.VISIBLE);
            txtWarnPassword2.setVisibility(View.VISIBLE);
            txtWarnPassword1.setText("Passwords don't match");
            txtWarnPassword2.setText("Passwords don't match");
            return false;
        }

        return true;
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");

        edtTxtName = findViewById(R.id.editTextNameR);
        edtTxtPassword1 = findViewById(R.id.editTextPassword1R);
        edtTxtPassword2 = findViewById(R.id.editTextPassword2R);

        name = edtTxtName.getText().toString();
        password1 = edtTxtPassword1.getText().toString();
        password2 = edtTxtPassword2.getText().toString();

        btnRegister = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        txtWarnName = findViewById(R.id.textWarnNameR);
        txtWarnPassword1 = findViewById(R.id.textWarnPassword1R);
        txtWarnPassword2 = findViewById(R.id.textWarnPassword2R);

        parent = findViewById(R.id.parent1);
        DB = new DBHelper(this);
    }

}