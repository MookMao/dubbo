package com.alibaba.dubbo.demo;

import java.io.Serializable;

/**
 * @Author: maojunkai
 * @Date: 2018/10/24 上午12:19
 * @Description:
 */
public class User implements Serializable{
    private String name;

    private Integer userId;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
