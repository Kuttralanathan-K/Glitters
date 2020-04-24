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


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>  {

    List<UserBookings> bookingslist;
    Context mctx;
    int resource;

    public ListAdapter(Context mctx, List<UserBookings> bookingslist) {
        this.mctx = mctx;
        this.bookingslist = bookingslist;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.rowlist, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        //getting the product of the specified position
        UserBookings userBookings = bookingslist.get(position);

        //binding the data with the viewholder views
        holder.txt1.setText(userBookings.getName(position));
        holder.txt2.setText(userBookings.getDate(position));
        holder.txt3.setText(userBookings.getTime(position));
        holder.txt4.setText(userBookings.getPlayers(position));

    }

    @Override
    public int getItemCount() {
        return bookingslist.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, txt3, txt4;
        ImageView imageView;

        public ListViewHolder(View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.usname);
            txt2 = itemView.findViewById(R.id.usdate);
            txt3 = itemView.findViewById(R.id.ustime);
            txt4 = itemView.findViewById(R.id.usplayers);
        }
    }
}
