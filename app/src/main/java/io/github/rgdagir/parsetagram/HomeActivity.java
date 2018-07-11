package io.github.rgdagir.parsetagram;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // defining fragments
        final Fragment home = new Fragment();
        final Fragment postPic = new Fragment();
        final Fragment profile = new Fragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // main feed
                        FragmentTransaction homeTransaction = fragmentManager.beginTransaction();
                        homeTransaction.replace(R.id.homeFragment, home).addToBackStack(null).commit();
                        return true;
                    case R.id.explore:
                        // do nothing. TODO implement
                        return true;
                    case R.id.postpic:
                        // launch camera
                        FragmentTransaction postPicTransaction = fragmentManager.beginTransaction();
                        postPicTransaction.replace(R.id.postPicFragment, postPic).addToBackStack(null).commit();
                        return true;
                    case R.id.notifs:
                        // launch notifications tab (if time allows)
                        return true;
                    case R.id.profile:
                        FragmentTransaction profileTransaction = fragmentManager.beginTransaction();
                        profileTransaction.replace(R.id.profileFragment, profile).addToBackStack(null).commit();
                        return true;
                }
            }
        });


    }
}
