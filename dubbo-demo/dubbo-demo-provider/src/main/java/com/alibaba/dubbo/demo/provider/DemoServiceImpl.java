/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.demo.User;
import com.alibaba.dubbo.demo.UserGroup;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.*;

public class DemoServiceImpl implements DemoService {

    @Override
    public void doNothing() {
        System.out.println("doNothing");
    }

    /**
     * invoke com.alibaba.dubbo.demo.DemoService.processListOfDate(["2019-01-23 14:50:55", "2018-01-23 14:50:55"])
     * @param dates
     * @return
     */
    @Override
    public int processListOfDate(List<Date> dates) {
        System.out.println(1);
        return 1;
    }

    @Override
    public int processSet(Set<Long> set) {
        System.out.println(1);
        return 1;
    }

    public static void main(String[] args) {
        Set<Long> set = new TreeSet<>();
        set.add(1L);
        set.add(2L);
        System.out.println(JSON.toJSONString(set));
    }

    @Override
    public Integer getBookNumByName(String name) {
        System.out.println(this.getClass().getName() + "--" + name);
        return Integer.valueOf(10);
    }

    @Override
    public int getBookNum(int num) {
        System.out.println(this.getClass().getName() + "--" + num);
        return num;
    }

    @Override
    public List<String> getList(List<String> list) {
        System.out.println(this.getClass().getName() + "--" + JSON.toJSONString(list));
        return list;
    }

    @Override
    public Map<User, Integer> getMap(List<String> list) {
        System.out.println(this.getClass().getName() + "--" + JSON.toJSONString(list));
        final Map<User, Integer> map = Maps.newHashMap();
        list.forEach( o -> {
            User user = new User();
            user.setName(o);
            map.put(user, 1);
        });
        return map;
    }

    @Override
    public User getUser(User user) {
        System.out.println(this.getClass().getName() + "--" + JSON.toJSONString(user));
        return user;
    }

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public Integer getRemoteResult() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public List<List<UserGroup>> saveUser(User user, String str) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(str);
        userGroup.setUserList(Arrays.asList(user));
        return Arrays.asList(Arrays.asList(userGroup));
    }

    @Override
    public boolean saveUserGroup(List<UserGroup> list) {
        return true;
    }

}