package com.example.twittercc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.prefs.Preferences;

public class NewTweet extends AppCompatActivity {
    Button tweetSubmit;
    ImageView Close;
    EditText Tweetmessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        Tweetmessage = findViewById(R.id.tweetmessage);
        Close = findViewById(R.id.close_btn);
        tweetSubmit = findViewById(R.id.tweet_btn);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Tweets");

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String username = PreferenceManager.getDefaultSharedPreferences(this).getString("Username","Shashank S p");
        String displayname = PreferenceManager.getDefaultSharedPreferences(this).getString("displayname","ShashankAppu");

        tweetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Tweetmessage.getText().toString().equals("")){
                    tweetSubmit.setEnabled(false);
                    String key = userRef.push().getKey();
                    userRef.child(key).child("message").setValue(Tweetmessage.getText().toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh.mma MM-dd-yyyy", Locale.US);
                    String CurrentDnT = simpleDateFormat.format(new Date());
                    userRef.child(key).child("Publishedtime").setValue(CurrentDnT);
                    userRef.child(key).child("displayname").setValue(displayname);
                    userRef.child(key).child("Username").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            onBackPressed();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(NewTweet.this,"Something went wrong while saving your tweet",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(NewTweet.this,"Enter your tweet first",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}