package com.icomp.isscp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.RespListenerDialogToast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        findViewById(R.id.text_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                NetTaskContext.getInstance().doLogin("1100011", "123456", new RespListenerDialogToast<RespLogin>(LoginActivity.this) {
                    @Override
                    public void onResponse(RespLogin resp) {
                        if(!resp.isError()){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("data-user", resp);
                            startActivity(intent);
                        }else{
                            Snackbar.make(v, resp.getReMsg(), Snackbar.LENGTH_LONG) .show();
                        }
                    }
                });
            }
        });

    }
}
