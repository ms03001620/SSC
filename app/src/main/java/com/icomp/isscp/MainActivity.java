package com.icomp.isscp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.webkit.WebView;
import android.widget.Toast;

import com.icomp.isscp.fragment.WebFragment;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.LogUtils;
import com.mark.mobile.volley.RespListenerDialogToast;

public class MainActivity extends BaseActivity implements WebFragment.OnFragmentInteractionListener {
    private RespLogin mUser;
    private WebFragment showCurrent;
    private FragmentManager mFgr;
    private FragmentTransaction mFragmentTransaction;

    private String[] urls = new String[]{
            "http://dldx.mob.sigilsoft.com/UserService/TokenLogin?TokenID=",
        "http://dldx.mob.sigilsoft.com/Main/Index",
        "http://dldx.mob.sigilsoft.com/Movement/Index",
        "http://dldx.mob.sigilsoft.com/EventActivity/Index",
        "http://dldx.mob.sigilsoft.com/CarveOutCommunity/Index",
        "http://dldx.mob.sigilsoft.com/My/Index"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        NetTaskContext.getInstance().doTokenLogin(mUser.getReData(), new RespListenerDialogToast<RespLogin>(this) {
            @Override
            public void onResponse(RespLogin resp) {
                Snackbar.make(getWindow().getDecorView(), resp.getReMsg(), Snackbar.LENGTH_LONG).show();
            }
        });

        WebView sv = new WebView(this);
        sv.loadUrl("http://dldx.mob.sigilsoft.com/UserService/TokenLogin?TokenID="+mUser.getReData());
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
            Toast.makeText(this, "再点击一次返回键退出", Toast.LENGTH_SHORT).show();
        }
        backTime = now;
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
                logout();
                break;
        }
    }
}
