package com.example.xchange;

import java.util.ArrayList;

public class AdminData {
    ArrayList<String> name,email,ph;

    public AdminData(ArrayList<String> name, ArrayList<String> email, ArrayList<String> ph) {
        this.name = name;
        this.email = email;
        this.ph = ph;
    }

    public AdminData() {
    }

    public String getName(int position) {
        return name.get(position);
    }

    public String getMail(int position) {
        return email.get(position);
    }

    public String getPh(int position) {
        return ph.get(position);
    }

}
