package com.example.twittercc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    EditText  Uname ,password;
    Button Finish;
    ImageView Back;
    String Name,Email,DOB;
    private FirebaseAuth mAuth;
    final String TAG = this.getClass().getSimpleName();
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //automatically login user
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Name = getIntent().getStringExtra("name");
        Email = getIntent().getStringExtra("email");
        DOB = getIntent().getStringExtra("dob");
        Uname = findViewById(R.id.uname_edt);
        password = findViewById(R.id.password_edt);
        Finish = findViewById(R.id.finish_btn);
        Back = findViewById(R.id.back_btn);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(Uname.getText().toString(),password.getText().toString())){
                    Register(Email,password.getText().toString());
                }else{
                    Toast.makeText(SignIn.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Register(String Email,String pass){
        mAuth.createUserWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            nextpage(user);
                        } else {
                            try{
                                throw task.getException();
                            }catch(FirebaseAuthUserCollisionException e){
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignIn.this, "You are already registered ", Toast.LENGTH_SHORT).show();
                            }catch(Exception e ){
                                e.printStackTrace();
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void nextpage(FirebaseUser user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Tweets");
        userRef.child(user.getUid()).child("name").setValue(Name);
        userRef.child(user.getUid()).child("email").setValue(Email);
        userRef.child(user.getUid()).child("dob").setValue(DOB);
        userRef.child(user.getUid()).child("username").setValue(Uname.getText().toString());

        Intent intent = new Intent(SignIn.this,Home.class);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("is_user_logged",true).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("display_name",Name).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("uname",Uname.getText().toString()).commit();
        startActivity(intent);
    }
    public boolean validate(String uname, String password)
    {
        if(uname.equals("") || password.equals(""))
            return false;
        return true;

    }
}