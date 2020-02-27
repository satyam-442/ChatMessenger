package com.example.sonoflordshiva.chatmessenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    private EditText mUsername, mEmail, mPassword, mContact;
    private Button mCreateBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Toolbar mToolbar;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loadingBar=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mUsername = (EditText) findViewById(R.id.reg_display_name);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mContact = (EditText) findViewById(R.id.reg_contact);
        mCreateBtn = (Button) findViewById(R.id.reg_create_btn);
        mCreateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = mUsername.getText().toString();
                String contact = mContact.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(TextUtils.isEmpty(username))
                { Toast.makeText(RegisterActivity.this, "Username is Empty", Toast.LENGTH_SHORT).show(); }
                if(TextUtils.isEmpty(contact))
                { Toast.makeText(RegisterActivity.this, "Contact is empty", Toast.LENGTH_SHORT).show(); }
                if(contact.length()!=10)
                { Toast.makeText(RegisterActivity.this, "Enter Correct Contact no", Toast.LENGTH_SHORT).show(); }
                if(TextUtils.isEmpty(email))
                { Toast.makeText(RegisterActivity.this, "E-mail is empty", Toast.LENGTH_SHORT).show(); }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                { Toast.makeText(RegisterActivity.this, "Incorrect E-mail", Toast.LENGTH_SHORT).show(); }
                if(TextUtils.isEmpty(password))
                { Toast.makeText(RegisterActivity.this, "Password Is Empty", Toast.LENGTH_SHORT).show(); }
                if (password.length()<8)
                { Toast.makeText(RegisterActivity.this, "Password must be atleast 8 characters", Toast.LENGTH_SHORT).show(); }
                else
                {
                    register(username,password,email,contact);
                }
            }
        });
    }

    private void register(final String username, final String password, final String email, final String contact)
    {
        loadingBar.setTitle("Registering Users");
        loadingBar.setMessage("Please wait while we create our account");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser current_user = mAuth.getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    //String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("ID",uid);
                    hashMap.put("name",username);
                    hashMap.put("email",email);
                    hashMap.put("password",password);
                    hashMap.put("contact",contact);
                    //hashMap.put("device_token",device_token);
                    hashMap.put("status","Hi there, I'm using Chap App");
                    hashMap.put("image","default");
                    hashMap.put("thumb_image","default");

                    mDatabase.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(mainIntent);
                                finish();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
                else
                {
                    String message=task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this,"Error Occured!"+ message,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}
