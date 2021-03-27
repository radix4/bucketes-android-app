package com.example.bucketes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    Context context;
    private List<ItemModel> items;

    /** Constructor */
    public MainRecyclerViewAdapter(Context context, List<ItemModel> items) {
        this.context = context;
        this.items = items;
    }


    /** Initialize a row of of recycler view. */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_row, parent, false);
        return new MyViewHolder(view);
    }

    /** Set the title name. */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
    }

    /** This method displays the number of items in the recycler view. */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**  Initialize title, imgDot, imgTrashCan by their IDs. */
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
