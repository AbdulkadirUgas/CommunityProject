package com.door2door.delivery.AdapterControllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.door2door.delivery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ad_products extends RecyclerView.Adapter<Ad_products.ViewHolder> {
    ArrayList<ProductDataObjects> products;
    Context context;
    ProductListener mListener;

    public interface ProductListener{
        void OnProductClickListener(View view,int position);
    }
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
        Picasso.with(context).load(current.image_url).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productName,tv_productCategory,tv_productPrice;
        RatingBar ratingBar;
        ImageView img_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_fav_product);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            tv_productCategory = itemView.findViewById(R.id.tv_fav_product_category);
            tv_productName = itemView.findViewById(R.id.tv_fav_product_name);
            tv_productPrice = itemView.findViewById(R.id.tv_fav_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnProductClickListener(view,getAdapterPosition());
                }
            });
        }
    }
    public void setClickListener(ProductListener mListener){
        this.mListener = mListener;
    }
}
