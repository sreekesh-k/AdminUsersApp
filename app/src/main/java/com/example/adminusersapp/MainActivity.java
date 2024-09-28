package com.example.adminusersapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button login,register,admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login = findViewById(R.id.loginbtn);
        register = findViewById(R.id.registerbtn);
        admin = findViewById(R.id.adminbtn);

        login.setOnClickListener(v -> {
            //from where to where
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            //from where to where
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

        admin.setOnClickListener(v -> {
            //from where to where
            Intent intent = new Intent(MainActivity.this,AdminActivity.class);
            startActivity(intent);
        });


    }
}