package com.icomp.isscp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TabLayout.Tab home = tabLayout.newTab();
        TabLayout.Tab sports = tabLayout.newTab();
        TabLayout.Tab part = tabLayout.newTab();
        TabLayout.Tab comm = tabLayout.newTab();
        TabLayout.Tab mine = tabLayout.newTab();

        home.setCustomView(R.layout.layout_tab_home);
        sports.setCustomView(R.layout.layout_tab_sports);
        part.setCustomView(R.layout.layout_tab_part);
        comm.setCustomView(R.layout.layout_tab_comm);
        mine.setCustomView(R.layout.layout_tab_mine);

        tabLayout.addTab(home);
        tabLayout.addTab(sports);
        tabLayout.addTab(part);
        tabLayout.addTab(comm);
        tabLayout.addTab(mine);
    }
}
