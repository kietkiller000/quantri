package com.example.myapplication;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class MyPagerAdapterCustomerInfo extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;

    public MyPagerAdapterCustomerInfo(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CustomerInfoFragment();
            case 1:
                return new CustomerListBillFragment();
            default:
                return null;
        }
    }
}
