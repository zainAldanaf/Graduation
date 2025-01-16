package com.example.palliativecareguidelines.Adapters;

import android.content.Context;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.palliativecareguidelines.Fragments.ChatFragment;
import com.example.palliativecareguidelines.Fragments.ContactsFragment;
import com.example.palliativecareguidelines.Fragments.RequestFragment;

public class MyAdapter extends FragmentPagerAdapter {

            private Context myContext;
            int totalTabs;

            public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
                super(fm);
                myContext = context;
                this.totalTabs = totalTabs;
            }


            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        ChatFragment chatFragment = new ChatFragment();
                        return chatFragment;
                    case 1:
                        ContactsFragment contactsFragment = new ContactsFragment();
                        return contactsFragment;

                    case 2:
                        RequestFragment requestFragment = new RequestFragment();
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