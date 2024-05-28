package com.example.assessment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<BasketItem> basketItems;

    public BasketRecyclerViewAdapter(Context context, ArrayList<BasketItem> basketItems) {
        this.context = context;
        this.basketItems = basketItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout and giving looks to each of the rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_basket, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Assigning values to the views created in the recycler_view_row_basket layout file
        // based on the position of the recycler view
        BasketItem basketItem = basketItems.get(position);
        holder.name.setText(basketItem.getName());
        holder.artistName.setText(basketItem.getArtistName());
        holder.price.setText("£" + basketItem.getPrice());
        holder.quantity.setText(String.valueOf(basketItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        // How many items are in total
        return basketItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabs the view from the recycler_view_row_basket layout file
        ImageView imageView;
        TextView name, artistName, price, quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageProdHP);
            name = itemView.findViewById(R.id.nameProd);
            artistName = itemView.findViewById(R.id.artistName);
            price = itemView.findViewById(R.id.priceProd);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
