package com.codestrela.product.data;

public class Commodity {
    String name;
    String price;

    public Commodity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Commodity(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
