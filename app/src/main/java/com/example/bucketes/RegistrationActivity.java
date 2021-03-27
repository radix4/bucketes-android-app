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

    private TextView tvLinkToActivityLogin;
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
                    Toast.makeText(RegistrationActivity.this, "Error create user", Toast.LENGTH_SHORT).show();
                }


                // check password == confirm password
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    boolean success = dbHelper.addUser(userModel);

                    // toasts to validate user creation
                    if (success)
                        Toast.makeText(RegistrationActivity.this, "Create user success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RegistrationActivity.this, "Error create user", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();;
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
