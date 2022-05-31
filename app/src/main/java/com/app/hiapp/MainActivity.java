package com.app.hiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.app.hiapp.Adapter.SectionViewPager;
import com.app.hiapp.Fragments.HomeFragment;
import com.app.hiapp.Fragments.NotificationFragment;
import com.app.hiapp.Fragments.ProfileFragment;
import com.app.hiapp.Fragments.SearchFragment;
import com.app.hiapp.Fragments.StatusFragment;
import com.app.hiapp.Permissions.Permissions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    FloatingActionButton floatingActionButton;
    Fragment selectedfragment = null;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(fabnavigationListener);
        mAuth = FirebaseAuth.getInstance();
        Bundle intent = getIntent().getExtras();
        if (intent != null){
          String publisher = intent.getString("publisherid");

           SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
           editor.putString("profileid", publisher);
           editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
       } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        if (checkPermissionsArray(Permissions.PERMISSIONS)) {

        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    private void verifyPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(MainActivity.this, permissions , +1);
    }

    public boolean checkPermissionsArray(String[] permissions){
        for(int i = 0; i<permissions.length; i++){
            String check = permissions[i];
            if(!checkpermissions(check)){
                return false;
            }
        }
        return true;
    }

    public boolean checkpermissions(String check) {
        int permissionsrequest = ActivityCompat.checkSelfPermission(MainActivity.this, check);
        if(permissionsrequest != PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }

    private FloatingActionButton.OnClickListener fabnavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;
                        case R.id.nav_heart:
                            selectedfragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedfragment = new ProfileFragment();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };

    public void updateUI(FirebaseUser currentUser){
        if(currentUser == null){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }else{

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}