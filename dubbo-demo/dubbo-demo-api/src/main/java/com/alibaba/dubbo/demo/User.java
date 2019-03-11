package com.alibaba.dubbo.demo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: maojunkai
 * @Date: 2018/10/24 上午12:19
 * @Description:
 */
public class User implements Serializable{
    private String name;

    private Integer userId;

    /**
     * 测试telnet invoke参数类型转换
     */
    List<Date> dates;

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

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
