package me.saro.example.springbatch;

import me.saro.commons.Converter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
@EnableScheduling
public class CronConfiguration {

    @Autowired ApplicationContext context;
    @Autowired JobLauncher jobLauncher;

    // 10분에 한번씩
    @Scheduled(cron = "0 0/10 * * * ?")
    public void step() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Converter.toJson(jobLauncher.run((Job)context.getBean("stepTestJob"), new JobParameters(new LinkedHashMap<String, JobParameter>(){{ put("key", new JobParameter(System.currentTimeMillis())); }})).getExitStatus());
    }

    // 5분부터 10분에 한번씩
    @Scheduled(cron = "0 5/10 * * * ?")
    public void person() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Converter.toJson(jobLauncher.run((Job)context.getBean("importUserJob"), new JobParameters(new LinkedHashMap<String, JobParameter>(){{ put("key", new JobParameter(System.currentTimeMillis())); }})).getExitStatus());
    }
}
