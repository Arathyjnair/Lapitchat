package com.example.harishpadmanabh.lapitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
private Button mRegBtn,malrdy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mRegBtn=findViewById(R.id.nwbtn);
        malrdy=findViewById(R.id.button2);
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(in);
            }
        });
        malrdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(k);
            }
        });
    }
}
