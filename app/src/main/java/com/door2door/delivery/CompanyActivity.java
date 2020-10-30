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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.door2door.delivery.AdapterControllers.Ad_companyProducts;
import com.door2door.delivery.AdapterControllers.ProductDataObjects;
import com.door2door.delivery.fragment.DetailFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.door2door.delivery.MainActivity.db;

public class CompanyActivity extends AppCompatActivity implements Ad_companyProducts.ProductListener {
    String companyID ;
    ArrayList<ProductDataObjects> products = new ArrayList<>();
    RecyclerView rc_products;
    Activity mActivity;
    TextView tv_company_name,tv_company_address,tv_company_category;
    RatingBar ratingBar;
    ImageView img_company_icon;
    //Products Adapter
    Ad_companyProducts productsAdapter;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        mActivity = this;
        intent = getIntent();
        companyID = ((intent.hasExtra("compID")) ? intent.getStringExtra("compID") : "");

        findView();
    }

    private void findView() {
        rc_products = findViewById(R.id.rc_products);
        tv_company_category = findViewById(R.id.tv_company_category);
        img_company_icon = findViewById(R.id.img_company_icon);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_company_address = findViewById(R.id.tv_company_address);
        ratingBar   = findViewById(R.id.ratingbar);
        rc_products.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL,false));
        productsAdapter = new Ad_companyProducts(products,mActivity);
        rc_products.setAdapter(productsAdapter);
        productsAdapter.setOnClickListener(this);

        if (intent.hasExtra("name")){
            tv_company_name.setText(intent.getStringExtra("name"));
            tv_company_category.setText(intent.getStringExtra("category"));
            tv_company_address.setText(intent.getStringExtra("address"));
            Picasso.with(mActivity).load(intent.getStringExtra("image")).into(img_company_icon);
            String sRate = intent.getStringExtra("rate");
             Float rate = Float.valueOf(sRate);
             ratingBar.setRating(rate);
        }

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
                                String image = (String) document.get("image");

                                info.setProduct_ID(document.getId());
                                info.setProduct_name(name);
                                info.setProduct_category(category);
                                info.setProduct_price(price);
                                info.setProduct_rate(rate);
                                info.setProduct_des(desc);
                                info.setImage_url(image);

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
        gotoItemDetails(productInfo.getProduct_ID(),productInfo.getProduct_name(),productInfo.getImage_url(),productInfo.getProduct_category(),productInfo.getProduct_des(),productInfo.getProduct_price());
    }

    public void gotoItemDetails(String ID, String name,String image, String category, String desc, String price) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment newFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("productImage",image);
        bundle.putString("productName",name);
        bundle.putString("productCategory",category);
        bundle.putString("productPrice",price);
        bundle.putString("productDesc",desc);
        bundle.putString("productID",ID);
        bundle.putString("hasBackground","no");
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_to_top,R.anim.anim_top_bottom);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

}