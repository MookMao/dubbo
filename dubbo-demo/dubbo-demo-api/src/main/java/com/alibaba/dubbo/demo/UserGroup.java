package com.alibaba.dubbo.demo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: maojunkai
 * @Date: 2018/10/24 上午12:45
 * @Description:
 */
public class UserGroup implements Serializable {
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    private List<User> userList;
}
