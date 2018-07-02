package com.example.andre.app_swipepage;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager1);


        SwipeAdapter MyswipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(MyswipeAdapter);
    }

}
