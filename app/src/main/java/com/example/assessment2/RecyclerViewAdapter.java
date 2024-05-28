/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/


package com.example.assessment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder> {

    private final HomeRecycler homeRecyclerInterface;

    Context context;
    ArrayList<Products> products;
    public RecyclerViewAdapter(Context context, ArrayList<Products> products, HomeRecycler homeRecyclerInterface){
        this.context = context;
        this.products = products;
        this.homeRecyclerInterface = homeRecyclerInterface;
    }



    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the layout and giving looks to each of the rows

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent,false);
        return new RecyclerViewAdapter.MyViewHolder(view, homeRecyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //Assigning Values to the views created in the my_recycler_view_row layout file
        //based on the position of the recycler view
        holder.name.setText(products.get(position).getName());
        holder.artistName.setText(products.get(position).getArtistName());
        holder.price.setText("£"+products.get(position).getPrice());
        holder.briefDescr.setText(String.valueOf(products.get(position).getBriefDescr()));
        Picasso.get().load(products.get(position).getImage()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        // How many items are in total
        return products.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // Grabs the view from the my_recycler_view_row layout file

        ImageView imageView;
        TextView name, artistName, price, briefDescr;
        EditText quantityBasket;

        public MyViewHolder(@NonNull View itemView, HomeRecycler homeRecyclerInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageProdHP);
            name = itemView.findViewById(R.id.nameProd);
            artistName = itemView.findViewById(R.id.artistName);
            price = itemView.findViewById(R.id.priceProd);
            briefDescr = itemView.findViewById(R.id.briefDescr);
            quantityBasket = itemView.findViewById(R.id.quantityBasket);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(homeRecyclerInterface != null){
                        int pos = getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            homeRecyclerInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }



}
