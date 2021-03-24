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
    private TextView txtWarnName,txtWarnPassword;
    private ConstraintLayout parent;
    DBHelper DB;

    private TextView tvLinkToActivityRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLinkToActivityRegistration = findViewById(R.id.tvLinkToActivityRegistration);

        tvLinkToActivityRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

}
