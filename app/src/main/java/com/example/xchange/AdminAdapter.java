package com.example.xchange;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    List<WholeBookings> wholeBookings;
    Context mctx;
    int resource;

    public AdminAdapter(Context mctx, List<WholeBookings> wholeBookings) {
        this.mctx = mctx;
        this.wholeBookings = wholeBookings;
    }

    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.bookpass, null);
        return new AdminAdapter.AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminAdapter.AdminViewHolder holder, int position) {
        //getting the product of the specified position
        WholeBookings wholeBookings1 = wholeBookings.get(position);

        //binding the data with the viewholder views
        holder.txt1.setText(wholeBookings1.getName(position));
        holder.txt2.setText(wholeBookings1.getDate(position));
        holder.txt3.setText(wholeBookings1.getTime(position));
        holder.txt4.setText(wholeBookings1.getPlayers(position));
        holder.txt5.setText(wholeBookings1.getAmt(position));

    }

    @Override
    public int getItemCount() {
        return wholeBookings.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, txt3, txt4,txt5;

        public AdminViewHolder(View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.adname);
            txt2 = itemView.findViewById(R.id.addate);
            txt3 = itemView.findViewById(R.id.adtime);
            txt4 = itemView.findViewById(R.id.adplayers);
            txt5 =  itemView.findViewById(R.id.adamt);
        }
    }
}
