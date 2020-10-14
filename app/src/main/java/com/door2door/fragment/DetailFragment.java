package com.door2door.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.door2door.delivery.R;

public class DetailFragment extends DialogFragment {
    String name,category,price,desc;
    TextView tv_product_name, tv_product_category,tv_product_price,tv_product_desc,tv_total_price,tv_product_name_title;
    RelativeLayout rl_quantity_add,rl_quantity_minus;
    TextView tv_quantity;
    int quantity = 1;
    double item_price,total_price,discounts=0.0;
    ImageView img_back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail,container,false);

        tv_product_name         = view.findViewById(R.id.tv_product_name);
        tv_product_name_title   = view.findViewById(R.id.tv_product_name_title);
        tv_product_price        = view.findViewById(R.id.tv_product_price);
        tv_product_desc         = view.findViewById(R.id.tv_product_desc);
        tv_product_category     = view.findViewById(R.id.tv_product_category);
        tv_total_price          = view.findViewById(R.id.tv_total_price);
        tv_quantity             = view.findViewById(R.id.tv_quantity);
        rl_quantity_add         = view.findViewById(R.id.rl_quantity_add);
        rl_quantity_minus       = view.findViewById(R.id.rl_quantity_minus);
        img_back                = view.findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //decrease quantity
        rl_quantity_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1){
                    quantity--;
                    calculateTotal();
                }
            }
        });

        //increase quantity
        rl_quantity_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                calculateTotal();
            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("productName");
            category = bundle.getString("productCategory");
            price = bundle.getString("productPrice");
            desc  = bundle.getString("productDesc");

            tv_product_category.setText(category);
            tv_product_name.setText(name);
            tv_product_name_title.setText(name);
            tv_product_price.setText("$"+price);
            tv_product_desc.setText(desc);
            tv_total_price.setText("$"+price);

            item_price = Double.parseDouble(price);
        }


        return view;
    }

    private void calculateTotal(){
        total_price = (item_price * quantity)-discounts;
        tv_quantity.setText(""+quantity);
        tv_total_price.setText("$"+total_price);
    }
}