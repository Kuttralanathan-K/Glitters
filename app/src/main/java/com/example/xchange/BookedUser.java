package com.example.xchange;

public class BookedUser {

    public String Name;
    public String No_of_players;
    public String Amt_paid;
    public String Userid;


    public BookedUser(String name, String no_of_players, String amt_paid, String userid) {
        this.Name = name;
        this.No_of_players = no_of_players;
        this.Amt_paid = amt_paid;
        this.Userid = userid;
    }

    public BookedUser() {
    }
}

