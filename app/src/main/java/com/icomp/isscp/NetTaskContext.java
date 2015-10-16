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

    public <T> void doLogin(String id, String pwd, RespListenerToast listener) {
        Map<String, String> param = new HashMap<>();
        param.put("StudentNo", id);
        param.put("Pwd", pwd);
        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/UserLogin", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }

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
        param.put("StudentNo", email);
        param.put("Email", email);
        param.put("Name", email);
        param.put("Pwd", email);

        GsonRequest<T> request = new GsonRequest<T>(HOST + "UserService/SetUserReg", listener, RespLogin.class, param);
        bus.addToRequestQueue(request);
    }


}
