package me.saro.example.springbatch;

import lombok.extern.slf4j.Slf4j;
import me.saro.example.springbatch.error.ErrorBatchConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableBatchProcessing
@ContextConfiguration(classes = { ErrorBatchConfiguration.class })
@Slf4j
public class ErrorTests {

	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void contextLoads() throws Exception {
		log.info("ready");
		var jobExecution = jobLauncherTestUtils.launchJob();
		log.info("started");

		log.info(jobExecution.toString());
		Assert.assertEquals(ExitStatus.FAILED.getExitCode(), jobExecution.getExitStatus().getExitCode());
	}
}
