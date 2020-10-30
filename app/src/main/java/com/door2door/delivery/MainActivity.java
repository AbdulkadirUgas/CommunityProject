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
import android.widget.TextView;

import com.door2door.delivery.Utilities.Utility;
import com.door2door.delivery.AdapterControllers.Ad_companies;
import com.door2door.delivery.AdapterControllers.Ad_products;
import com.door2door.delivery.AdapterControllers.CompanyDataObjects;
import com.door2door.delivery.AdapterControllers.ProductDataObjects;
import com.door2door.delivery.fragment.DetailFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Ad_companies.CompanyListener, Ad_products.ProductListener {
    ArrayList<CompanyDataObjects> companiesInfo = new ArrayList<>();
    ArrayList<ProductDataObjects> products = new ArrayList<>();
    public static FirebaseFirestore db ;
    RecyclerView rc_companies,rc_products;
    Activity mActivity;
    TextView tv_cart;
    //Companies Adapter
    Ad_companies companiesAdapter;
    //Products Adapter
    Ad_products productsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        db = FirebaseFirestore.getInstance();
        Utility.HideKeyboard(mActivity);
        findView();

    }

    private void findView() {
        rc_companies = findViewById(R.id.rc_companies);
        tv_cart = findViewById(R.id.tv_cart);
        rc_products = findViewById(R.id.rc_products);
        rc_companies.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        rc_products.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        companiesAdapter = new Ad_companies(companiesInfo,mActivity);
        companiesAdapter.setClickListener(this);
        productsAdapter  = new Ad_products(products,mActivity);
        rc_products.setAdapter(productsAdapter);
        productsAdapter.setClickListener(this);
        rc_companies.setNestedScrollingEnabled(false);
        rc_companies.setAdapter(companiesAdapter);
        tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity,CartActivity.class));
            }
        });
        getCompanies();
        getProducts();
    }

    private void getCompanies() {
        db.collection("companies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            CompanyDataObjects info;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                info = new CompanyDataObjects();
                                String ID = document.getId();
                                String address = (String) document.get("address");
                                String rate = (String) document.get("rate");
                                String contactNo = (String) document.get("contact_no");
                                String name = (String) document.get("name");
                                String category = (String) document.get("category");
                                String image = (String) document.get("image");

                                info.setCompID(ID);
                                info.setImage_url(image);
                                info.setComp_name(name);
                                info.setComp_category(category);
                                info.setComp_contactNo(contactNo);
                                info.setComp_rate(rate);
                                info.setComp_address(address);

                                companiesInfo.add(info);
                            }
                            companiesAdapter.notifyDataSetChanged();
                        }else Log.e("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    private void getProducts() {
        db.collection("products")
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
                                boolean isFav = true;//(boolean) document.get("isFav");
                                String name = (String) document.get("name");
                                String desc = (String) document.get("desc");
                                String category = (String) document.get("category");
                                String image = (String) document.get("image");

                                info.setFav(isFav);
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
    public void OnClickListener(View view, int position) {
        CompanyDataObjects company = companiesInfo.get(position);
        Intent intent = new Intent(mActivity,CompanyActivity.class);
        intent.putExtra("compID",company.getCompID());
        intent.putExtra("name",company.getComp_name());
        intent.putExtra("rate",company.getComp_rate());
        intent.putExtra("category",company.getComp_category());
        intent.putExtra("address",company.getComp_address());
        intent.putExtra("image",company.getImage_url());
        startActivity(intent);
    }

    @Override
    public void OnProductClickListener(View view, int position) {
        ProductDataObjects favProduct = products.get(position);
        gotoItemDetails(favProduct.getProduct_ID(),favProduct.getProduct_name(),favProduct.getImage_url(),favProduct.getProduct_category(),favProduct.getProduct_des(),favProduct.getProduct_price());
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
        bundle.putString("hasBackground","yes");
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_to_top,R.anim.anim_top_bottom);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }
}