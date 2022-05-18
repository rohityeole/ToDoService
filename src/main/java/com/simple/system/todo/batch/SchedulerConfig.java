
package com.simple.system.todo.batch;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	@Scheduled(fixedDelay = 100000, initialDelay = 200000)
	public void scheduleByFixedRate() throws Exception {
		log.info("Batch job starting");
		JobParameters jobParameters = new JobParametersBuilder().addString("time", LocalDate.now().toString())
				.toJobParameters();
		jobLauncher.run(job, jobParameters);
		log.info("Batch job executed successfully\n");
	}
}