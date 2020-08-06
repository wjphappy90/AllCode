package com.example.mybatisplusdemo.config;

import com.example.mybatisplusdemo.task.DemoTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 *
 * @author IT讲坛
 */
@Slf4j
@Configuration
public class TaskConfiguration {

    @Bean("demoTask")
    @ConditionalOnMissingBean(name = "demoTask")
    public DemoTask hydraSyncFileRetryTask() {
        log.info("########初始化定时任务配置");
        return new DemoTask();
    }
}
