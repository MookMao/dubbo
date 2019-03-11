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
package com.alibaba.dubbo.demo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DemoService {

    /**
     * 无参数和无返回值
     */
    void doNothing();

    /**
     * 测试telnet invoke参数类型转换
     * @param dates
     * @return
     */
    int processListOfDate(List<Date> dates);

    int processSet(Set<Long> set);

    /**
     * 参数和返回值为基本类型
     * @param name
     * @return
     */
    Integer getBookNumByName(String name);

    /**
     * 参数和返回值类型为int
     * @param num
     * @return
     */
    int getBookNum(int num);

    /**
     * 参数和返回值为list
     * @param list
     * @return
     */
    List<String> getList(List<String> list);

    /**
     * 参数为List，返回值为Map
     * @param list
     * @return
     */
    Map<User, Integer> getMap(List<String> list);

    /**
     * 参数和返回值类型为POJO
     * @param user
     * @return
     */
    User getUser(User user);

    String sayHello(String name);

    Integer getRemoteResult();

    List<List<UserGroup>> saveUser(User user, String str);

    boolean saveUserGroup(List<UserGroup> list);

}