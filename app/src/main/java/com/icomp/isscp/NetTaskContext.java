package com.icomp.isscp;

import com.icomp.isscp.resp.RespLogin;
import com.mark.mobile.volley.GsonRequest;
import com.mark.mobile.volley.RespListenerToast;
import com.mark.mobile.volley.VolleyBus;

import java.util.HashMap;
import java.util.Map;


public class NetTaskContext {
    /**
     * 测试
     */
    private final static String HOST = "http://dldx.test.sigilsoft.com/";
    /**
     * 正式
     */
    //private final static String HOST = "http://dldx.test.sigilsoft.com/";


    private static NetTaskContext instance;
    private VolleyBus bus;

    private NetTaskContext(){
        bus = VolleyBus.getInstance();
    }

    public static NetTaskContext getInstance() {
        if (instance == null) {
            instance = new NetTaskContext();
        }
        return instance;
    }

    /**
     * 登录接口 ： UserService/UserLogin 参数 StudentNo(id)，Pwd
     * @param id
     * @param pwd
     * @param listener
     * @param <T>
     */
    public <T> void doLogin(String id, String pwd, RespListenerToast listener) {
        Map<String, String> param = new HashMap<>();
        param.put("StudentNo", id);
        param.put("Pwd", pwd);
        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/UserLogin", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }

    /**
     * 找回密码： UserService/SetForgetPwd 参数Email，StudentNo(id)，Name
     * @param email
     * @param id
     * @param name
     * @param listener
     * @param <T>
     */
    public <T> void findPwd(String email, String id, String name, RespListenerToast listener) {
        Map<String, String> param = new HashMap<>();
        param.put("Email", email);
        param.put("StudentNo", id);
        param.put("Name", name);
        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/SetForgetPwd", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }

    /**
     * UserService/SetUserReg   参数StudentNo(id)，Email，Name，Pwd
     * @param id
     * @param email
     * @param name
     * @param pwd
     * @param listener
     * @param <T>
     */
    public <T> void reg(String id, String email, String name, String pwd, RespListenerToast listener) {
        Map<String, String> param = new HashMap<>();
        param.put("StudentNo", id);
        param.put("Email", email);
        param.put("Name", name);
        param.put("Pwd", pwd);

        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/SetUserReg", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }

    /**
     * 修改密码 UserService/SetRePwd 参数TokenID，Pwd
     * @param token
     * @param pwd
     * @param listener
     * @param <T>
     */
    public <T> void resetPwd(String token, String pwd, RespListenerToast listener) {
        Map<String, String> param = new HashMap<>();
        param.put("TokenID", token);
        param.put("Pwd", pwd);
        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/SetRePwd", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }
}
