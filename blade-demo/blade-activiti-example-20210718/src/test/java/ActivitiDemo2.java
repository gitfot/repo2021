import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zz
 * @date 2021/7/26
 */
public class ActivitiDemo2 {

	/**
	 * 查询流程实例
	 */
	@Test
	public void queryProcessInstance() {
		// 流程定义key
		String processDefinitionKey = "evection";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取RunTimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		List<ProcessInstance> list = runtimeService
			.createProcessInstanceQuery()
			.processDefinitionKey(processDefinitionKey)//
			.list();
		for (ProcessInstance processInstance : list) {
			System.out.println("----------------------------");
			System.out.println("流程实例id："
				+ processInstance.getProcessInstanceId());
			System.out.println("所属流程定义id："
				+ processInstance.getProcessDefinitionId());
			System.out.println("是否执行完成：" + processInstance.isEnded());
			System.out.println("是否暂停：" + processInstance.isSuspended());
			System.out.println("当前活动标识：" + processInstance.getActivityId());
		}
	}

	/**
	 * 设置流程负责人
	 */
	@Test
	public void assigneeUEL(){
		// 获取流程引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//获取 RuntimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 设置assignee的取值，用户可以在界面上设置流程的执行
		Map<String,Object> assigneeMap = new HashMap<>();
		assigneeMap.put("assignee0","张三");
		assigneeMap.put("assignee1","李经理");
		assigneeMap.put("assignee2","王总经理");
		assigneeMap.put("assignee3","赵财务");
		// 启动流程实例，同时还要设置流程定义的assignee的值
		runtimeService.startProcessInstanceByKey("myEvection1",assigneeMap);
		// 输出
		System.out.println(processEngine.getName());
	}

	public class MyTaskListener implements TaskListener {
		@Override
		public void notify(DelegateTask delegateTask) {
			if(delegateTask.getName().equals("创建出差申请")&&
				delegateTask.getEventName().equals("create")){
				//这里指定任务负责人
				delegateTask.setAssignee("张三");
			}
		}
	}

	/**
	 * 查询任务关联businessKey
	 */
	@Test
	public void findProcessInstance(){
		// 获取processEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取TaskService
		TaskService taskService = processEngine.getTaskService();
		// 获取RuntimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 查询流程定义的对象
		Task task = taskService.createTaskQuery()
			.processDefinitionKey("myEvection1")
			.taskAssignee("张三")
			.singleResult();
		// 使用task对象获取实例id
		String processInstanceId = task.getProcessInstanceId();
		// 使用实例id，获取流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		// 使用processInstance，得到 businessKey
		String businessKey = processInstance.getBusinessKey();
		System.out.println("businessKey=="+businessKey);
	}

	/**
	 * 完成任务，判断当前用户是否有权限
	 */
	@Test
	public void completTask2() {
		//任务id
		String taskId = "15005";
		// 任务负责人
		String assingee = "张三";
		//获取processEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 创建TaskService
		TaskService taskService = processEngine.getTaskService();
		// 完成任务前，需要校验该负责人可以完成当前任务
		// 校验方法：
		// 根据任务id和任务负责人查询当前任务，如果查到该用户有权限，就完成
		Task task = taskService.createTaskQuery()
			.taskId(taskId)
			.taskAssignee(assingee)
			.singleResult();
		if(task != null){
			taskService.complete(taskId);
			System.out.println("完成任务");
		}
	}








}
