package com.icomp.isscp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.icomp.isscp.fragment.WebFragment;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.LogUtils;

public class MainActivity extends BaseActivity implements WebFragment.OnFragmentInteractionListener {
    private RespLogin mUser;
    private WebFragment showCurrent;
    private FragmentManager mFgr;
    private FragmentTransaction mFragmentTransaction;

/*    private String[] urls = new String[]{
            "http://dldx.test.sigilsoft.com/",
            "http://dldx.demo.sigilsoft.com/mob/Movement_record.html",
            "http://dldx.demo.sigilsoft.com/mob/Competition.html",
            "http://dldx.demo.sigilsoft.com/mob/Community_management.html",
            "http://dldx.demo.sigilsoft.com/mob/My.html"
    };*/

    private String[] urls = new String[]{
            "http://dldx.test.sigilsoft.com/UserService/TokenLogin?TokenID=",
            "http://dldx.demo.sigilsoft.com/mob/Movement_record.html",
            "http://dldx.demo.sigilsoft.com/mob/Competition.html",
            "http://dldx.demo.sigilsoft.com/mob/Community_management.html",
            "http://dldx.demo.sigilsoft.com/mob/My.html"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("");
        setSupportActionBar(toolbar);
        mUser = getIntent().getParcelableExtra("data-user");
        mFgr = getSupportFragmentManager();

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

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                WebFragment current = (WebFragment) mFgr.findFragmentByTag("tag" + position);
                if (current == null) {
                    current = WebFragment.newInstance(urls[position], mUser);
                }
                mFragmentTransaction = mFgr.beginTransaction();
                if (!current.isAdded()) {
                    if (showCurrent != null) {
                        mFragmentTransaction.hide(showCurrent);
                    }
                    mFragmentTransaction.add(R.id.frLayout, current, "tag" + position);
                    showCurrent = current;
                } else {
                    if (showCurrent != null) {
                        mFragmentTransaction.hide(showCurrent);
                    }
                    mFragmentTransaction.show(current);
                    showCurrent = current;
                }
                mFragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtils.paintD("onTabReselected", MainActivity.this);
            }
        });

        tabLayout.addTab(home);
        tabLayout.addTab(sports);
        tabLayout.addTab(part);
        tabLayout.addTab(comm);
        tabLayout.addTab(mine);
    }

    private long backTime = 0;
    @Override
    public void onBackPressed() {
        if(showCurrent!=null){
            if(showCurrent.onBackPressed()){
                return;
            }
        }

        long now = System.currentTimeMillis();
        long past = now - backTime;
        if (past < 3000) {
            finish();
        } else {
            Toast.makeText(this, "再按退出", Toast.LENGTH_SHORT).show();
        }
        backTime = now;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            intent.putExtra("data-user", mUser);
            startActivityForResult(intent, 10010);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        switch(requestCode){
            case 10010:
                finish();
                break;
        }

    }
}
