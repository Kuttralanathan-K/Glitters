package com.example.xchange;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Afterlogin extends AppCompatActivity {

    EditText nop;
    TextView toast,date;
    Spinner spinner;
    Button proceed;
    String []timeslot={"Choose","5am to 6am","6am to 7am","7am to 8am","8am to 9am","9am to 10am","10am to 11am","11am to 12pm","12pm to 1pm","1pm to 2pm","2pm to 3pm","3pm to 4pm","4pm to 5pm","5pm to 6pm","6pm to 7pm","7pm to 8pm","8pm to 9pm","9pm to 10pm"};
    ArrayAdapter<String> adapter;
    String booktime,curdate;
    Calendar calendar,c;
    DatePickerDialog dp;
    DatabaseReference myref= FirebaseDatabase.getInstance().getReference();
    private ProgressDialog progressDialog;
    int cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        date=findViewById(R.id.date);
        nop=findViewById(R.id.nop);
        toast=findViewById(R.id.toast);
        proceed=findViewById(R.id.proceed);
        spinner=findViewById(R.id.spinner);



        //Set current date
        calendar=new GregorianCalendar();
        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        curdate=df.format(calendar.getTime());
        date.setText(curdate);



        adapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, timeslot);
        spinner.setAdapter(adapter);


        //Set date picker dialogue box to choose date with minimum date as current date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int day=Calendar.getInstance().get(c.DAY_OF_MONTH);
                int month=Calendar.getInstance().get(c.MONTH);
                int year=Calendar.getInstance().get(c.YEAR);
                dp=new DatePickerDialog(Afterlogin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayofmonth);
                        c.set(Calendar.YEAR,year);
                        if(month < 10 && dayofmonth < 10){

                            curdate="0"+dayofmonth+"-"+"0"+(month+1)+"-"+year;
                        }
                        else if(dayofmonth < 10){

                            curdate="0"+dayofmonth+"-"+(month+1)+"-"+year;
                        }
                        else if(month<10){
                            curdate=dayofmonth+"-"+"0"+(month+1)+"-"+year;
                        }

                        date.setText(curdate);
                    }
                },year,month,day);
                dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dp.show();
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 1:
                        booktime="5am to 6am";
                        break;
                    case 2:
                        booktime="6am to 7am";
                        break;
                    case 3:
                        booktime="7am to 8am";
                        break;
                    case 4:
                        booktime="8am to 9am";
                        break;
                    case 5:
                        booktime="9am to 10am";
                        break;
                    case 6:
                        booktime="10am to 11am";
                        break;
                    case 7:
                        booktime="11am to 12pm";
                        break;
                    case 8:
                        booktime="12pm to 1pm";
                        break;
                    case 9:
                        booktime="1pm to 2pm";
                        break;
                    case 10:
                        booktime="2pm to 3pm";
                        break;
                    case 11:
                        booktime="3pm to 4pm";
                        break;
                    case 12:
                        booktime="4pm to 5pm";
                        break;
                    case 13:
                        booktime="5pm to 6pm";
                        break;
                    case 14:
                        booktime="6pm to 7pm";
                        break;
                    case 15:
                        booktime="7pm to 8pm";
                        break;
                    case 16:
                        booktime="8pm to 9pm";
                        break;
                    case 17:
                        booktime="9pm to 10pm";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(date.getText().toString())||TextUtils.isEmpty(booktime)||TextUtils.isEmpty(nop.getText().toString()))
                {
                    Toast.makeText(Afterlogin.this,"Enter all details",Toast.LENGTH_LONG).show();
                }
                else {


                    //Requires code for timeslot condition*********
                    myref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("Booked_History").child(curdate).child(booktime).getValue()!=null)
                            {
                                toast.setText("*Sorry!!!Selected time is booked.......");
                            }
                            else{
                                cost=Integer.parseInt(nop.getText().toString());
                                if(cost>1){
                                    progressDialog=new ProgressDialog(Afterlogin.this);
                                    progressDialog.setMax(3500);
                                    progressDialog.setTitle("Loading....");
                                    progressDialog.show();
                                    cost=cost*100;
                                    Intent intent = new Intent(Afterlogin.this, ViewPass.class);
                                    intent.putExtra("Date", curdate);
                                    intent.putExtra("Time", booktime);
                                    intent.putExtra("Cost", cost);
                                    intent.putExtra("Players",nop.getText().toString());
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(Afterlogin.this,"Please select more than 1 player....",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });



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
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
