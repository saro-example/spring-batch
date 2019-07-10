package me.saro.example.springbatch.step;

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
public class StepBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepTestJob() {
        return jobBuilderFactory.get("stepTestJob")
                .listener(new JobLogListener())
                .start(stepTestJobStep1())
                .next(stepTestJobStep2())
                .next(stepTestJobStep3())
                .next(stepTestJobStep4())
                .build();
    }

    @Bean
    public Step stepTestJobStep1() {
        return stepBuilderFactory
                .get("StepBuilder 1")
                .tasklet(new Task("step 1"))
                .build();
    }

    @Bean
    public Step stepTestJobStep2() {
        return stepBuilderFactory
                .get("StepBuilder 2")
                .tasklet(new Task("step 2"))
                .build();
    }

    @Bean
    public Step stepTestJobStep3() {
        return stepBuilderFactory
                .get("StepBuilder 3")
                .tasklet(new Task("step 3"))
                .build();
    }

    @Bean
    public Step stepTestJobStep4() {
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