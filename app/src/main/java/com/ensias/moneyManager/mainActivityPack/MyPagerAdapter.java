package com.ensias.moneyManager.mainActivityPack;

import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ensias.moneyManager.R;

import static com.ensias.moneyManager.Item.*;



public class MyPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_ITEMS = 3;
    private Resources mResources;

    public MyPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        mResources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                return ItemsFragment.createItemFragment(TYPE_EXPENSE);

            }
            case 1: {
                return ItemsFragment.createItemFragment(TYPE_INCOME);

            }
            case 2: {
                BalanceFragment tabBalance = new BalanceFragment();
                return tabBalance;

            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mResources.getString(R.string.tab_expense);
            case 1:
                return mResources.getString(R.string.tab_income);
            case 2:
                return mResources.getString(R.string.tab_balance);
            default:
                return null;
        }

    }
}
