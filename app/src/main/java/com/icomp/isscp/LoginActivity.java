package com.icomp.isscp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.RespListenerToast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                NetTaskContext.getInstance().doLogin("1100011", "123456", new RespListenerToast<RespLogin>() {
                    @Override
                    public void onResponse(RespLogin resp) {
                        Snackbar.make(view, resp.getReData(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });

                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
