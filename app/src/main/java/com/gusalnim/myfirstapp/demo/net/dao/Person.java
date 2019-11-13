package com.gusalnim.myfirstapp.demo.net.dao;

/**
 * Created by gusalnim on 12/11/2019.
 */
public class Person {

    private int _id; // pk
    private String name;
    private String age;
    private String phone;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
