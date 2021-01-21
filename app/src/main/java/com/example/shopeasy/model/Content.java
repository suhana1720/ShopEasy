package com.example.shopeasy.model;

public class Content {
    private int id; // to track down each item
    private String item;
    private String itemQuantity;
    private int itemPrice;
    private String itemBrand;
    private String dateItemAdded;

    public Content() {
    }

    public Content(int id, String Item , String itemQuantity, int itemPrice , String itemBrand, String dateItemAdded) {
        this.id=id;
        this.item = Item;
        this.itemQuantity=itemQuantity;
        this.itemBrand =itemBrand;
        this.itemPrice =itemPrice;
        this.dateItemAdded=dateItemAdded;
    }

    public Content( String Item , String itemQuantity, int itemPrice , String itemBrand, String dateItemAdded) {
        this.item = Item;
        this.itemQuantity=itemQuantity;
        this.itemBrand =itemBrand;
        this.itemPrice =itemPrice;
        this.dateItemAdded=dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
