package com.example.administrator.mvpdemo.bean;

public class LoginBean {
    public int errorCode;
    public String errorMsg;
    public Data data;

    @Override
    public String toString() {
        return "LoginBean{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public String email;
        public String icon;
        public long id;
        public String password;
        public String token;
        public int type;
        public String username;

        @Override
        public String toString() {
            return "Data{" +
                    "email='" + email + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", password='" + password + '\'' +
                    ", token='" + token + '\'' +
                    ", type=" + type +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}
