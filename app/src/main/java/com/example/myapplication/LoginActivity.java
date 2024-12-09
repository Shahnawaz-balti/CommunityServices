package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText user, pass;
    Button loginBtn, signupBtn;
    private final String UKEY = "uKey";
    private final String PKEY = "pKey";
    private final String EKEY = "eKey";
    private final String PREF_NAME = "Authentication";
    SharedPreferences preferences;

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


        user = findViewById(R.id.userName);
        pass = findViewById(R.id.pass);
        loginBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = user.getText().toString().trim();
                String passs = pass.getText().toString().trim();

                preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor  editor = preferences.edit();

                editor.putBoolean("flag",true);


                String sName = preferences.getString(UKEY, null);

                String sPass =preferences.getString(PKEY, null);



                editor.commit();

                if(name.equals(sName) && passs.equals(sPass)){
                    Intent  intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "User Not Exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}