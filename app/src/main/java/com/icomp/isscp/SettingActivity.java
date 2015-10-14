package com.icomp.isscp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("设置");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listview = (ListView)findViewById(R.id.list);

        List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> item1 = new HashMap<>();
        item1.put("name", "修改密码");

        HashMap<String,Object> item2 = new HashMap<>();
        item2.put("name", "清除缓存");

        data.add(item1);
        data.add(item2);

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.layout_listitem_setting, new String[]{"name"}, new int[]{R.id.text_name});
        listview.setAdapter(adapter);
    }
}
