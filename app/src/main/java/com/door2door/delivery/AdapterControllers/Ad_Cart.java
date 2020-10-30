package com.door2door.delivery.AdapterControllers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.door2door.delivery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ad_Cart extends RecyclerView.Adapter<Ad_Cart.ViewHolder> {
    Context context;
    ArrayList<ProductDataObjects> cartInfo;

    public Ad_Cart(Context context, ArrayList<ProductDataObjects> cartInfo) {
        this.context = context;
        this.cartInfo = cartInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_cart_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataObjects current = cartInfo.get(position);

        holder.tv_item_name.setText(current.getProduct_name());
        holder.tv_item_price.setText("$"+current.getProduct_price());
        Picasso.with(context).load(current.getImage_url()).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return cartInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name,tv_item_price;
        ImageView img_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            img_product = itemView.findViewById(R.id.img_product);
        }
    }
}
