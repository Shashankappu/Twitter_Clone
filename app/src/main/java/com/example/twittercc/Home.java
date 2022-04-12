package com.example.twittercc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Tweet> tweetList = new ArrayList<>();
    FloatingActionButton AddTweet ;
    ImageView btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AddTweet = findViewById(R.id.fab);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tweetRef = database.getReference("Tweets");
        TweetAdapter tweetAdapter = new TweetAdapter(Home.this, android.R.layout.simple_list_item_2,tweetList);
        //Navigation menu
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        btnMenu = findViewById(R.id.menu_btn);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        View headerView = navigationView.getHeaderView(0);
        TextView txtHeaderName = headerView.findViewById(R.id.txt_displayname);
        TextView txtHeaderUserName = headerView.findViewById(R.id.txt_username);
        txtHeaderName.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("Username","Shashank S p"));
        txtHeaderUserName.setText("@"+PreferenceManager.getDefaultSharedPreferences(this).getString("displayname","ShashankAppu"));

         btnMenu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
             }
         });

        tweetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    tweetList.clear();
                    for (DataSnapshot tweetdataSnapshot : snapshot.getChildren()) {
                        tweetList.add(new Tweet(tweetdataSnapshot.child("Username").getValue().toString(),
                                tweetdataSnapshot.child("displayname").getValue().toString(),
                                tweetdataSnapshot.child("message").getValue().toString(),
                                tweetdataSnapshot.child("Publishedtime").getValue().toString()));
                    }
                    tweetAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Something is wrong with the database", Toast.LENGTH_SHORT).show();
            }
        });
        AddTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Home.this,NewTweet.class);
            startActivity(intent);
            }
        });
        RecyclerView tweetRv = findViewById(R.id.tweet_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        tweetRv.setLayoutManager(linearLayoutManager);
        tweetRv.setAdapter(tweetAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
       if( item.getItemId() == R.id.item9){
           Intent intent = new Intent(Home.this,Splash.class);
           startActivity(intent);
//           FirebaseAuth.getInstance().signOut();
//           PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("is_user_logged",false).commit();
//           PreferenceManager.getDefaultSharedPreferences(this).edit().putString("display_name","").clear().commit();
//           PreferenceManager.getDefaultSharedPreferences(this).edit().putString("uname","").clear().commit();
           finish();
       }
       return true;
    }
}