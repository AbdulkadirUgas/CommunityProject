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

public class Ad_companyProducts extends RecyclerView.Adapter<Ad_companyProducts.ViewHolder> {
    ArrayList<ProductDataObjects> productsInfo;
    Context context;
    ProductListener onClickListener;
    public interface ProductListener{
        void onProductClickListener(View view,int position);
    }
    public Ad_companyProducts(ArrayList<ProductDataObjects> productsInfo, Context context) {
        this.productsInfo = productsInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_company_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataObjects current = productsInfo.get(position);
        holder.tv_productName.setText(current.getProduct_name());
        holder.tv_productDesc.setText(current.getProduct_des());
        Picasso.with(context).load(current.image_url).into(holder.img_product);
        holder.tv_productPrice.setText("$"+current.getProduct_price());
    }

    @Override
    public int getItemCount() {
        return productsInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product,img_add_to_cart;
        TextView tv_productName,tv_productPrice,tv_productDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productPrice = itemView.findViewById(R.id.tv_product_price);
            tv_productDesc = itemView.findViewById(R.id.tv_product_desc);
            tv_productName = itemView.findViewById(R.id.tv_product_name);
            img_product = itemView.findViewById(R.id.img_product_icon);
            img_add_to_cart = itemView.findViewById(R.id.img_add_to_cart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onProductClickListener(view,getAdapterPosition());
                }
            });
        }
    }

    public void setOnClickListener(ProductListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
