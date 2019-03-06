package com.example.harishpadmanabh.lapitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private CircleImageView mDisplayImage;
    private TextView mName;
    private  TextView mStatus;

    private Button mStatusBtn;
    private Button mImagebtn;

    private static int GALLERY_PICK=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage=(CircleImageView) findViewById(R.id.settings_image) ;
        mName=findViewById(R.id.settings_name);
        mStatus=findViewById(R.id.settings_status);

        mStatusBtn=findViewById(R.id.settings_status_btn);
        mImagebtn=findViewById(R.id.settings_image_btn);
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=mCurrentUser.getUid();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String thumb_image=dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String status_value=mStatus.getText().toString();
                Intent kl=new Intent(SettingsActivity.this,StatusActivity.class);
                kl.putExtra("status_value",status_value);
                startActivity(kl);
            }
        });
        mImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent gal=new Intent();
            gal.setType("image/+");
            gal.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gal,"SELECT_IMAGE"),GALLERY_PICK);

                // start picker to get image for cropping and then use the image in cropping activity
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(SettingsActivity.this);
            }
        });
    }
}
