package com.door2door.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.door2door.delivery.AdapterControllers.Ad_Cart;
import com.door2door.delivery.AdapterControllers.ProductDataObjects;
import com.door2door.delivery.Utilities.DatabaseHelper;
import com.door2door.delivery.Utilities.SharedPref;
import com.door2door.delivery.Utilities.Utility;
import com.door2door.delivery.fragment.DialogPaymentSuccess;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    double delivery = 0.5;
    DatabaseHelper myDb;
    Activity mActivity;
    RecyclerView carList;
    Ad_Cart adapter;
    Double payment=0.0;
    ImageView img_back;
    ArrayList<ProductDataObjects> cartInfo = new ArrayList<>();
    TextView tv_total_payment,tv_total,tv_sub_total;
    RelativeLayout rl_check_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mActivity = this;
        myDb = new DatabaseHelper(this);
        findView();

        getCartData();
    }


    private void findView() {
        carList = findViewById(R.id.rc_cart);
        rl_check_out = findViewById(R.id.rl_check_out);
        tv_total_payment = findViewById(R.id.tv_total_price);
        tv_total = findViewById(R.id.tv_total);
        tv_sub_total = findViewById(R.id.tv_sub_total);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteCartAll(mActivity);
                SharedPref.setCartCount(0,mActivity);
                showDialogPaymentSuccess();
            }
        });
    }

    protected void getCartData(){
        Cursor cursor =myDb.getCartData();
        ProductDataObjects items;
        if (cursor.getCount() == 0){


            return;
        }
        while (cursor.moveToNext()){
            items = new ProductDataObjects();
            String id=cursor.getString(1);
            String item_name=cursor.getString(2);
            String item_price=cursor.getString(3);
            String unit_price=cursor.getString(4);
            String item_qty=cursor.getString(6);
            String item_image_url=cursor.getString(7);
            String item_desc=cursor.getString(9);
            payment = (payment + Double.valueOf(item_price));
            payment = Math.round(payment*100)/100.0;

            Log.e("Cart","ID:"+id+" name:"+item_name+" price:"+item_price);
            items.setProduct_ID(id);
            items.setProduct_name(item_name);
            items.setProduct_price(item_price);
            items.setProduct_des(item_desc);


            cartInfo.add(items);
        }
        tv_sub_total.setText("$"+payment);
        double totalAmount = payment + delivery;
        tv_total_payment.setText("$"+totalAmount);
        tv_total.setText("$"+totalAmount);
        String filter="cart";
        adapter = new Ad_Cart(mActivity,cartInfo);



        adapter.notifyDataSetChanged();
        carList.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        carList.setAdapter(adapter);
        myDb.close();

    }

    private void showDialogPaymentSuccess() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        DialogPaymentSuccess newFragment = new DialogPaymentSuccess();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }
}