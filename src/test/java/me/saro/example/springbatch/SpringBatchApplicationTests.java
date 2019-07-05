package me.saro.example.springbatch;

import lombok.extern.slf4j.Slf4j;
import me.saro.example.springbatch.person.PersonBatchConfiguration;
import me.saro.example.springbatch.person.PersonJobCompletionNotificationListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableBatchProcessing
@ContextConfiguration(classes = { PersonBatchConfiguration.class, PersonJobCompletionNotificationListener.class })
@Slf4j
public class SpringBatchApplicationTests {

	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void contextLoads() throws Exception {
		System.out.println("ready");
		System.out.println(jobLauncherTestUtils);
		jobLauncherTestUtils.launchJob();
		System.out.println("started");
	}
}
