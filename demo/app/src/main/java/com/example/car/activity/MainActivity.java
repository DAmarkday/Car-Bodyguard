package com.example.car.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.car.R;
import com.example.car.fragment.CarFragmentAdapter;
import com.example.car.fragment.FirstFragment;
import com.example.car.fragment.SecondFragment;
import com.example.car.fragment.ThirdFramgent;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private ViewPager content;
    private ArrayList<Fragment> fragments;
    private CarFragmentAdapter adapter;
    private LinearLayout fg_item_one;
    private LinearLayout fg_item_two;
    private LinearLayout fg_item_three;
    private ImageView first_page;
    private ImageView second_page;
    private ImageView third_page;
    private static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
         Init();
        initView();
        setListener();
        SharedPreferences preferences=getSharedPreferences("serverip", Context.MODE_PRIVATE);
        ip=preferences.getString("serverip", "none");

    }

public void Init(){

   first_page = (ImageView) findViewById(R.id.history_record_picture);
    second_page = (ImageView) findViewById(R.id.microphone_picture);
    third_page = (ImageView) findViewById(R.id.user_settings_picture);
    fg_item_one = (LinearLayout) findViewById(R.id.fg_item_one);
    fg_item_two = (LinearLayout) findViewById(R.id.fg_item_two);
    fg_item_three = (LinearLayout) findViewById(R.id.fg_item_three);
    fg_item_one.setOnClickListener(this);
    fg_item_two.setOnClickListener(this);
    fg_item_three.setOnClickListener(this);
}

    private void initView(){
        content = findViewById(R.id.viewpager);
        content.setOnPageChangeListener(new MyPagerChangeListener());
        initViewPager();
        //setting = findViewById(R.id.title_left);
        content.setAdapter(adapter);
        content.setCurrentItem(0);
        first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page_press));

    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFramgent());
        adapter = new CarFragmentAdapter(getSupportFragmentManager(),fragments);
    }
    public static String returnIp(){
        return ip;
    }

    private void setListener() {
        //setting.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        /*switch (v.getId()){
//            case R.id.title_left:
//                startActivity(new Intent(this, SettingActivity.class));
//                break;
//        }*/
//    }
public void onClick(View v) {
    switch (v.getId()) {
        case R.id.fg_item_one:
            content.setCurrentItem(0);
            first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page_press));
            second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page));
            third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page));
            break;
        case R.id.fg_item_two:
            content.setCurrentItem(1);
            first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page));
            second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page_press));
            third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page));
            break;
        case R.id.fg_item_three:
            content.setCurrentItem(2);
            first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page));
            second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page));
            third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page_press));
            break;
    }
}
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page_press));
                    second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page));
                    third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page));
                    break;
                case 1:
                    first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page));
                    second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page_press));
                    third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page));
                    break;
                case 2:
                    first_page.setImageDrawable(getResources().getDrawable(R.mipmap.first_page));
                    second_page.setImageDrawable(getResources().getDrawable(R.mipmap.second_page));
                    third_page.setImageDrawable(getResources().getDrawable(R.mipmap.third_page_press));
                    break;
            }
        }
    }
    
}
