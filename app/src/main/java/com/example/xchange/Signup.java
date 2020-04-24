package com.example.xchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {

    EditText usname,password,mobile,email;
    Button dragin,register,verify;
    FirebaseAuth firebaseAuth;
    DatabaseReference myref;
    String name,mail,pass,ph;
    User userprof;
    private  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usname=findViewById(R.id.user1);
        password=findViewById(R.id.password1);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        dragin=findViewById(R.id.button2);
        register=findViewById(R.id.register);
        register.setEnabled(false);
        verify=findViewById(R.id.verify);
        firebaseAuth=FirebaseAuth.getInstance();
        myref=FirebaseDatabase.getInstance().getReference();



        dragin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Signup.this,MainActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.in_left,R.anim.out_left);
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.getCurrentUser().reload();
                if(firebaseAuth.getCurrentUser().isEmailVerified())
                {
                    progressDialog=new ProgressDialog(Signup.this);
                    progressDialog.setMax(3500);
                    progressDialog.setTitle("Registering Details....");
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.show();
                    writedata(mail,ph,name);
                    Toast.makeText(Signup.this, "REGISTERED Successfully...",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Signup.this,Afterlogin.class));


                }
                else
                {
                    Toast.makeText(Signup.this, "Verify your E-mail/Click again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

   /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(Signup.this,Afterlogin.class));
        }
    }*/

    public void signup()
    {
        name=usname.getText().toString();
        pass=password.getText().toString();
        ph=mobile.getText().toString();
        mail=email.getText().toString();
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(ph)||TextUtils.isEmpty(mail))
        {
            Toast.makeText(Signup.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                verifyemail();
                                register.setEnabled(true);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    public void writedata(String mail,String ph,String name)
    {
        String user_id=firebaseAuth.getCurrentUser().getUid();
        userprof=new User(mail,ph,name);
        myref.child("Users").child(user_id).setValue(userprof);
    }

    public void verifyemail()
    {
        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Signup.this, "Verification link sent to E-mail",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Signup.this, "Verification error",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}


