/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import com.google.firebase.database.PropertyName;

public class BasketItem {
    private String name;
    private String image;
    private String artistName;
    private float price;
    private int quantity;

    private int id;
    private float total;



    public BasketItem(String name, String artistName, String image, float price, int quantity, int id, float total) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.artistName = artistName;
        this.total = total;

    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BasketItem() {
        // Default constructor required for Firebase
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getArtistName() {
        return artistName;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }
}
