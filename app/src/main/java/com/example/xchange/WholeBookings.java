package com.example.xchange;

import java.util.ArrayList;

public class WholeBookings {
    ArrayList<String> name,date,time,players,amt;

    public WholeBookings(ArrayList<String> name, ArrayList<String> date, ArrayList<String> time, ArrayList<String> players, ArrayList<String> amt) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.players = players;
        this.amt = amt;
    }

    public WholeBookings() {
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
    public String getAmt(int position) {
        return amt.get(position);
    }
}
