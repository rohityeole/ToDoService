package com.simple.system.todo.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.simple.system.todo.entity.Item;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class StatusBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	CustomItemReader customItemReader;

	@Autowired
	CustomProcessor customProcessor;

	@Autowired
	CustomItemWriter customItemWriter;

	@Bean
	public Job createJob() {
		return jobBuilderFactory.get("StatusUpdateJob").incrementer(new RunIdIncrementer()).flow(createStep()).end()
				.build();
	}

	@Bean
	public Step createStep() {
		return stepBuilderFactory.get("Step1").<Item, Item>chunk(1).reader(customItemReader).processor(customProcessor)
				.writer(customItemWriter).build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
