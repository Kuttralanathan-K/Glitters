package com.example.xchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText user,pass;
    Button dragup,login;
    FirebaseAuth firebaseAuth;
    private  ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=findViewById(R.id.user);
        pass=findViewById(R.id.password);
        dragup=findViewById(R.id.button);
        login=findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();

        dragup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_right,R.anim.out_right);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signinauth();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals("BevanuHOr9bgRsilap0lRs3QaPy2")){
                startActivity(new Intent(MainActivity.this,AdminList.class));
            }
            else {
                startActivity(new Intent(MainActivity.this,Afterlogin.class));
            }
        }
    }


    public void signinauth()
    {
        String username=user.getText().toString();
        String password=pass.getText().toString();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
        {
            Toast.makeText(MainActivity.this,"Please enter the username/password",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMax(3500);
            progressDialog.setTitle("Logging In....");
            progressDialog.setMessage("Please Wait....");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressDialog.dismiss();
                                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals("BevanuHOr9bgRsilap0lRs3QaPy2")){
                                    startActivity(new Intent(MainActivity.this,AdminList.class));
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this,Afterlogin.class));
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
