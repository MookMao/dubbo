package com.mook;

import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Future;

/**
 * @Author: maojunkai
 * @Date: 2018/10/18 下午9:15
 * @Description:
 */
public class ConsumerAsyncServiceTest {
    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-consumer-async.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

        long beginTime = System.currentTimeMillis();

        for (int count = 0; count < 10; count++) { // 调用10次
            Integer result = demoService.getRemoteResult(); //wait 返回结果 等待3秒

            Thread.sleep(4000l); //模拟本地复杂的逻辑操作，耗时4秒

            Future<Integer> future = RpcContext.getContext().getFuture();
            try {
                result = future.get();
            } catch (java.util.concurrent.ExecutionException e) {
                e.printStackTrace();

            }

            Integer localcalcResult = 2;//本地经过4秒处理得到的计算数据是2

            System.out.println(result + localcalcResult);//根据远程调用返回的结果和本地操作的值，得到结果集

        }
        System.out.println("call 10 times,cost time is "
                + (System.currentTimeMillis() - beginTime));

        Thread.sleep(2000000l);
    }
}
