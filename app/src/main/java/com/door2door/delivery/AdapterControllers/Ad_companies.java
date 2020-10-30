package com.door2door.delivery.AdapterControllers;

import android.content.Context;
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

public class Ad_companies extends RecyclerView.Adapter<Ad_companies.ViewHolder> {
    ArrayList<CompanyDataObjects> companiesInfo;
    Context context;
    CompanyListener mListener;

    public interface CompanyListener{
        void OnClickListener(View view,int position);
    }

    public Ad_companies(ArrayList<CompanyDataObjects> companiesInfo, Context context) {
        this.companiesInfo = companiesInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_popular_companies,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyDataObjects current = companiesInfo.get(position);
        holder.tv_company_nam.setText(current.getComp_name());
        holder.tv_company_category.setText(current.getComp_category());
        holder.tv_company_rate.setText(current.getComp_rate());
        Picasso.with(context).load(current.getImage_url()).into(holder.img_com);
    }

    @Override
    public int getItemCount() {
        return companiesInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_company_nam,tv_company_category,tv_company_rate;
        ImageView img_com;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_com = itemView.findViewById(R.id.img_com);
            tv_company_nam = itemView.findViewById(R.id.tv_company_name);
            tv_company_category = itemView.findViewById(R.id.tv_company_category);
            tv_company_rate = itemView.findViewById(R.id.tv_company_rate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnClickListener(view,getAdapterPosition());
                }
            });
        }
    }

    public void setClickListener(CompanyListener mListener){
        this.mListener = mListener;
    }
}
