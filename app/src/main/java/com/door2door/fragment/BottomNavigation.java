package com.door2door.fragment;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.door2door.delivery.R;

import java.util.ArrayList;

public class BottomNavigation extends Fragment implements View.OnClickListener {
    View view;
    Activity mActivity;
    LinearLayout ln_home_menu,ln_bookmark_menu,ln_cart_menu,ln_user_menu,ln_settings_menu;
    TextView     tv_home_menu,tv_bookmark_menu,tv_cart_menu,tv_user_menu,tv_settings_menu;
    ImageView    img_home_menu,img_bookmark_menu,img_cart_menu,img_user_menu,img_settings_menu;
    ArrayList<LinearLayout> menuLayouts = new ArrayList<>();
    ArrayList<TextView> menuLabels = new ArrayList<>();
    ArrayList<ImageView> imageMenu = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ly_bottom_navigation,container,false);
        this.view = view;
        mActivity = getActivity();
        findView();
        return view;
    }

    private void findView() {
        ln_home_menu        = view.findViewById(R.id.ln_home_menu);
        ln_bookmark_menu    = view.findViewById(R.id.ln_bookmark_menu);
        ln_cart_menu        = view.findViewById(R.id.ln_cart_menu);
        ln_user_menu        = view.findViewById(R.id.ln_user_menu);
        ln_settings_menu    = view.findViewById(R.id.ln_setting_menu);

        tv_home_menu        = view.findViewById(R.id.tv_home_menu);
        tv_bookmark_menu    = view.findViewById(R.id.tv_bookmark_menu);
        tv_cart_menu        = view.findViewById(R.id.tv_cart_menu);
        tv_user_menu        = view.findViewById(R.id.tv_user_menu);
        tv_settings_menu    = view.findViewById(R.id.tv_settings_menu);

        img_home_menu       = view.findViewById(R.id.img_home_menu);
        img_bookmark_menu   = view.findViewById(R.id.img_bookmark_menu);
        img_cart_menu       = view.findViewById(R.id.img_cart_menu);
        img_user_menu       = view.findViewById(R.id.img_user_menu);
        img_settings_menu   = view.findViewById(R.id.img_settings_menu);

        menuLayouts.add(ln_home_menu);
        menuLayouts.add(ln_bookmark_menu);
        menuLayouts.add(ln_cart_menu);
        menuLayouts.add(ln_user_menu);
        menuLayouts.add(ln_settings_menu);

        menuLabels.add(tv_home_menu);
        menuLabels.add(tv_bookmark_menu);
        menuLabels.add(tv_cart_menu);
        menuLabels.add(tv_user_menu);
        menuLabels.add(tv_settings_menu);

        imageMenu.add(img_home_menu);
        imageMenu.add(img_bookmark_menu);
        imageMenu.add(img_cart_menu);
        imageMenu.add(img_user_menu);
        imageMenu.add(img_settings_menu);


        ln_home_menu.setOnClickListener(this);
        ln_bookmark_menu.setOnClickListener(this);
        ln_cart_menu.setOnClickListener(this);
        ln_user_menu.setOnClickListener(this);
        ln_settings_menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ln_home_menu:
                setActive(ln_home_menu,tv_home_menu,img_home_menu);
                break;
            case R.id.ln_bookmark_menu:
                setActive(ln_bookmark_menu,tv_bookmark_menu,img_bookmark_menu);
                break;
            case R.id.ln_cart_menu:
                setActive(ln_cart_menu,tv_cart_menu,img_cart_menu);
                break;
            case R.id.ln_user_menu:
                setActive(ln_user_menu,tv_user_menu,img_user_menu);
                break;
            case R.id.ln_setting_menu:
                setActive(ln_settings_menu,tv_settings_menu,img_settings_menu);
        }
    }

    private void setActive(LinearLayout active_menu,TextView tv_active_label,ImageView active_icon) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        for (LinearLayout ln : menuLayouts){
            ln.setBackgroundResource(0);
            ln.setLayoutParams(param);
        }
        for (ImageView imageView : imageMenu){
            ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor(mActivity,R.color.color1)));
        }
        for (TextView tv : menuLabels){
            tv.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams active_param = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (float) 1.5);
        active_menu.setBackgroundResource(R.drawable.dr_active_navigation_back);
        active_menu.setLayoutParams(active_param);
        ImageViewCompat.setImageTintList(active_icon, ColorStateList.valueOf(ContextCompat.getColor(mActivity,R.color.color2)));
        tv_active_label.setVisibility(View.VISIBLE);
    }

}
