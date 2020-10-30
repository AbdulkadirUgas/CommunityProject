package com.door2door.delivery.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.door2door.delivery.CartActivity;
import com.door2door.delivery.R;
import com.door2door.delivery.Utilities.DatabaseHelper;
import com.door2door.delivery.Utilities.Utility;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailFragment extends DialogFragment implements View.OnClickListener {
    String ID,name,category,price,desc,filter,img_url;
    TextView tv_product_name, tv_product_category,tv_product_price,tv_product_desc,tv_total_price,tv_product_name_title,tv_add_to_cart,tv_quantity_anim;
    RelativeLayout rl_quantity_add,rl_quantity_minus,rl_top_bar;
    ImageView product_image,backImage;
    TextView tv_quantity;
    int quantity = 1;
    TextView tv_cartCount;
    FrameLayout cartCountLayout;
    double item_price,total_price,discounts=0.0;
    ImageView img_back,img_add_to_cart_icon;
    ConstraintLayout background_layout;
    RelativeLayout rl_add_to_cart;
    TranslateAnimation animation;
    DatabaseHelper db;
    Activity mActivity;
    FrameLayout cart_layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail,container,false);
        mActivity = getActivity();
        db = new DatabaseHelper(mActivity);
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
        background_layout       = view.findViewById(R.id.hole);
        rl_add_to_cart          = view.findViewById(R.id.rl_add_to_cart);
        tv_add_to_cart          = view.findViewById(R.id.tv_add_to_cart);
        tv_quantity_anim        = view.findViewById(R.id.tv_quantity_anim);
        img_add_to_cart_icon    = view.findViewById(R.id.img_add_to_cart_icon);
        tv_cartCount            = view.findViewById(R.id.tv_cart_count);
        cartCountLayout         = view.findViewById(R.id.cart_count_layout);
        cart_layout             = view.findViewById(R.id.cart_layout);
        product_image           = view.findViewById(R.id.product_image);
        backImage               = view.findViewById(R.id.backImage);
        rl_top_bar              = view.findViewById(R.id.rl_top_bar);

        rl_add_to_cart.setOnClickListener(this);
        tv_add_to_cart.setOnClickListener(this);
        img_add_to_cart_icon.setOnClickListener(this);
        cart_layout.setOnClickListener(this);


        background_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
            ID = bundle.getString("productID");
            name = bundle.getString("productName");
            category = bundle.getString("productCategory");
            price = bundle.getString("productPrice");
            desc  = bundle.getString("productDesc");
            filter  = bundle.getString("hasBackground");
            img_url  = bundle.getString("productImage");

            tv_product_category.setText(category);
            tv_product_name.setText(name);
            tv_product_name_title.setText(name);
            tv_product_price.setText("$"+price);
            tv_product_desc.setText(desc);
            tv_total_price.setText("$"+price);


            Picasso.with(mActivity).load(img_url).into(product_image);
            item_price = Double.parseDouble(price);
            total_price = item_price;
        }

        if (filter.equals("yes")){
////            background_layout.setBackgroundResource(R.color.white);
////            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
////            StrictMode.setThreadPolicy(policy);
////            Bitmap myImage = getBitmapFromURL(img_url);
////            //BitmapDrawable(obj) convert Bitmap object into drawable object.
////            Drawable dr = new BitmapDrawable(myImage);
////            background_layout.setBackgroundDrawable(dr);
            Picasso.with(mActivity).load(img_url).into(backImage);
            backImage.setVisibility(View.VISIBLE);
            rl_top_bar.setAlpha((float) 1);
        }
        // if this item is on cart already change view to BuyNow
        if (Utility.isOnCart(ID,db)){
            tv_add_to_cart.setText("BUY NOW");
            rl_add_to_cart.setBackgroundTintList(ContextCompat.getColorStateList(mActivity, R.color.color2));
        }

        updateBadge();
        return view;
    }
    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateBadge();
    }

    private void calculateTotal(){
        total_price = (item_price * quantity)-discounts;
        tv_quantity.setText(""+quantity);
        tv_total_price.setText("$"+total_price);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_add_to_cart:
                addToCart(view,"layout");
                break;
            case R.id.img_add_to_cart:
                addToCart(view,"image");
                break;
            case R.id.tv_add_to_cart:
                addToCart(view,"textview");
                break;
            case R.id.cart_layout:
                startActivity(new Intent(mActivity, CartActivity.class));
        }
    }

    private void updateBadge(){
        Utility.setCartCountToolbar(mActivity,tv_cartCount,cartCountLayout);
    }
    private void addToCart(View view,String filter) {
        Log.e("AddToCart","ID:"+ID+" name:"+name+" totalPrice:"+total_price+" price:"+price+" discount:0 quantity:"+quantity);
        if(db.insertCartData(ID,name,String.valueOf(total_price),price,"0",String.valueOf(quantity),img_url,"com",desc))
        {
            Toast.makeText(mActivity,"Database Inserted",Toast.LENGTH_LONG).show();
            Utility.countUpdate(db,mActivity);
            updateBadge();
        }
        else{

        }

        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
                int x = locations[0];
                int y = locations[1];
                if (filter.equals("image")){
                    y = y - 14-25;
                }else if (filter.equals("layout")){
                    y = y-25;
                }else if (filter.equals("textview")){
                    y = y-16-25;
                }
        Log.e("TAG","Inside animation X:"+x+" Y:"+y);
        TranslateAnimation animation = new TranslateAnimation(0,0,0,-(y-50));
        animation.setDuration(5000);
        animation.setRepeatCount(0);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_quantity_anim.startAnimation(animation);
//        img_add_to_cart_icon.setAnimation(animation);
    }


}