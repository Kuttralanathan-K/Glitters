package com.example.xchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Afterbooking extends AppCompatActivity {

    DatabaseReference db;
    RecyclerView recyclerView;
    public static String name,date,time,players;

     List<UserBookings> bookingslist=new ArrayList<>();

     ArrayList<String> setname=new ArrayList<String>();
     ArrayList<String> setdate=new ArrayList<String>();
     ArrayList<String> settime=new ArrayList<String>();
     ArrayList<String> setplayers=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterbooking);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db= FirebaseDatabase.getInstance().getReference().child("Users");
        db.keepSynced(true);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.v("E/root","Full Database of USERS----"+dataSnapshot.getValue());

                //Inside uid -> that is all fields
                for(DataSnapshot dst :dataSnapshot.getChildren() ){

                    if(dst.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        name=String.valueOf(dst.child("username").getValue());
                        Log.v("E/parent","---"+dst.child("username").getValue()+" Uid-----"+dst.getKey());

                        //Seperates each child node (Eg: Booking,Username)
                        for(DataSnapshot ds1 : dst.getChildren()) {
                            Log.v("E/child", "Delete----" + ds1.getValue());

                            //Next Child node...... Gives date
                            for(DataSnapshot ds2 : ds1.getChildren()){
                                date=String.valueOf(ds2.getKey());
                                Log.v("E/child1", "Date----" + ds2.getKey());

                                //Further one more child node down.......... Gives time and players and amount
                                for(DataSnapshot ds3 : ds2.getChildren()){
                                    time=String.valueOf(ds3.getKey());
                                    players=String.valueOf(ds3.child("No_of_players").getValue());
                                    Log.v("E/child2", "time----" +ds3.getKey()+"--------"+ds3.child("No_of_players").getValue());
                                    setname.add(name);
                                    setdate.add(date);
                                    settime.add(time);
                                    setplayers.add(players);
                                    bookingslist.add(new UserBookings(setname,setdate,settime,setplayers));
                                }
                            }
                        }
                    }


                    ListAdapter adapter=new ListAdapter(Afterbooking.this,bookingslist);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent it = new Intent(Afterbooking.this, Afterlogin.class);
        startActivity(it);
    }

}
