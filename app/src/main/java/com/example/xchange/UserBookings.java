package com.example.xchange;

import java.util.ArrayList;

public class UserBookings {
    ArrayList<String> name,date,time,players;


    public UserBookings(ArrayList<String> name, ArrayList<String> date, ArrayList<String> time, ArrayList<String> players) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.players = players;
    }

    public UserBookings() {
    }

    public String getName(int position) {
        return name.get(position);
    }

    public String getDate(int position) {
        return date.get(position);
    }

    public String getTime(int position) {
        return time.get(position);
    }

    public String getPlayers(int position) {
        return players.get(position);
    }


}
