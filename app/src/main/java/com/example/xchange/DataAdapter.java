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


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    List<AdminData> list;
    Context context;

    public DataAdapter(List<AdminData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.datalist, null);
        return new DataAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.DataViewHolder holder, int position) {
        //getting the product of the specified position
        AdminData adminData = list.get(position);

        //binding the data with the viewholder views
        holder.txt1.setText(adminData.getName(position));
        holder.txt2.setText(adminData.getMail(position));
        holder.txt3.setText(adminData.getPh(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, txt3;

        public DataViewHolder(View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.dataname);
            txt2 = itemView.findViewById(R.id.datamail);
            txt3 = itemView.findViewById(R.id.dataph);

        }
    }
}
