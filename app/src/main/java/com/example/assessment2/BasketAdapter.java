/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/


package com.example.assessment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private List<BasketItem> basketItems;
    private OnDeleteItemClickListener onDeleteItemClickListener;
    private OnItemClickListener onItemClickListener;

    public BasketAdapter(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_basket, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        BasketItem basketItem = basketItems.get(position);
        holder.bind(basketItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click, e.g., notify the listener
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(Integer.toString(basketItems.get(position).getId()));
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteItemClickListener != null) {
                    onDeleteItemClickListener.onDeleteItemClick(position);
                }
            }
        });
    }

    // Interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(String productId); // You can use any type that identifies your item uniquely
    }


    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    // Interface to handle delete button clicks
    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    // Method to set the listener
    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        onDeleteItemClickListener = listener;
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemArtist, itemPrice;
        private EditText itemQuantity;
        private ImageView itemImage;
        private ImageButton deleteBtn;




        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.nameProd);
            itemArtist = itemView.findViewById(R.id.artistName);
            itemPrice = itemView.findViewById(R.id.priceProd);
            itemQuantity = itemView.findViewById(R.id.quantityBasket);
            itemImage = itemView.findViewById(R.id.imageProdHP);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);


        }

        public void bind(BasketItem basketItem) {
            itemName.setText(basketItem.getName());
            itemArtist.setText(basketItem.getArtistName());
            itemPrice.setText("Price: £" + basketItem.getPrice());
            itemQuantity.setText("Quantity: " + basketItem.getQuantity());
            Picasso.get().load(basketItem.getImage()).into(itemImage);

        }
    }
}
