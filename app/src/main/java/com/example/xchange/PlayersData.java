package com.example.xchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlayersData extends AppCompatActivity {

    RecyclerView recyclerView2;
    DatabaseReference db;
    List<AdminData> list=new ArrayList<>();
    public static String name,mail,ph,uid;
    ArrayList<String> dname=new ArrayList<String>();
    ArrayList<String> dmail=new ArrayList<String>();
    ArrayList<String> dph=new ArrayList<String>();
    ArrayList<String> duid=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_data);

        recyclerView2=findViewById(R.id.admin);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        db= FirebaseDatabase.getInstance().getReference().child("Users");
        db.keepSynced(true);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.v("E/root","Full Database of USERS----"+dataSnapshot.getValue());

                //Inside uid -> that is all fields
                for(DataSnapshot dst :dataSnapshot.getChildren() ){
                    uid=String.valueOf(dst.getKey());
                    name=String.valueOf(dst.child("username").getValue());
                    mail=String.valueOf(dst.child("email").getValue());
                    ph=String.valueOf(dst.child("mobile").getValue());
                    dname.add(name);
                    dmail.add(mail);
                    dph.add(ph);
                    duid.add(uid);

                    list.add(new AdminData(dname,dmail,dph));


                    DataAdapter adapter=new DataAdapter(list,PlayersData.this);
                    recyclerView2.setAdapter(adapter);
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
        Intent it = new Intent(PlayersData.this, AdminList.class);
        startActivity(it);
    }
}
