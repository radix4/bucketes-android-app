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

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    private EditText edtTxtName, edtTxtPassword;
    private Button btnLogin;
    private TextView txtWarnName,txtWarnPassword, registrationLink;
    private ConstraintLayout parent;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLogin();
            }
        });

        registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLogin() {
        Log.d(TAG, "initLogin: Started");

        if (validateData()) {
            showSnackBar();
        }
    }

    private void showSnackBar() {
        Log.d(TAG, "showSnackBar: Started");
        txtWarnName.setVisibility(View.GONE);
        txtWarnPassword.setVisibility(View.GONE);

        Snackbar.make(parent, "Login Successful", 1000);
    }

    private boolean validateData() {
        Log.d(TAG, "validateData: Started");

        String name = edtTxtName.getText().toString();
        String password = edtTxtPassword.getText().toString();

        if (name.equals("") || password.equals("")) {
            if (name.equals("")) {
                txtWarnName.setVisibility(View.VISIBLE);
                txtWarnName.setText("Enter Name");
            }
            if (password.equals("")) {
                txtWarnPassword.setVisibility(View.VISIBLE);
                txtWarnPassword.setText("Enter Password");
            }
            return false;
        }

        // Check with database
        Boolean checkuserpass = DB.checkusernamepassword(name, password);
        if(checkuserpass==true){
            showSnackBar();
        }else{
            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");

        edtTxtName = findViewById(R.id.editTextNameS);
        edtTxtPassword = findViewById(R.id.editTextPasswordS);

        btnLogin = findViewById(R.id.loginButton);
        registrationLink = findViewById(R.id.linkToRegistration);

        txtWarnName = findViewById(R.id.textWarnNameS);
        txtWarnPassword = findViewById(R.id.textWarnPasswordS);

        parent = findViewById(R.id.parent1);
        DB = new DBHelper(this);
    }
}
