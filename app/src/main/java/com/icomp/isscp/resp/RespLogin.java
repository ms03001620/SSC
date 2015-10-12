package com.icomp.isscp.resp;

public class RespLogin {
/*    {
        "isError": false,
            "ReData": "DE896DF9-5719-4190-B696-73FE4F9CEE71",
            "ReMsg": "登录成功！"
    }*/

    private boolean isError;
    private String reData;
    private String reMsg;

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getReData() {
        return reData;
    }

    public void setReData(String reData) {
        this.reData = reData;
    }

    public String getReMsg() {
        return reMsg;
    }

    public void setReMsg(String reMsg) {
        this.reMsg = reMsg;
    }
}
