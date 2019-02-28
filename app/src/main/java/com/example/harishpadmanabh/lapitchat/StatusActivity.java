package com.example.harishpadmanabh.lapitchat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private TextInputLayout mStatus;
    private Button mSavebtn;
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrentUser.getUid();
        mStatusDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mtoolbar=(Toolbar) findViewById(R.id.status_appbar);

        mStatus=(TextInputLayout) findViewById(R.id.status_input);
        mSavebtn=findViewById(R.id.status_save_btn);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Account Status ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprogress=new ProgressDialog(StatusActivity.this);

                mprogress.setTitle("Saving Changes ");
                mprogress.setMessage("Please wait while we save the changes ");
                mprogress.dismiss();

                String status=mStatus.getEditText().getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            mprogress.dismiss();
                        }
                        else 
                        {
                            Toast.makeText(StatusActivity.this, "There was some error in saving changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}