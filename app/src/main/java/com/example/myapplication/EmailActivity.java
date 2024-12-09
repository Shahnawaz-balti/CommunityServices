package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmailActivity extends AppCompatActivity {

    EditText mailTo, subject, mText;
    String to, mSubject, text;
    Button sBtn, vBtn, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mailTo = findViewById(R.id.mailTo);
        subject = findViewById(R.id.subject);
        mText = findViewById(R.id.text);
        sBtn = findViewById(R.id.sendButton);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EmailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to= mailTo.getText().toString();
                mSubject= subject.getText().toString();

                text= mText.getText().toString();
                if(to.isEmpty() && mSubject.isEmpty() && text.isEmpty()){
                    Toast.makeText(EmailActivity.this, "All Fields are required", Toast.LENGTH_LONG ).show();

                }
                else
                {
                    sendMail(to, mSubject, text);
                }
            }
        });
    }

    private void sendMail(String to, String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");//  intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(intent, "Choose one"));

    }
    }
