package com.icomp.isscp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.icomp.isscp.fragment.BaseFragment;
import com.icomp.isscp.fragment.WebFragment;
import com.icomp.isscp.fragment.WebMineFragment;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.LogUtils;

public class MainActivity extends BaseActivity implements WebFragment.OnFragmentInteractionListener {
    private RespLogin mUser;
    private BaseFragment showCurrent;
    private FragmentManager mFgr;
    private FragmentTransaction mFragmentTransaction;
    private long backTime = 0;

    private final static String HOST = "http://dldx.mob.sigilsoft.com/";

    private String[] urls = new String[]{
            HOST + "UserService/TokenLogin?TokenID=",
            HOST + "Movement/Index",
            HOST + "EventActivity/Index",
            HOST + "CarveOutCommunity/Index",
            HOST + "My/Index",
            HOST + "UserService/TokenLogout?TokenID="
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = getIntent().getParcelableExtra("data-user");
        mFgr = getSupportFragmentManager();
        urls[0]+=mUser.getReData();
        urls[5]+=mUser.getReData();

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
                BaseFragment current = (BaseFragment)mFgr.findFragmentByTag("tag" + position);
                if (current == null) {
                    switch(position){
                        case 4:
                            current = WebMineFragment.newInstance(urls[position]);
                            break;
                        default:
                            current = WebFragment.newInstance(urls[position]);
                            break;
                    }
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

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        long past = now - backTime;
        if (past < 3000) {
            finish();
        } else {
            Toast.makeText(this, "再点击一次返回键退出", Toast.LENGTH_SHORT).show();
        }
        backTime = now;
    }

    @Override
    public void onFragmentInteraction(String url) {
        LogUtils.paintD("shouldOverrideUrlLoading:", url);

        if(url.endsWith("user/login")){
            logout();
        }
        if(url.endsWith("app/action/setting")){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            intent.putExtra("data-user", mUser);
            startActivityForResult(intent, 10010);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        switch(requestCode){
            case 10010:
                showCurrent.onPageLoad(urls[5]);
                break;
        }
    }
}
