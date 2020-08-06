package com.example.mybatisplusdemo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @package: com.example.mybatisplusdemo.task
 * @description: 定时任务
 * @author IT讲坛
 * @create: 2019-01-09 14:26
 **/
@Slf4j
public class DemoTask {

    @Scheduled(cron ="0/2 * * * * ?}")
    public void sayHelloTask(){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info(String.format("定时方法%s执行了",methodName));
    }


    @Scheduled(cron ="0/3 * * * * ?}")
    public void sayHelloTask2(){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info(String.format("定时方法%s执行了",methodName));
    }
}
