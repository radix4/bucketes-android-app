package com.example.bucketes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketes.models.Item;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

    Context context;
    private List<Item> items;
    private ItemClickListener mItemClickListener, TrashClickListener;

    /** Constructor */
    public MainRecyclerViewAdapter(Context context, List<Item> items) {
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

        // Click listener for trash can
        holder.imgTrashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TrashClickListener != null) {
                    TrashClickListener.onTrashClick(position, items.get(position).getId());
                }
            }
        });

        // Click listener for the item
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
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

    // Create item click listener
    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
        void onTrashClick(int position, String id);
    }

    // Create trash can click listener
    public void addTrashClickListener(ItemClickListener listener) {
        TrashClickListener = listener;
    }

}
