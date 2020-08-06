package com.itheima.test;

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring与Junit整合测试
 *    目标：activiti与spring整合
 *         成功标志：看activiti库中是否有相关的表
 *         输出一个Repositoryervice对象
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:activiti-spring.xml")
public class ActivitiSpringIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testDeploymentObj(){
        System.out.println("部署对象："+repositoryService);
    }
}
