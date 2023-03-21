package com.git.github.Model;

import java.util.List;

public class BaseListResponse<T> {
    private String status;
    private Integer statuscode;
    private String message;
    private int code;
    private List<T> data;
    private List<T> results;
    private List<T> states;


    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getStates() {
        return states;
    }

    public void setStates(List<T> states) {
        this.states = states;
    }
}
