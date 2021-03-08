package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    private EditText edtTxtName, edtTxtPassword;
    private Button btnLogin;
    private TextView txtWarnName,txtWarnPassword;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLogin();
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
        if (edtTxtName.getText().toString().equals("")) {
            txtWarnName.setVisibility(View.VISIBLE);
            txtWarnName.setText("Enter Name");
            return false;
        }

        if (edtTxtPassword.getText().toString().equals("")) {
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Enter Password");
            return false;
        }

        // Check with database
        if (edtTxtPassword.getText().toString().equals("check")) {
            txtWarnPassword.setVisibility(View.VISIBLE);
            txtWarnPassword.setText("Wrong Password");
            return false;
        }

        return true;
    }

    private void initViews() {
        Log.d(TAG, "initViews: Started");

        edtTxtName = findViewById(R.id.editTextNameS);
        edtTxtPassword = findViewById(R.id.editTextPasswordS);

        btnLogin = findViewById(R.id.loginButton);

        txtWarnName = findViewById(R.id.textWarnNameS);
        txtWarnPassword = findViewById(R.id.textWarnPasswordS);

        parent = findViewById(R.id.parent1);
    }
}