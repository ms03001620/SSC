package com.icomp.isscp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.PreferencesUtils;
import com.mark.mobile.volley.RespListenerDialogToast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditId;
    private EditText mEditPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditId = (EditText)findViewById(R.id.edit_id);
        mEditPwd = (EditText)findViewById(R.id.edit_pwd);

        String loginId = PreferencesUtils.getString("data-userid", "");
        if(!TextUtils.isEmpty(loginId)){
            mEditId.setText(loginId);
        }

        mEditId.setText("1100015");
        mEditPwd.setText("wwwww");

        findViewById(R.id.text_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

        findViewById(R.id.text_forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PwdForgetActivity.class));
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (TextUtils.isEmpty(mEditId.getText()) || TextUtils.isEmpty(mEditPwd.getText())) {
                    Toast.makeText(LoginActivity.this, "请填写帐号密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetTaskContext.getInstance().doLogin(mEditId.getText().toString(), mEditPwd.getText().toString(), new RespListenerDialogToast<RespLogin>(LoginActivity.this) {
                    @Override
                    public void onResponse(RespLogin resp) {
                        if (!resp.isError()) {
                            //PreferencesUtils.putString("data-userid", mEditId.getText().toString());
                            //PreferencesUtils.putString("data-json-string", new Gson().toJson(resp));

                            Toast.makeText(LoginActivity.this, resp==null?"null1":resp.toString()+"11", 0).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("data-user", resp);
                            //startActivity(intent);
                            //finish();
                        } else {
                            Snackbar.make(v, resp.getReMsg(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
