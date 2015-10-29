package com.icomp.isscp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.RespListenerDialogToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingActivity extends BaseActivity {

    private RespLogin mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mUser = getIntent().getParcelableExtra("data-user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("设置");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listview = (ListView)findViewById(R.id.list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(SettingActivity.this, PwdChangeActivity.class);
                        intent.putExtra("data-user", mUser);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                }
            }
        });

        List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> item1 = new HashMap<>();
        item1.put("name", "修改密码");

        HashMap<String,Object> item2 = new HashMap<>();
        item2.put("name", "清除缓存");

        data.add(item1);
        data.add(item2);

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.layout_listitem_setting, new String[]{"name"}, new int[]{R.id.text_name});
        listview.setAdapter(adapter);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog mSubmitDialog = new AlertDialog.Builder(SettingActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog_Alert).create();
                mSubmitDialog.setCancelable(false);
                mSubmitDialog.setMessage("您确定要退出登录吗？");
                mSubmitDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetTaskContext.getInstance().doTokenLogout(mUser.getReData(), new RespListenerDialogToast<RespLogin>(SettingActivity.this) {
                            @Override
                            public void onResponse(RespLogin resp) {
                                if (!resp.isError()) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
                    }
                });

                mSubmitDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                mSubmitDialog.show();
            }
        });
    }
}
