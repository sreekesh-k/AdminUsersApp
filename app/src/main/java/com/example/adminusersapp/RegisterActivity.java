package com.example.adminusersapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText registerUserName, registerEmail, registerPassword;
    TextView errors;
    Button registerBtn;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dbHandler = new DbHandler(RegisterActivity.this);
        //regex - regular expression
        registerUserName = findViewById(R.id.regitserUserName); //must not have numbers
        registerEmail = findViewById(R.id.regitserUserEmail);
        registerPassword = findViewById(R.id.RegisterUserPassword);//mustbe > 8

        errors = findViewById(R.id.registerError);


        registerBtn = findViewById(R.id.adduserbtn);



        registerBtn.setOnClickListener(v -> {
            String userName = registerUserName.getText().toString().trim();
            String email = registerEmail.getText().toString().trim();
            String password = registerPassword.getText().toString().trim();

            if(validateInputs(userName,email,password)){
                if(dbHandler.addUser(userName,email,password)){
                    Toast.makeText(RegisterActivity.this, "REGISTERED SUCCESSFULLY! YOU CAN NOW LOGIN",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "REGISTRATION FAILED",Toast.LENGTH_LONG).show();
                }
            }


        });
    }
    public boolean validateInputs(String username, String email, String password){

        //[a-zA-Z]+
        if(username.isEmpty() || !username.matches("[a-zA-Z]+") ){
            errors.setText("Username cannot be empty or Username cannot have numbers");
            return false;
        }
        if(email.isEmpty()){
            errors.setText("Email cannot be empty");
            return false;
        }
        if(password.isEmpty() || !(password.length() > 8) ){
            errors.setText("Password cannot be empty or Password length shoud be minimum 8");
            return false;
        }
        return true;
    }

}