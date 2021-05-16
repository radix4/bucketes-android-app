package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketes.models.User;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";
    public static User user;
    private TextView tvLinkToActivityRegistration, WarnName, WarnPassword;
    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* initialize views by id */
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLinkToActivityRegistration = findViewById(R.id.tvLinkToActivityRegistration);
        WarnName = findViewById(R.id.textWarnNameS);
        WarnPassword = findViewById(R.id.textWarnPasswordS);

        /* ===== login ===== */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(LoginActivity.this);   // create reference to db

                /* attempt to instantiate user */
                try {
                    user = new User(etUsername.getText().toString(), etPassword.getText().toString());
                } catch (Exception e) {
                    user = new User("error", "error");
                    Toast.makeText(LoginActivity.this, "Error create user", Toast.LENGTH_SHORT).show();
                }

                boolean success = dbHelper.validateUser(user);

                if (success && !etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // No name entered
                    if (etUsername.getText().toString().equals("")) {
                        WarnName.setVisibility(View.VISIBLE);
                        WarnName.setText("Enter Name");
                    }
                    // No password entered
                    if (etPassword.getText().toString().equals("")) {
                        WarnPassword.setVisibility(View.VISIBLE);
                        WarnPassword.setText("Enter Password");
                    }

                    // Hide warning if it was taken care of
                    if (!etUsername.getText().toString().equals("")) {
                        WarnName.setVisibility(View.INVISIBLE);
                    }
                    if (!etPassword.getText().toString().equals("")) {
                        WarnPassword.setVisibility(View.INVISIBLE);
                    }

                    if (!etPassword.getText().toString().equals("") && !etUsername.getText().toString().equals("")){
                        // Wrong name or password
                        WarnPassword.setVisibility(View.VISIBLE);
                        WarnName.setVisibility(View.VISIBLE);
                        WarnName.setText("Wrong Name or Password");
                        WarnPassword.setText("Wrong Name or Password");
                    }

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
