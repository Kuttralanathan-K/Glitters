package com.example.xchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminList extends AppCompatActivity {

    DatabaseReference db;
    RecyclerView recyclerView1;
    public static String name,date,time,players,amt;

    List<WholeBookings> wholeBookings=new ArrayList<>();

    ArrayList<String> setname1=new ArrayList<String>();
    ArrayList<String> setdate1=new ArrayList<String>();
    ArrayList<String> settime1=new ArrayList<String>();
    ArrayList<String> setplayers1=new ArrayList<String>();
    ArrayList<String> setamt1=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        recyclerView1=findViewById(R.id.adminlist);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));



        db= FirebaseDatabase.getInstance().getReference().child("Booked_History");
        db.keepSynced(true);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.v("E/root","Full Database of Booked_History----"+dataSnapshot.getValue());

                //Inside date -> that is all fields
                for(DataSnapshot dst :dataSnapshot.getChildren() ){
                    date=String.valueOf(dst.getKey());//Gives date
                    Log.v("E/parent","Date---"+dst.getKey());

                    //Seperates each child node --> Time
                    for(DataSnapshot ds1 : dst.getChildren()) {
                        time=String.valueOf(ds1.getKey());//Gives Time
                        Log.v("E/child", "Time----" + ds1.getKey());

                        //Next Child node...... ---> Uid
                        for(DataSnapshot ds2 : ds1.getChildren()){
                            Log.v("E/child1", "Uid----" + ds2.getKey());
                            name=String.valueOf(ds2.child("Name").getValue());
                            players=String.valueOf(ds2.child("No_of_players").getValue());
                            amt=String.valueOf(ds2.child("Amt_paid").getValue());
                            Log.v("E/child2", "Name----" +ds2.child("Name").getValue()+"Players--"+ds2.child("No_of_players").getValue());
                            setname1.add(name);
                            setdate1.add(date);
                            settime1.add(time);
                            setplayers1.add(players);
                            setamt1.add(amt);
                            wholeBookings.add(new WholeBookings(setname1,setdate1,settime1,setplayers1,setamt1));
                        }
                    }


                    AdminAdapter adapter=new AdminAdapter(AdminList.this,wholeBookings);
                    recyclerView1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Menu inflater and its functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
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
            startActivity(new Intent(this,PlayersData.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
