package com.example.gymassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import javax.annotation.Nullable;

public class Home extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    TextView username,email;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fStore;
    String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);

        mFirebaseAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();



        userId= mFirebaseAuth.getCurrentUser().getUid();
    //Not working code
       DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(Home.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText( documentSnapshot.getString("firstName"));

                email.setText(documentSnapshot.getString("email"));

            }
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        setupDrawerContent(nvDrawer);
    //Default fragment on creation
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl, new Dashboard()).commit();
            nvDrawer.setCheckedItem(R.id.db);
        }
    }




    //Toggling Side Navigation Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//Checking Which Item was CLicked in the Navigation Drawer and Replacing it
    public boolean selectItemDrawer(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.db:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl,new Dashboard()).commit();
                break;
            case R.id.search:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl,new Search()).commit();
                break;
            case R.id.events:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl,new Events()).commit();
                break;
            case R.id.edit_profile:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl,new editProfile()).commit();
                break;
            case R.id.feedback:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl,new Feedback()).commit();
                break;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Intent intLogin= new Intent(Home.this,Login.class);
                startActivity(intLogin);
                break;


        }


        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
        return true;
    }
    //Calling selectItemDrawer method whenever a navigation menu item is selected
    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }




}
