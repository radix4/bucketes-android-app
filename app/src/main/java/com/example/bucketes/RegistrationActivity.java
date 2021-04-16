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

public class RegistrationActivity extends AppCompatActivity {
    private static String TAG = "RegistrationActivity";

    private EditText txtWarnPassword1, txtWarnPassword2;
    private TextView txtWarnName;
    private ConstraintLayout parent;

    private TextView tvLinkToActivityLogin, WarnName, WarnPassword1, WarnPassword2;
    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // initialize variables
        btnRegister = findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etRegisterUsername);
        etPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);
        tvLinkToActivityLogin = findViewById(R.id.etLinkToActivityLogin);
        WarnName = findViewById(R.id.textWarnNameR);
        WarnPassword1 = findViewById(R.id.textWarnPassword1R);
        WarnPassword2 = findViewById(R.id.textWarnPassword2R);



        /* this button registers the user*/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(RegistrationActivity.this);
                UserModel userModel;

                // attempt to instantiate user
                try {
                    userModel = new UserModel(etUsername.getText().toString(), etPassword.getText().toString());
                } catch (Exception e) {
                    userModel = new UserModel("error", "error");

                    Toast.makeText(RegistrationActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                }

                boolean success = dbHelper.addUser(userModel);

                // toasts to validate user creation
                if (success && etPassword.getText().toString().equals(etConfirmPassword.getText().toString()) && !etUsername.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // No name entered
                    if (etUsername.getText().toString().equals("")) {
                        WarnName.setText("Enter Name");
                        WarnName.setVisibility(View.VISIBLE);
                    }

                    // Check passwords
                    if (etPassword.getText().toString().equals("")) {
                        WarnPassword1.setText("Enter Password");
                        WarnPassword1.setVisibility(View.VISIBLE);
                    }

                    // Password is not confirmed
                    if (etConfirmPassword.getText().toString().equals("")) {
                        WarnPassword2.setVisibility(View.VISIBLE);
                        WarnPassword2.setText("Confirm Password");
                    }

                    if (!etPassword.getText().toString().equals("") && !etConfirmPassword.getText().toString().equals("")){
                        // Password != Password confirm
                        WarnPassword1.setVisibility(View.VISIBLE);
                        WarnPassword2.setVisibility(View.VISIBLE);
                        WarnPassword1.setText("Passwords don't match");
                        WarnPassword2.setText("Passwords don't match");
                    }

                    // Hide warning if it was taken care of
                    if (!etUsername.getText().toString().equals("")) {
                        WarnName.setVisibility(View.INVISIBLE);
                    }

                    if (!etPassword.getText().toString().equals("")) {
                        WarnPassword1.setVisibility(View.INVISIBLE);
                    }

                    if (!etConfirmPassword.getText().toString().equals("")) {
                        WarnPassword2.setVisibility(View.INVISIBLE);
                    }
                    Toast.makeText(RegistrationActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    }
                }
        });

        // upon click switches to Activity Login
        tvLinkToActivityLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
