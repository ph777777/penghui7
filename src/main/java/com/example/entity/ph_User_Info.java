package com.example.entity;

public class ph_User_Info {

    public String cust_id;
    public String user_name;
    public String password;

    public ph_User_Info(String cust_id, String user_name, String password, String describe, String cooking_type, String grade) {
        this.cust_id = cust_id;
        this.user_name = user_name;
        this.password = password;
    }

    public ph_User_Info() {
    }

    @Override
    public String toString() {
        return "ph_User_Info{" +
                "cust_id='" + cust_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}