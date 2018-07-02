package com.example.andre.app_swipepage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter {


    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        //Paginas
        Fragment frag = new PageFragment();
        Fragment frag2 = new Page2Fragment();
        Fragment frag3 = new Page3Fragment();

        Bundle bund = new Bundle();
        bund.putInt("count", position + 1);
        frag.setArguments(bund);
        frag2.setArguments(bund);
        frag3.setArguments(bund);

        if(position == 0){
            return frag;
        }else if(position == 1){
            return frag2;
        }else{
            return frag3;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
