package com.example.memegenerator.APIResponse;

import java.util.ArrayList;
import java.util.List;

public class MainResponse {
    int code;
    List<Data> dataList = new ArrayList<>();
    String message;
    String next;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getData() {
        return dataList;
    }

    public void setData(List<Data> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
