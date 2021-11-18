import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zz
 * @date 2021/7/25
 */
public class ActivitiDemo {

	@Test
	public void testDeploy() {
		// 1、创建ProcessEngine
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//先构建ProcessEngineConfiguration
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

		//通过ProcessEngineConfiguration创建ProcessEngine，此时会创建数据库
		ProcessEngine processEngine = configuration.buildProcessEngine();

		// 2、得到RepositoryService实例
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 3、使用RepositoryService进行部署
		Deployment deployment = repositoryService.createDeployment()
			.addClasspathResource("bpmn/travel.bpmn") // 添加bpmn资源
			.addClasspathResource("bpmn/travel.png") // 添加png资源
			.name("出差申请流程")
			.deploy();
		// 4、输出部署信息
		System.out.println("流程部署id：" + deployment.getId());
		System.out.println("流程部署名称：" + deployment.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartProcess(){
		// 1、创建ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 2、获取RunTimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 3、根据流程定义Id启动流程（procdef表的key字段）
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("出差流程申请");
		// 输出内容
		System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例id：" + processInstance.getId());
		System.out.println("当前活动Id：" + processInstance.getActivityId());
	}

	@Test
	public void testFindPersonalTaskList() {
		// 任务负责人
		String assignee = "test2";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 创建TaskService
		TaskService taskService = processEngine.getTaskService();
		// 根据流程key 和 任务负责人 查询任务
		List<Task> list = taskService.createTaskQuery()
			.processDefinitionKey("出差流程申请") //流程Key
			.taskAssignee(assignee)//只查询该任务负责人的任务
			.list();
		for (Task task : list) {
			System.out.println("流程实例id：" + task.getProcessInstanceId());
			System.out.println("任务id：" + task.getId());
			System.out.println("任务负责人：" + task.getAssignee());
			System.out.println("任务名称：" + task.getName());
		}
	}

	/**
	 * 处理任务
	 */
	@Test
	public void completeTask(){
		// 获取引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 获取taskService
		TaskService taskService = processEngine.getTaskService();
		// 根据流程key 和 任务的负责人 查询任务
		// 返回一个任务对象
		Task task = taskService.createTaskQuery()
			.processDefinitionKey("出差流程申请") //流程Key
			.taskAssignee("test2") //要查询的负责人
			.singleResult();
		// 完成任务,参数：任务id
		taskService.complete(task.getId());
	}

	/**
	 * 查询流程定义
	 */
	@Test
	public void queryProcessDefinition(){
		// 获取引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// repositoryService
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 得到ProcessDefinitionQuery 对象
		ProcessDefinitionQuery processDefinitionQuery =
			repositoryService.createProcessDefinitionQuery();
		// 查询出当前所有的流程定义
		// 条件：processDefinitionKey =evection
		// orderByProcessDefinitionVersion 按照版本排序
		// desc倒叙
		// list 返回集合
		List<ProcessDefinition> definitionList =
			processDefinitionQuery.processDefinitionKey("出差流程申请")
				.orderByProcessDefinitionVersion()
				.desc()
				.list();
		// 输出流程定义信息
		for (ProcessDefinition processDefinition : definitionList) {
			System.out.println("流程定义 id="+processDefinition.getId());
			System.out.println("流程定义 name="+processDefinition.getName());
			System.out.println("流程定义 key="+processDefinition.getKey());
			System.out.println("流程定义 Version="+processDefinition.getVersion());
			System.out.println("流程部署ID ="+processDefinition.getDeploymentId());
		}
	}

	/**
	 * 删除流程
	 */
	@Test
	public void deleteDeployment() {
		// 流程部署id
		String deploymentId = "10001";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 通过流程引擎获取repositoryService
		RepositoryService repositoryService = processEngine
			.getRepositoryService();
		//删除流程定义，如果该流程定义已有流程实例启动则删除时出错
		repositoryService.deleteDeployment(deploymentId);
		//设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
		//repositoryService.deleteDeployment(deploymentId, true);
	}

	/**
	 * 获取 流程资资源
	 */
	@Test
	public void queryBpmnFile() throws IOException {
		// 1、得到引擎
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 2、获取repositoryService
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 3、得到查询器：ProcessDefinitionQuery，设置查询条件,得到想要的流程定义
		ProcessDefinition processDefinition =
			repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("myEvection")
				.singleResult();
		// 4、通过流程定义信息，得到部署ID
		String deploymentId = processDefinition.getDeploymentId();
		// 5、通过repositoryService的方法，实现读取图片信息和bpmn信息
		// png图片的流
		InputStream pngInput = repositoryService.getResourceAsStream(deploymentId,
			processDefinition.getDiagramResourceName());
		// bpmn文件的流
		InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId,
			processDefinition.getResourceName());
		// 6、构造OutputStream流
		File file_png = new File("d:/evectionflow01.png");
		File file_bpmn = new File("d:/evectionflow01.bpmn");
		FileOutputStream bpmnOut = new FileOutputStream(file_bpmn);
		FileOutputStream pngOut = new FileOutputStream(file_png);
		// 7、输入流，输出流的转换
		IOUtils.copy(pngInput,pngOut);
		IOUtils.copy(bpmnInput,bpmnOut);
		// 8、关闭流
		pngOut.close();
		bpmnOut.close();
		pngInput.close();
		bpmnInput.close();
	}

	/**
	 * 查看历史信息
	 */
	@Test
	public void findHistoryInfo(){
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		HistoryService historyService = processEngine.getHistoryService();
		// 获取 actinst表的查询对象
		HistoricActivityInstanceQuery instanceQuery =
			historyService.createHistoricActivityInstanceQuery();
		// 查询 actinst表，条件：根据 InstanceId 查询
		// instanceQuery.processInstanceId("2501");
		// 查询 actinst表，条件：根据 DefinitionId 查询
		instanceQuery.processDefinitionId("myEvection:1:4");
		// 增加排序操作,orderByHistoricActivityInstanceStartTime 根据开始时间排序 asc 升序
		instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
		// 查询所有内容
		List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
		// 输出
		for (HistoricActivityInstance hi : activityInstanceList) {
			System.out.println(hi.getActivityId());
			System.out.println(hi.getActivityName());
			System.out.println(hi.getProcessDefinitionId());
			System.out.println(hi.getProcessInstanceId());
			System.out.println("<==========================>");
		}
	}

	/**
	 * 启动流程实例，添加businessKey
	 */
	@Test
	public void addBusinessKey(){
		// 1、得到ProcessEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 2、得到RunTimeService
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 3、启动流程实例，同时还要指定业务标识businessKey，也就是出差申请单id，这里是1001
		ProcessInstance processInstance = runtimeService.
			startProcessInstanceByKey("myEvection","1001");
		// 4、输出processInstance相关属性
		System.out.println("业务id=="+processInstance.getBusinessKey());
	}


}
