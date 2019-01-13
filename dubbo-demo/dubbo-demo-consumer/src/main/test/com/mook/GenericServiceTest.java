package com.mook;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.demo.User;
import com.alibaba.dubbo.demo.UserGroup;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @Author: maojunkai
 * @Date: 2018/10/18 下午5:55
 * @Description:
 */
public class GenericServiceTest {

    private GenericService genericService;

    @Before
    public void initGenericService() {
        // 服务提供方-应用配置信息
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("appp");

        // 注册中心配置信息
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("com.alibaba.dubbo.demo.DemoService");
        reference.setGeneric(true);
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);

        genericService = reference.get();
    }

    @Test
    public void test_paramNull_and_returnVoid() {
        // null
        Object result = genericService.$invoke("doNothing", new String[]{}, new Object[]{});
        System.out.println(result);
    }

    @Test
    public void test_paramString_and_returnInteger() {
        // Integer 10
        Object result = genericService.$invoke("getBookNumByName", new String[]{"java.lang.String"}, new Object[]{"None of You"});
        System.out.println(result);
    }

    @Test
    public void test_paramint_and_returnint() {
        // 参数和返回值均为int：泛化调用时，参数类型设为int，返回结果为Integer(int的包装类)
        // Integer 1234
        Object result = genericService.$invoke("getBookNum", new String[]{"int"}, new Object[]{1234});
        System.out.println(result);
    }

    @Test
    public void test_paramList_and_returnList() {
        List<String> paramValue = Arrays.asList("a", "b", "c");
        // 对于list，不需要要转换
        // ArrayList ["a","b","c"]
        Object result = genericService.$invoke("getList", new String[]{"java.util.List"}, new Object[]{paramValue});
        System.out.println(result);
    }

    @Test
    public void test_paramList_and_returnMap() {
        List<String> paramValue = Arrays.asList("a", "b", "c");
        // 对于list和map，不需要要转换
        // Map
        Object result = genericService.$invoke("getMap", new String[]{"java.util.List"}, new Object[]{paramValue});
        System.out.println(result);
    }

    @Test
    public void test_paramPOJO_and_returnPOJO() {
        User user = new User();
        user.setUserId(111);
        user.setName("aaa5");

        // 参数和返回值均为POJO
        // 泛化调用时：
        //      参数值可以为POJO
        //      返回值为Map<String, Object>：{name=aaa5, class=com.alibaba.dubbo.demo.User, userId=111}
        Object result = genericService.$invoke("getUser", new String[]{"com.alibaba.dubbo.demo.User"}, new Object[]{user});
        System.out.println(result);
    }

    @Test
    public void test_paramPOJO_and_returnPOJO2() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 111);
        map.put("name", "aaa5");
        // 参数和返回值均为POJO
        // 泛化调用时：
        //      参数可以为Map：一般泛化调用的情况下，客户端没有参数类，所以就考虑传一个Map<String, Object>
        //      返回值为Map<String, Object>：{name=aaa5, class=com.alibaba.dubbo.demo.User, userId=111}
        Object result = genericService.$invoke("getUser", new String[]{"com.alibaba.dubbo.demo.User"}, new Object[]{map});
        System.out.println(result);
    }

    @Test
    public void test_paramPOJO_and_returnPOJO3() {
        // JSONObeject其实就是Map<String, Object>
        // 泛化调用时，考虑是不是参数用JSONObject/JSONArray/基本类型的包装类/String？？？？返回值就是Object（基本类型/List/Map）？？？？
        String input = "{\"name\":\"aaa5\",\"userId\":111}";
        // JSONObject
        JSONObject paramValue = JSON.parseObject(input);
        // 参数和返回值均为POJO
        // 泛化调用时：
        //      参数值：JSONObject/Map<String, Object>
        //      返回值：Object(具体是什么类型都有可能)，这里的返回值为Map<String, Object>
        Object result = genericService.$invoke("getUser", new String[]{"com.alibaba.dubbo.demo.User"}, new Object[]{paramValue});
        System.out.println(result);
    }

    @Test
    public void test_paramType_1() {
        // 参数和返回值均为int：泛化调用时，参数类型设为int
        // 参数值为"123"
        // Integer 123 在服务端会做类型兼容？？？
        Object result = genericService.$invoke("getBookNum", new String[]{"int"}, new Object[]{"123"});
        System.out.println(result);
    }

    @Test
    public void test_paramType_2() {
        // 参数和返回值均为int：泛化调用时，参数类型设为int
        // 参数值为"aa123"
        // Caused by: com.alibaba.dubbo.remoting.RemotingException: java.lang.NumberFormatException: For input string: "aa123"
        Object result = genericService.$invoke("getBookNum", new String[]{"int"}, new Object[]{"aa123"});
        System.out.println(result);
    }

    @Test
    public void testDubboNormal() {
        GenericService genericService = getGenericService();

//        RpcContext.getContext().setAttachment(ParamNameConstant.ENV_TAG.getName(), "dev");

        if (genericService != null) {
            String str = "{\"name\":\"mook\",\"userId\":11}";
            Object object = JSON.parseObject(str);
            Object result = genericService.$invoke("saveUser",
                    new String[]{"com.alibaba.dubbo.demo.User", "java.lang.String"},
                    new Object[]{object, "groupId"});
            String resultStr = JSON.toJSONString(result);
            List list = JSON.parseObject(resultStr, List.class);
            System.out.println(111);


            Object result2 = genericService.$invoke("saveUserGroup",
                    new String[]{"java.util.List"},
                    new Object[]{list});
            System.out.println(result2);

//            Map<String, Object> objectMap = (Map<String, Object>) result;
//            assertEquals("com.youzan.api.common.response.PlainBoolResult", objectMap.get("class"));
        }
    }

    private GenericService getGenericService() {
        // 服务提供方-应用配置信息
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("appp");

        // 注册中心配置信息
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        // 引用远程服务
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("com.alibaba.dubbo.demo.DemoService");
        reference.setGeneric(true);
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);

        GenericService genericService = reference.get();
        return genericService;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUserId(111);
        user.setName("aaa5");
        System.out.println(JSON.toJSONString(user));
        // string -> JSONObject
        System.out.println(JSON.parseObject(JSON.toJSONString(user)));

        Integer integer = 1;

        // Caused by: java.lang.ClassCastException: java.lang.Integer cannot be cast to com.alibaba.fastjson.JSONObject
//        JSONObject o1 = JSON.parseObject(JSON.toJSONString(integer));
        // string -> 具体的JavaBean
        Object o2 = JSON.parseObject(JSON.toJSONString(integer), Integer.class);

        // string -> JSONArray
        String arr = "[1, \"aaa\", {}]";
        Object o4 = JSON.parseArray(arr); // JSONArray
        System.out.println(o4);

        // string -> 自动选择Object类型：基础类型的包装类/String/JSONObject/JSONArray
        Object o3 = JSON.parse(JSON.toJSONString(integer));
        System.out.println(o3);
        System.out.println(JSON.parse(JSON.toJSONString(user))); // JSONObject
        System.out.println(JSON.parse(JSON.toJSONString("abc"))); // String
        System.out.println(JSON.parse(JSON.toJSONString(Arrays.asList(1, 2, 3)))); // JSONArray

        // javaBean -> 基础类型的包装类/String/JSONObject/JSONArray
        Object o5 = JSON.toJSON(integer);
        System.out.println(o5);
    }
}
