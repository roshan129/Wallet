package com.roshanadke.wallet;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class show_expense extends AppCompatActivity {

    TabAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        viewPager=findViewById(R.id.viewPager1);
        tabLayout=findViewById(R.id.tabLayout1);

        adapter=new TabAdapter(getSupportFragmentManager());
        adapter.addFrag(new show_date_rpt(),"Datewise Report");
        adapter.addFrag(new show_category_rpt(),"Categorywise Report");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
