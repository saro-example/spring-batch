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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired ApplicationContext context;
    @Autowired JobLauncher jobLauncher;

    @RequestMapping("/{name}")
    String api(@PathVariable("name") String name) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        switch (Objects.requireNonNullElse(name, "")) {
            case "step" :
                return Converter.toJson(jobLauncher.run((Job)context.getBean("stepTestJob"), new JobParameters(new LinkedHashMap<String, JobParameter>(){{ put("key", new JobParameter(System.currentTimeMillis())); }})).getExitStatus());
            case "person" :
                return Converter.toJson(jobLauncher.run((Job)context.getBean("importUserJob"), new JobParameters(new LinkedHashMap<String, JobParameter>(){{ put("key", new JobParameter(System.currentTimeMillis())); }})).getExitStatus());
            case "error" :
                return Converter.toJson(jobLauncher.run((Job)context.getBean("stepTestErrorJob"), new JobParameters(new LinkedHashMap<String, JobParameter>(){{ put("key", new JobParameter(System.currentTimeMillis())); }})).getExitStatus());
        }
        return Converter.toJson(new HashMap<String, String>(){{ put("msg", "존재하지 않는 명령"); }});
    }

}
