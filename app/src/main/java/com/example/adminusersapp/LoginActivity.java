package com.example.adminusersapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText  email, password;
    TextView errors;
    Button loginbtn;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = new DbHandler(LoginActivity.this);
        email = findViewById(R.id.loginUserEmail);
        password = findViewById(R.id.LoginUserPassword);
        errors = findViewById(R.id.loginError);
        loginbtn =findViewById(R.id.verifyLoginBtn);


        loginbtn.setOnClickListener(v ->{
            String strEmail = email.getText().toString().trim();
            String strPassword = password.getText().toString().trim();

            if(strEmail.isEmpty() || strPassword.isEmpty()){
                errors.setText("All Feilds are Mandatory");
            }
            else{
                if(dbHandler.checkUserEmail(strEmail,strPassword)){
                    Toast.makeText(LoginActivity.this, "LOGIN SUCCESS",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, SuccessLoginActivity.class);
                    intent.putExtra("Email",strEmail);
                    startActivity(intent);
                    finish();
                    return;
                }

                    errors.setText("Invalid Credentials");
                    password.setText("");

            }

        });




    }
}