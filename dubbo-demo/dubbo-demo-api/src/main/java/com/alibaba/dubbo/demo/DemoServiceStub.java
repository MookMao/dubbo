package com.alibaba.dubbo.demo;

import java.util.List;
import java.util.Map;

/**
 * @Author: maojunkai
 * @Date: 2019/1/13 10:12 PM
 * @Description:
 */
public class DemoServiceStub implements DemoService {

    private final DemoService demoService;

    /**
     * 传入远程代理对象
     * @param demoService
     */
    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void doNothing() {

    }

    @Override
    public Integer getBookNumByName(String name) {
        return null;
    }

    @Override
    public int getBookNum(int num) {
        return 0;
    }

    @Override
    public List<String> getList(List<String> list) {
        return null;
    }

    @Override
    public Map<User, Integer> getMap(List<String> list) {
        return null;
    }

    @Override
    public User getUser(User user) {
        return null;
    }

    @Override
    public String sayHello(String name) {
        if ("world".equals(name)) {
            return "world" + "from" + this.getClass().getSimpleName();
        }
        return demoService.sayHello(name);
    }

    @Override
    public Integer getRemoteResult() {
        return null;
    }

    @Override
    public List<List<UserGroup>> saveUser(User user, String str) {
        return null;
    }

    @Override
    public boolean saveUserGroup(List<UserGroup> list) {
        return false;
    }
}
