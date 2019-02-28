package com.example.harishpadmanabh.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private TextInputLayout LoginEmail;
    private TextInputLayout LoginPassword;
    private Button login;
    private ProgressDialog loginProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mtoolbar=(Toolbar) findViewById(R.id.log_toolbar);
        LoginEmail=findViewById(R.id.log_email);
        LoginPassword=findViewById(R.id.log_password);
        login=findViewById(R.id.lo_gin);
        loginProgress=new ProgressDialog(this);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=LoginEmail.getEditText().getText().toString();
                String password=LoginPassword.getEditText().getText().toString();
                if(!TextUtils.isEmpty(email)||(!TextUtils.isEmpty(password)))
                {
                    loginProgress.setTitle("Logging user");
                    loginProgress.setMessage("Please wait while we check your credentials");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email,password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    loginProgress.dismiss();
                    Intent r=new Intent(LoginActivity.this,MainActivity.class);
                    r.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(r);
                    finish();
                }
                else {

                    loginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Can't sign in .Please check the form and try again.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
