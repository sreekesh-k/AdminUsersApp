package com.example.adminusersapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "ADMINUSERDB";
    private static int DB_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_ADMIN = "admins";
    private static final String COL_ADMIN_ID = "id";
    private static final String COL_ADMIN_NAME = "name";
    private static final String COL_ADMIN_PASSWORD = "password";


    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT UNIQUE, password TEXT);
        String query = "CREATE TABLE " + TABLE_USER + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_USERNAME + " TEXT, " + COL_EMAIL + " TEXT UNIQUE, " + COL_PASSWORD + " TEXT)";

        db.execSQL(query);

        // Create admins table
        String createAdminsTable = "CREATE TABLE " + TABLE_ADMIN + " (" +
                COL_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ADMIN_NAME + " TEXT, " +
                COL_ADMIN_PASSWORD + " TEXT)";
        db.execSQL(createAdminsTable);

        // Insert default admin ("admin", "admin")
        ContentValues values = new ContentValues();
        values.put(COL_ADMIN_NAME, "admin");
        values.put(COL_ADMIN_PASSWORD, "admin");
        db.insert(TABLE_ADMIN, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DROP TABLE IF EXISTS users;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    }

    public boolean addUser(String username, String email, String password){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_USERNAME,username);
        values.put(COL_EMAIL,email);
        values.put(COL_PASSWORD,password);

        //tablename, null, values
        long result = db.insert(TABLE_USER, null,values);

        return result != -1;//result -1 false result not -1 true
    }

    public boolean checkUserEmail(String email, String password){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        //SELECT * FROM users WHERE email=? AND password=?
        cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + "=?" + " AND " +
                        COL_PASSWORD + "=?",new String[]{email,password});//String str[] = {}

        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean checkAdminCredentials(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE " + COL_ADMIN_NAME + "=?" +
                " AND " + COL_ADMIN_PASSWORD + "=?", new String[]{name, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor listAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        return db.rawQuery(query, null);
    }



}
