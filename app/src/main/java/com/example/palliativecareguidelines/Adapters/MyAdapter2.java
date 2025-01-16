package com.example.palliativecareguidelines.Adapters;

import android.content.Context;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.palliativecareguidelines.Fragments.ChatFragment;
import com.example.palliativecareguidelines.Fragments.ChatFragment2;
import com.example.palliativecareguidelines.Fragments.ContactFragment2;
import com.example.palliativecareguidelines.Fragments.ContactsFragment;
import com.example.palliativecareguidelines.Fragments.RequestFragment;
import com.example.palliativecareguidelines.Fragments.RequestFragment2;

public class MyAdapter2 extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter2(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ChatFragment2 chatFragment = new ChatFragment2();
                return chatFragment;
            case 1:
                ContactFragment2 contactsFragment = new ContactFragment2();
                return contactsFragment;

            case 2:
                RequestFragment2 requestFragment = new RequestFragment2();
                return requestFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}