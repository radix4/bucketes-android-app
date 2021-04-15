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

import com.example.bucketes.models.UserModel;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    private TextView txtWarnName,txtWarnPassword;
    private ConstraintLayout parent;
    DBHelper DB;

    public static UserModel user;

    // initialize variables
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

        /* instantiate user */
        user = new UserModel(etUsername.getText().toString(), etPassword.getText().toString());

        /* ===== login ===== */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(LoginActivity.this);   // create reference to db
                UserModel userModel;

                /* attempt to instantiate user */
                try {
                    userModel = new UserModel(etUsername.getText().toString(), etPassword.getText().toString());
                } catch (Exception e) {
                    userModel = new UserModel("error", "error");
                    Toast.makeText(LoginActivity.this, "Error create user", Toast.LENGTH_SHORT).show();
                }

                boolean success = dbHelper.validateUser(userModel);

                if (success) {
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
                    else if (etPassword.getText().toString().equals("")) {
                        WarnPassword.setVisibility(View.VISIBLE);
                        WarnPassword.setText("Enter Password");
                    }
                    else {
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
