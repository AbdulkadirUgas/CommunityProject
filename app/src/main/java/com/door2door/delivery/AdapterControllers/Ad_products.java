package com.door2door.delivery.AdapterControllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.door2door.delivery.R;

import java.util.ArrayList;

public class Ad_products extends RecyclerView.Adapter<Ad_products.ViewHolder> {
    ArrayList<ProductDataObjects> products;
    Context context;

    public Ad_products(ArrayList<ProductDataObjects> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_favourite,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataObjects current = products.get(position);
        holder.tv_productName.setText(current.getProduct_name());
        holder.tv_productCategory.setText(current.getProduct_category());
        holder.tv_productPrice.setText("$"+current.getProduct_price());
        holder.ratingBar.setRating(Float.valueOf(current.getProduct_rate()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productName,tv_productCategory,tv_productPrice;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            tv_productCategory = itemView.findViewById(R.id.tv_fav_product_category);
            tv_productName = itemView.findViewById(R.id.tv_fav_product_name);
            tv_productPrice = itemView.findViewById(R.id.tv_fav_product_price);
        }
    }
}
