package com.icy9.entity;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public class Response {
    public static int OK = 0;
    public static int FAIL = 1;

    private int code;
    private String message;
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public Response() {

    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public void setMsg(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "no content";
    }

    public void setMsg(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }
}
