package com.icomp.isscp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.utils.StringUtils;
import com.mark.mobile.volley.RespListenerDialogToast;

public class RegActivity extends BaseActivity {

    private EditText mEditId;
    private EditText mEditName;
    private EditText mEditMail;
    private EditText mEditPwd;
    private EditText mEditPwdOk;
    private CheckBox mCheckLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditId = (EditText)findViewById(R.id.edit_id);
        mEditName = (EditText)findViewById(R.id.edit_name);
        mEditMail = (EditText)findViewById(R.id.edit_email);
        mEditPwd = (EditText)findViewById(R.id.edit_pwd);
        mEditPwdOk = (EditText)findViewById(R.id.edit_pwd_ok);
        mCheckLicense = (CheckBox)findViewById(R.id.check_license);

        findViewById(R.id.text_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (TextUtils.isEmpty(mEditId.getText()) ||
                        TextUtils.isEmpty(mEditName.getText())||
                        TextUtils.isEmpty(mEditMail.getText())||
                        TextUtils.isEmpty(mEditPwd.getText())||
                        TextUtils.isEmpty(mEditPwdOk.getText())) {
                    Toast.makeText(RegActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!StringUtils.isEmail(mEditMail.getText().toString())) {
                    Toast.makeText(RegActivity.this, "请填写正确的电子邮件地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.getTrimmedLength(mEditPwd.getText())<4) {
                    Toast.makeText(RegActivity.this, "请输入复杂点的密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(mEditPwd.getText(), mEditPwdOk.getText())) {
                    Toast.makeText(RegActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!mCheckLicense.isChecked()){
                    Toast.makeText(RegActivity.this, "请接受注册协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetTaskContext.getInstance().reg(
                        mEditId.getText().toString(),
                        mEditMail.getText().toString(),
                        mEditName.getText().toString(),
                        mEditPwd.getText().toString(),
                        new RespListenerDialogToast<RespLogin>(RegActivity.this) {
                    @Override
                    public void onResponse(RespLogin resp) {
                        if (!resp.isError()) {
                            Toast.makeText(RegActivity.this, resp.getReMsg(), Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Snackbar.make(v, resp.getReMsg(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
