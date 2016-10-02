package com.example.vipin.assignment3;

import java.util.Date;

/**
 * Created by vipin on 2/10/16.
 */
public class Task {
    private int _id;
    private String _name;
    private String _desc;

    public Task(){}

    public Task(String name, String desc){
        this._name = name;
        this._desc = desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }
}
