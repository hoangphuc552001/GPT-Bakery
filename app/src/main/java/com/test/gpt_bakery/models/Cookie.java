package com.test.gpt_bakery.models;

import java.io.Serializable;
import java.util.Arrays;

public class Cookie implements Serializable {
    int id;
    private String name;
    private double price;
    private String description;
    private byte[] image;
    private int quantity;

    public Cookie(int id, String name, double price, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity=1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void increaseQuantity(){this.quantity++;}

    public void decreaseQuantity(){this.quantity--;}

    public int getQuantity(){return this.quantity;}

    public double getTotalPrice(){return  this.quantity*this.price;}

    @Override
    public String toString() {
        return "Cookie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
//        return name;
    }

}
