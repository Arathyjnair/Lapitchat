package com.example.harishpadmanabh.lapitchat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Ztalk");

        mViewPager=(ViewPager) findViewById(R.id.main_tabPager);

        mSectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout=(TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendtostart();
        }
    }

    private void sendtostart() {
        Intent in = new Intent(MainActivity.this, StartActivity.class);
        startActivity(in);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.main_menu,menu);
         return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         int id=item.getItemId();
         if(id==R.id.main_logout)
         {
             FirebaseAuth.getInstance().signOut();
             sendtostart();

         }
         if(id==R.id.main_settings)
         {
             Intent st=new Intent(MainActivity.this,SettingsActivity.class);
             startActivity(st);
         }
         return true;
    }
}
