package com.example.gymassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    public EditText  emailId, password, fname, lname, height, weight;
    private Button btnSignup;
    private TextView tvSignIn;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore fstore;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        emailId= findViewById(R.id.email);
        password= findViewById(R.id.password);
        fname= findViewById(R.id.fname);
        lname= findViewById(R.id.lname);
        height= findViewById(R.id.height);
        weight= findViewById(R.id.weight);
        btnSignup = findViewById(R.id.signUp);
        tvSignIn = findViewById(R.id.signIn);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= emailId.getText().toString();
                final String pass= password.getText().toString();
                final String firstname= fname.getText().toString();
                final String lastname= lname.getText().toString();
                final String height1 = String.valueOf(height);
                final String weight1 = String.valueOf(weight);

                if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(SignUp.this,"Fields are Empty",Toast.LENGTH_SHORT ).show();

                }
                else if (firstname.isEmpty()){
                    fname.setError("Please enter First Name");
                    fname.requestFocus();
                }
                else if (lastname.isEmpty()){
                    lname.setError("Please enter Last Name");
                    lname.requestFocus();
                }
                else if (height1.isEmpty()){
                    height.setError("Please enter Height");
                    height.requestFocus();
                }
                else if (weight1.isEmpty()){
                    weight.setError("Please enter Weight");
                    weight.requestFocus();
                }
                else if (email.isEmpty()){
                    emailId.setError("Please enter email address");
                    emailId.requestFocus();
                }
                else if (pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(pass.length()<6){
                    password.setError("Password Less than 6 characters");
                    password.requestFocus();
                }

                else if (!(email.isEmpty() && pass.isEmpty() && firstname.isEmpty() && lastname.isEmpty() && height1.isEmpty() && weight1.isEmpty())){
                   mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               userID = mFirebaseAuth.getCurrentUser().getUid();
                               DocumentReference documentReference = fstore.collection("users").document(userID);
                               final Map<String,Object> user = new HashMap<>();
                               user.put("firstName", firstname);
                               user.put("lastName", lastname);
                               user.put("height", height1);
                               user.put("weight", weight1);
                               user.put("email",email);
                               user.put("password",pass);
                               documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       Log.d(TAG, "onSuccess: User Profile is created for"+ userID);
                                   }
                               });

                               startActivity(new Intent(SignUp.this, Home.class));
                           } else {

                               Toast.makeText(SignUp.this,"Sign UP Unsuccessful", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
                else{
                    Toast.makeText(SignUp.this,"Error Occurred, Please Try Again!",Toast.LENGTH_SHORT ).show();
                }
            }
        });
       tvSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(SignUp.this,Login.class);
               startActivity(i);
           }
       });
    }
}
