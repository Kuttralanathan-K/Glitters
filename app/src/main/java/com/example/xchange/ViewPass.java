package com.example.xchange;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPass extends AppCompatActivity {

    String date,time,players;
    int cost;
    TextView passname,passdate,passtime,passcost,passplay;
    Button pay;
    DatabaseReference myref;
    User userinfo;
    final int UPI_PAYMENT = 1;
    String total;
    Map<String,String> map;
    BookedUser bookedUser;
    private  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pass);

        passname=findViewById(R.id.passname1);
        passdate=findViewById(R.id.passdate1);
        passtime=findViewById(R.id.passtime1);
        passcost=findViewById(R.id.passcost1);
        passplay=findViewById(R.id.passplay1);
        pay=findViewById(R.id.pay);
        myref=FirebaseDatabase.getInstance().getReference().child("Users");
        final String us=FirebaseAuth.getInstance().getCurrentUser().getUid();
        map=new HashMap<String, String>();


        Intent intent =getIntent();
        date=intent.getStringExtra("Date");
        time=intent.getStringExtra("Time");
        players=intent.getStringExtra("Players");
        cost=intent.getIntExtra("Cost",0);


        final Query userQuery = myref.orderByChild("username");
        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                String value=dataSnapshot.child("username").getValue().toString();
                String date = String.valueOf(dataSnapshot.getKey());
                Log.v("E/v","-----"+date);
                map.put(key,value);
                passname.setText(map.get(us));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        passdate.setText(date);
        passtime.setText(time);
        passcost.setText(""+cost);
        passplay.setText(players);
        total=String.valueOf(cost);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi(total,"kananvinayakam@oksbi","Vinayakam","Court booking at "+time);
            }
        });
    }



    //Payment method for selecting UPI App

    void payUsingUpi(String amount, String upiId,String upname,String note) {

       String currencyUnit="INR";

        Uri uri = Uri.parse("upi://pay?pa="+upiId+"&pn="+upname+"&tn="+note+
                "&am="+amount+"&cu="+currencyUnit);
        Log.v("E/v", "onClick: uri: "+uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if(null != intent.resolveActivity(getPackageManager())) {
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(ViewPass.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }



    //Checks the payment status and act accordingly

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
        //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

        if(data!=null) {
            Log.v("E/c", "onActivityResult: data: " + data.getStringExtra("response"));
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res.toLowerCase().contains(search.toLowerCase())) {
                progressDialog=new ProgressDialog(ViewPass.this);
                progressDialog.setMax(3500);
                progressDialog.setTitle("Confirming your slot....");
                progressDialog.setMessage("Please Wait....");
                progressDialog.show();
                //Stores in Firebase confirming Timeslot
                DatabaseReference db=FirebaseDatabase.getInstance().getReference();
                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                bookedUser=new BookedUser(passname.getText().toString(),players,passcost.getText().toString(),uid);
                db.child("Booked_History").child(date).child(time).child(uid).setValue(bookedUser);
                db.child("Users").child(uid).child("Bookings").child(date).child(time).child("No_of_players").setValue(players);
                db.child("Users").child(uid).child("Bookings").child(date).child(time).child("Amount").setValue(passcost.getText().toString());

                //Then,moves to confirmed page........

                startActivity(new Intent(ViewPass.this,Afterbooking.class));
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

           } else {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }


    //Alert Dialog box

    private void opendialog() {
        /*
        bookDialogue.show(getSupportFragmentManager(),"Book Dialogue");*/
        AlertDialog.Builder builder=new AlertDialog.Builder(ViewPass.this);
        builder.setTitle("Congratulations").setMessage("Booking confirmed").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }




    // Menu inflater and its functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }


    // On Clicking each items in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.signout)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        if(item.getItemId()==R.id.passes)
        {
            startActivity(new Intent(this,Afterbooking.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(ViewPass.this, Afterlogin.class);
        startActivity(it);
    }
}
