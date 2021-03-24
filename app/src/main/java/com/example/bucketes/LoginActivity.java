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

    private TextView txtWarnName,txtWarnPassword;
    private ConstraintLayout parent;
    DBHelper DB;

    // initialize variables
    private TextView tvLinkToActivityRegistration;
    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLinkToActivityRegistration = findViewById(R.id.tvLinkToActivityRegistration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(LoginActivity.this);   // create reference to db
                UserModel userModel;


                // attempt to instantiate user
                try {
                    userModel = new UserModel(etUsername.getText().toString(), etPassword.getText().toString());
                } catch (Exception e) {
                    userModel = new UserModel("error", "error");
                    Toast.makeText(LoginActivity.this, "Error create user", Toast.LENGTH_SHORT).show();
                }

                boolean success = dbHelper.validateUser(userModel);

                if (success) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // upon click switches to Activity Registration
        tvLinkToActivityRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

}
