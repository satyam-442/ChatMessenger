package com.example.sonoflordshiva.chatmessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity
{
    Button loginBtn, signupBtn;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.sign);

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginActivityPage();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterActivityPage();
            }
        });
    }

    private void RegisterActivityPage() {
        Intent regisIntent = new Intent(this,RegisterActivity.class);
        startActivity(regisIntent);
    }

    private void LoginActivityPage()
    {
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
