package com.icomp.isscp.resp;

import android.os.Parcel;
import android.os.Parcelable;

public class RespLogin implements Parcelable{
/*    {
        "isError": false,
            "ReData": "DE896DF9-5719-4190-B696-73FE4F9CEE71",
            "ReMsg": "登录成功！"
    }*/

    private boolean isError;
    private String reData;
    private String reMsg;

    protected RespLogin(Parcel in) {
        isError = in.readByte() != 0;
        reData = in.readString();
        reMsg = in.readString();
    }

    public static final Creator<RespLogin> CREATOR = new Creator<RespLogin>() {
        @Override
        public RespLogin createFromParcel(Parcel in) {
            return new RespLogin(in);
        }

        @Override
        public RespLogin[] newArray(int size) {
            return new RespLogin[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isError ? 1 : 0));
        dest.writeString(reData);
        dest.writeString(reMsg);
    }
}
