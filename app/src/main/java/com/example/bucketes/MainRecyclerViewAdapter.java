package com.example.bucketes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    Context context;
    String[] items, descriptions;


    public MainRecyclerViewAdapter(Context context, String[] items, String[] descriptions) {
        this.context = context;
        this.items = items;
        this.descriptions = descriptions;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(items[position]);

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imgDot, imgTrashCan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtItemTitle);
            imgDot = itemView.findViewById(R.id.imgDot);
            imgTrashCan = itemView.findViewById(R.id.imgTrashCan);
        }
    }
}
