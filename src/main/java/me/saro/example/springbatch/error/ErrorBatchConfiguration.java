package me.saro.example.springbatch.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@Slf4j
public class ErrorBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepTestErrorJob() {
        return jobBuilderFactory.get("stepTestErrorJob")
                .listener(new JobLogListener())
                .start(stepTestErrorJobStep1())
                .next(stepTestErrorJobStep2())
                .next(stepTestErrorJobStep3())
                .next(stepTestErrorJobStep4())
                .build();
    }

    @Bean
    public Step stepTestErrorJobStep1() {
        return stepBuilderFactory
                .get("StepBuilder 1")
                .tasklet(new Task("step 1"))
                .build();
    }

    @Bean
    public Step stepTestErrorJobStep2() {
        return stepBuilderFactory
                .get("StepBuilder 2")
                .tasklet(new Task("step 2"))
                .build();
    }

    @Bean
    public Step stepTestErrorJobStep3() {
        return stepBuilderFactory
                .get("StepBuilder 3")
                .tasklet(new Task("step 3"))
                .build();
    }

    @Bean
    public Step stepTestErrorJobStep4() {
        return stepBuilderFactory
                .get("StepBuilder 4")
                .tasklet(new Task("step 4"))
                .build();
    }

    public static class Task implements Tasklet, StepExecutionListener {

        final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            log.info("[" + name + "] execute");

            if (name.equals("step 2")) {
                // ERROR STEP 2
                int a = 1 / 0;
            }

            return RepeatStatus.FINISHED;
        }


        @Override
        public void beforeStep(StepExecution stepExecution) {
            log.info("[" + name + "] before");
            log.info(stepExecution.toString());
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            log.info("[" + name + "] after");
            log.info(stepExecution.toString());
            return ExitStatus.COMPLETED;
        }
    }

    public static class JobLogListener implements JobExecutionListener {

        @Override
        public void beforeJob(JobExecution jobExecution) {
            log.info("job before --- info");
            log.info(jobExecution.toString());
            log.info("job before --- end");
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            log.info("job after --- info");
            log.info(jobExecution.toString());
            log.info("job after --- end");
        }
    }
}