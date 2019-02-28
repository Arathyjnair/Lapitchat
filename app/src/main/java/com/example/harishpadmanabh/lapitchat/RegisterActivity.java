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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

        private TextInputLayout mDisplayName;
        private TextInputLayout mEmail;
        private TextInputLayout mpassword;
        private Button crtebtn;
        private Toolbar mtoolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog regprogress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        mDisplayName=(TextInputLayout) findViewById(R.id.reg_display_name);
        mEmail=(TextInputLayout) findViewById(R.id.reg_email);
        mpassword=(TextInputLayout) findViewById(R.id.reg_password);
        crtebtn=(Button) findViewById(R.id.reg_create_btn);
        mtoolbar=(Toolbar) findViewById(R.id.register_toolbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regprogress=new ProgressDialog(this);



        crtebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name=mDisplayName.getEditText().getText().toString();
                String email=mEmail.getEditText().getText().toString();
                String password=mpassword.getEditText().getText().toString();
            if(!TextUtils.isEmpty(display_name)||(!TextUtils.isEmpty(email)||(!TextUtils.isEmpty(password))))
                {
                    regprogress.setTitle("Registering user");
                    regprogress.setMessage("Please wait while we creating the account");
                    regprogress.setCanceledOnTouchOutside(false);
                    regprogress.show();
                    register_user(display_name,email,password);
                }

            }
        });

    }

    private void register_user(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //To get the current user and user id

                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                            //o get user id
                            String uid=current_user.getUid();

                            //to store data

                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);//ROOT DIRECTORY
                            //COMPLEX
                            HashMap<String, String> userMap =new HashMap<>();
                            userMap.put("name",display_name);
                            userMap.put("status","Hi, i'm using ztalk App");
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");

                                    //to add the value

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        regprogress.dismiss();

                                        Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                            });


                        } else {

                            regprogress.hide();
                            Toast.makeText(RegisterActivity.this, "Can't sign in .Please check the form and try again.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
