package com.example.kamusbetawiindo.Adapter;

import com.example.kamusbetawiindo.Fragment.BetawiIndoFragment;
import com.example.kamusbetawiindo.Fragment.IndoBetawiFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int jumlahAtab;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int jumlahAtab) {
        super(fm);
        this.jumlahAtab = jumlahAtab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BetawiIndoFragment betawiIndoFragment = new BetawiIndoFragment();
                return betawiIndoFragment;
            case 1:
                IndoBetawiFragment indoBetawiFragment = new IndoBetawiFragment();
                return indoBetawiFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return jumlahAtab;
    }
}
