package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    private final String UKEY = "uKey";
    private final String PKEY = "pKey";
    private final String EKEY = "eKey";
    private final String PREF_NAME = "Authentication";
    EditText uname, email, password;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);

    }
    Intent intent;
    public void signup(View view) {
        String name = uname.getText().toString().trim();
        String eemail = email.getText().toString().trim();
        String ppass = password.getText().toString().trim();
        System.out.println("This is a console print statement:   "+name);
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(name.isEmpty() && eemail.isEmpty() && ppass.isEmpty()){
            Toast.makeText(SignUpActivity.this,"All Fields are required",Toast.LENGTH_LONG ).show();
        }else if (!isValidEmail(eemail)) {
            Toast.makeText(SignUpActivity.this, "Invalid email address", Toast.LENGTH_LONG).show();
        } else if (!isValidPassword(ppass)) {
            Toast.makeText(SignUpActivity.this, "Password must include at least one uppercase" +
                    " letter,one lowercase letter, one number, and one special character", Toast.LENGTH_LONG).show();
        }
        else {
            editor.putString(UKEY, name);
            editor.putString(EKEY, eemail);
            editor.putString(PKEY, ppass);
            editor.apply();
            intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void login(View view) {
        intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }

}