package com.example.kamusbetawiindo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;

import com.example.kamusbetawiindo.Adapter.ViewPagerAdapter;
import com.example.kamusbetawiindo.DB.DBHelper;
import com.example.kamusbetawiindo.Model.Kamus;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    TabLayout tabLayoutMain;
    ViewPager viewPagerMain;

    List<Kamus> listBasanya;
    List<Kamus> listIndo;
    DBHelper dbHelper;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayoutMain = (TabLayout)findViewById(R.id.tabLayoutMain);
        viewPagerMain = (ViewPager)findViewById(R.id.vPMain);

        listBasanya = new ArrayList<>();
        listIndo = new ArrayList<>();
        tabLayoutMain.addTab(tabLayoutMain.newTab().setText("Betawi - Indo"));
        tabLayoutMain.addTab(tabLayoutMain.newTab().setText("Indo - Betawi"));

        dbHelper = new DBHelper(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),tabLayoutMain.getTabCount());
        viewPagerMain.setAdapter(adapter);
        viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutMain));

        tabLayoutMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
