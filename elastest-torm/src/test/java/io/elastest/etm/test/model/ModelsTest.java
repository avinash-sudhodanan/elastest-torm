package io.elastest.etm.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import io.elastest.etm.model.ExternalJob;
import io.elastest.etm.model.Parameter;
import io.elastest.etm.model.Project;
import io.elastest.etm.model.SutExecution;
import io.elastest.etm.model.SutExecution.DeployStatusEnum;
import io.elastest.etm.model.SutSpecification;
import io.elastest.etm.model.TJob;
import io.elastest.etm.model.TJobExecution;
import io.elastest.etm.model.TJobExecution.ResultEnum;
import io.elastest.etm.model.TestCase;
import io.elastest.etm.model.TestSuite;

@RunWith(JUnitPlatform.class)
public class ModelsTest {

	@Test
	public void tTobTest(){
		
		Project project = new Project(3l, "name", new ArrayList<TJob>(), new ArrayList<SutSpecification>());
		Project project2 = new Project(3l, "name", new ArrayList<TJob>(), new ArrayList<SutSpecification>());
				
		SutSpecification sut = new SutSpecification(34l,"name","specification", null,"description",project, new ArrayList<>());
		SutSpecification sut2 = new SutSpecification(34l,"name","specification", null, "description",project2, new ArrayList<>());
		
		TJob tjob = new TJob(34l, "name", "imageName", sut, project, false, "execDashboardConfig");
		TJob tjob2 = new TJob(34l, "name", "imageName", sut2, project2, false, "execDashboardConfig");
		
		assertEquals(tjob, tjob2);
		assertEquals(tjob.hashCode(), tjob2.hashCode());
	}
	
	@Test
	public void executionTest(){
		
		Project project = new Project(3l, "name", new ArrayList<TJob>(), new ArrayList<SutSpecification>());
		SutSpecification sut = new SutSpecification(34l,"name","specification", null, "description",project, new ArrayList<>());
		TJob tjob = new TJob(34l, "name", "imageName", sut, project, false, "execDashboardConfig");
		
		List<Parameter> params = new ArrayList<>();
		params.add(new Parameter(45l,"param1", "value1", tjob, null));
		tjob.setParameters(params);
		
		TJobExecution exec = new TJobExecution(45l, 34543534l, ResultEnum.FINISHED);
		List<Parameter> paramsExec = new ArrayList<>();
		paramsExec.add(new Parameter(45l,"param1", "value1", tjob, exec));
		exec.setParameters(paramsExec);
		
		TJobExecution exec2 = new TJobExecution(45l, 34543534l, ResultEnum.FINISHED);
		List<Parameter> paramsExec2 = new ArrayList<>();
		paramsExec2.add(new Parameter(45l,"param1", "value1", tjob, exec2));
		exec2.setParameters(paramsExec2);
				
		exec.setTjob(tjob);
		exec2.setTjob(tjob);
		
		SutExecution sutExec = new SutExecution(45l, sut, "ssss", DeployStatusEnum.DEPLOYED);
		SutExecution sutExec2 = new SutExecution(45l, sut, "ssss", DeployStatusEnum.DEPLOYED);
		
		exec.setSutExecution(sutExec);
		exec2.setSutExecution(sutExec2);
		
		assertEquals(exec, exec2);
		assertEquals(exec.hashCode(), exec2.hashCode());
	}
	
	@Test
	public void testCreateExternalJob(){
		ExternalJob extJob = new ExternalJob("Job1", "htt://localhost:8090", "http://localhost:8090", 1L, "9200", "192.168.1.1");
		assertTrue(extJob.getJobName().equals("Job1"));
	}
	
	@Test
	public void testEqualsExternalJobs(){
		ExternalJob extJob1 = new ExternalJob("Job1", "htt://localhost:8090", "http://localhost:8090", 1L, "9200", "192.168.1.1");
		ExternalJob extJob2 = new ExternalJob("Job1", "htt://localhost:8090", "http://localhost:8090", 1L, "9200", "192.168.1.1");
		assertTrue(extJob1.equals(extJob2));
	}
	
	@Test
	public void testCreateSutExecution(){
		SutExecution sutExec = new SutExecution(1L, new SutSpecification(), "http://localhost:8090", DeployStatusEnum.DEPLOYING);
		assertTrue(sutExec.getId() == 1L);
	}
	
	@Test
	public void testEqualsSutExecution(){
		SutExecution sutExec1 = new SutExecution(1L, new SutSpecification(), "http://localhost:8090", DeployStatusEnum.DEPLOYING);
		SutExecution sutExec2 = new SutExecution(1L, new SutSpecification(), "http://localhost:8090", DeployStatusEnum.DEPLOYING);
		assertTrue(sutExec1.equals(sutExec2));
	}
	
	@Test
	public void testEqualsTestCases(){
		TestCase testCase1 = new TestCase("", 100L, "", "", "", "", new TestSuite());
		TestCase testCase2 = new TestCase("", 100L, "", "", "", "", new TestSuite());
		
		assertTrue(!testCase1.equals(testCase2));
	}
	
	@Test
	public void testCreateTestSuit(){
		TestSuite testSuite = new TestSuite(1L, "", 100L, 0, 0, 0, 0, 0, new ArrayList<TestCase>());
		assertTrue(testSuite.getId() == 1L);
	}
	
	@Test
	public void testEqualsTestSuites(){
		TestSuite testSuite1 = new TestSuite(1L, "", 100L, 0, 0, 0, 0, 0, new ArrayList<TestCase>());
		TestSuite testSuite2 = new TestSuite(1L, "", 100L, 0, 0, 0, 0, 0, new ArrayList<TestCase>());
		
		assertTrue(testSuite1.equals(testSuite2));
	}	
}
