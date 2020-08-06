package com.itheima.day04;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 *  启动流程实例，动态设置assignee
 */
public class AssigneeListener {


    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RuntimeService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 存储服务
        RepositoryService rs = processEngine.getRepositoryService();

        // 任务服务
        TaskService taskService = processEngine.getTaskService();
        //部署流程定义
        Deployment dep = rs.createDeployment().addClasspathResource("diagram/holiday3.bpmn").deploy();
        ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();

        //4.启动流程实例，同时还要设置流程定义的assignee的值
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(pd.getId());

        //5.输出
        System.out.println(processEngine.getName());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("代理人：" + task.getAssignee());
    }
}
