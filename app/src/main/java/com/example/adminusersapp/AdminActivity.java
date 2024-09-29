package com.example.adminusersapp;

import android.database.Cursor;
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

public class AdminActivity extends AppCompatActivity {

    public EditText username,password;
    public Button login;
    public TextView userlist;
    public DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.adminname);
        password = findViewById(R.id.adminpassword);
        userlist = findViewById(R.id.userslist);
        dbHandler = new DbHandler(AdminActivity.this);
        login = findViewById(R.id.verifyAdminbtn);

        login.setOnClickListener(v ->{
            String strName = username.getText().toString().trim();
            String strPassword = password.getText().toString().trim();

            if(strName.isEmpty() || strPassword.isEmpty()){
                Toast.makeText(AdminActivity.this, "All Fields are needed",Toast.LENGTH_LONG).show();
            }
            else{
                if(dbHandler.checkAdminCredentials(strName,strPassword)){
                    username.setText("");
                    password.setText("");
                    Cursor cursor = dbHandler.listAllUsers();
                    String users = "";
                    int i = 1;
                    if(cursor.moveToFirst()){
                        do{
                            //0-id, 1-username,2-email, 3-password
                           users = userlist.getText().toString();
                           users +=
                                   "\n" + i + ". " +cursor.getString(1) + " - "
                                   +cursor.getString(2) + " - "
                                   +cursor.getString(3);
                           i++;
                            userlist.setText(users);

                        }while(cursor.moveToNext());
                    }
                }
                else{
                    Toast.makeText(AdminActivity.this, "Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}