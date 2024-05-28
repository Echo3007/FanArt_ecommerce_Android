/*Student Name: Angela Pellillo
 * Student ID: 21499500
 * Module: Mobile Web Application Development
 * Module ID: CP5CS93E*/

package com.example.assessment2;

import com.google.firebase.database.PropertyName;

public class Products {
    private String name;
    private String image;
    private String descr;
    private float price;
    private int quantity;

    private int id;
    private String artistName;
    private String briefDescr;
    private String category;

    public Products() {
        // Default constructor required for Firebase
    }



    public Products(String name, String image, String descr, float price, int quantity, int id, String artistName, String briefDescr, String category) {
        this.name = name;
        this.image = image;
        this.descr = descr;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.artistName = artistName;
        this.briefDescr = briefDescr;
        this.category = category;

    }

    public String getCategory() {
        return category;
    }

    public String getBriefDescr() {
        return briefDescr;
    }



    public String getArtistName() {
        return artistName;
    }



    public int getQuantity() {
        return quantity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public float getPrice() {
        return price;
    }


}

