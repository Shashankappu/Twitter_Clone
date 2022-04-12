package com.example.twittercc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference usersRef;
    TextView Close;
    private FirebaseAuth mAuth;
    EditText username,password;
    Button Next;
    final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Close = findViewById(R.id.closeLogin);
        username = findViewById(R.id.edt_uname);
        password = findViewById(R.id.edt_password);
        Next = findViewById(R.id.next_btn);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Tweets");
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(username.getText().toString(),password.getText().toString())){
                    Login(username.getText().toString(),password.getText().toString());
                }else{
                    Toast.makeText(Login.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Login(String Email,String pass){
        mAuth.signInWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "User successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this,Home.class);
                            PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putBoolean("is user logged in",true).commit();
                            usersRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("display_name",snapshot.child("displayname").getValue().toString()).commit();
                                    PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("uname",snapshot.child("Username").getValue().toString()).commit();
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public boolean validate(String uname, String password)
    {
        if(uname.equals("") || password.equals(""))
            return false;
        return true;

    }
}