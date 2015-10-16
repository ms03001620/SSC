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

public class PwdForgetActivity extends BaseActivity {

    private EditText mEditMail;
    private EditText mEditId;
    private EditText mEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_forget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditMail = (EditText)findViewById(R.id.edit_email);
        mEditId = (EditText)findViewById(R.id.edit_id);
        mEditName = (EditText)findViewById(R.id.edit_name);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (TextUtils.isEmpty(mEditMail.getText()) || TextUtils.isEmpty(mEditId.getText())|| TextUtils.isEmpty(mEditName.getText())) {
                    Toast.makeText(PwdForgetActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

               NetTaskContext.getInstance().findPwd(mEditMail.getText().toString(), mEditId.getText().toString(), mEditId.getText().toString(),new RespListenerDialogToast<RespLogin>(PwdForgetActivity.this) {
                   @Override
                   public void onResponse(RespLogin resp) {
                       if (!resp.isError()) {
                           Toast.makeText(PwdForgetActivity.this, resp.getReMsg(), Toast.LENGTH_LONG).show();
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
