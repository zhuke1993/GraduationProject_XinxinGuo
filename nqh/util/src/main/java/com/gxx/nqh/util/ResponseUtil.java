package com.gxx.nqh.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by GXX on 2016/4/4.
 */
public class ResponseUtil {
    private String code;
    private String msg;

    public static ResponseUtil _401Error = new ResponseUtil("401", "登陆失效");
    public static ResponseUtil _403Error = new ResponseUtil("403", "未登陆");
    public static ResponseUtil _ServerError = new ResponseUtil("FAILED", "服务器错误");

    public ResponseUtil(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseUtil() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void write(HttpServletResponse response) throws IOException {
        response.getWriter().print(this.toString());
        response.getWriter().flush();
    }

    public ResponseUtil(Object object) {
        if (object instanceof Exception) {
            this.setCode("FAILED");
            this.setMsg(((Exception) object).getMessage());
        } else {
            this.setCode("OK");
            this.setMsg(new Gson().toJson(object));
        }
    }
}
