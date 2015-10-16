package com.icomp.isscp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.RespListenerDialogToast;

public class PwdChangeActivity extends BaseActivity {

    private RespLogin mUser;
    private EditText mEditPwd;
    private EditText mEditPwdOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);
        mUser = getIntent().getParcelableExtra("data-user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditPwd = (EditText)findViewById(R.id.edit_pwd);
        mEditPwdOk = (EditText)findViewById(R.id.edit_pwd_ok);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (TextUtils.isEmpty(mEditPwd.getText())||
                        TextUtils.isEmpty(mEditPwdOk.getText())) {
                    Toast.makeText(PwdChangeActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.getTrimmedLength(mEditPwd.getText())<4) {
                    Toast.makeText(PwdChangeActivity.this, "请输入复杂点的密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(mEditPwd.getText(), mEditPwdOk.getText())) {
                    Toast.makeText(PwdChangeActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetTaskContext.getInstance().resetPwd(
                        mUser.getReData(),
                        mEditPwd.getText().toString(),
                        new RespListenerDialogToast<RespLogin>(PwdChangeActivity.this) {
                            @Override
                            public void onResponse(RespLogin resp) {
                                if (!resp.isError()) {
                                    Toast.makeText(PwdChangeActivity.this, resp.getReMsg(), Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Snackbar.make(v, resp.getReMsg(), Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}
