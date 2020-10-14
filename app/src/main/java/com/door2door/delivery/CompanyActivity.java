package com.door2door.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.door2door.delivery.AdapterControllers.Ad_companyProducts;
import com.door2door.delivery.AdapterControllers.ProductDataObjects;
import com.door2door.fragment.DetailFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.door2door.delivery.MainActivity.db;

public class CompanyActivity extends AppCompatActivity implements Ad_companyProducts.ProductListener {
    String companyID ;
    ArrayList<ProductDataObjects> products = new ArrayList<>();
    RecyclerView rc_products;
    Activity mActivity;
    //Products Adapter
    Ad_companyProducts productsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        mActivity = this;
        Intent intent = getIntent();
        companyID = ((intent.hasExtra("compID")) ? intent.getStringExtra("compID") : "");
        findView();
    }

    private void findView() {
        rc_products = findViewById(R.id.rc_products);
        rc_products.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL,false));
        productsAdapter = new Ad_companyProducts(products,mActivity);
        rc_products.setAdapter(productsAdapter);
        productsAdapter.setOnClickListener(this);

        getProducts();
    }

    private void getProducts() {
        db.collection("products")
                .whereEqualTo("companyID",companyID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ProductDataObjects info;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                info = new ProductDataObjects();
                                String price = (String) document.get("price");
                                String rate = (String) document.get("rate");
                                String name = (String) document.get("name");
                                String category = (String) document.get("category");
                                String desc = (String) document.get("desc");

                                info.setProduct_name(name);
                                info.setProduct_category(category);
                                info.setProduct_price(price);
                                info.setProduct_rate(rate);
                                info.setProduct_des(desc);

                                products.add(info);
                            }
                            productsAdapter.notifyDataSetChanged();
                        }else Log.e("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onProductClickListener(View view, int position) {
        ProductDataObjects productInfo = products.get(position);
        gotoItemDetails(productInfo.getProduct_name(),productInfo.getProduct_category(),productInfo.getProduct_des(),productInfo.getProduct_price());
    }

    private void gotoItemDetails(String name,String category,String desc,String price) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        DetailFragment newFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("productName",name);
        bundle.putString("productCategory",category);
        bundle.putString("productPrice",price);
        bundle.putString("productDesc",desc);
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_to_top,R.anim.anim_top_bottom);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

}