package com.xxl.job.executor.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infincash.cron.collection.CronJobHandler;
import com.xxl.job.executor.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class ,webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobExecutorExampleBootApplicationTests {

	@Autowired
	CronJobHandler jobHandler;
	
	@Test
	public void test() throws Exception {
		jobHandler.execute("");
	}

}