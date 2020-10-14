package com.door2door.delivery.AdapterControllers;

public class ProductDataObjects {
    String product_name,product_category,product_rate,product_price,product_des;
    boolean isFav;

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_des(String product_des) {
        this.product_des = product_des;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public void setProduct_rate(String product_rate) {
        this.product_rate = product_rate;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_des() {
        return product_des;
    }

    public String getProduct_rate() {
        return product_rate;
    }

    public boolean isFav() {
        return isFav;
    }
}
