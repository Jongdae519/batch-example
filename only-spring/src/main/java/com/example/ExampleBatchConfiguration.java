package com.example;

import com.example.domain.SampleOne;
import com.example.domain.SampleTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.example.config", "com.example.reader", "com.example.processor", "com.example.writer"})
public class ExampleBatchConfiguration extends DefaultBatchConfiguration {

    @Bean
    public Job exampleJob(JobRepository jobRepository,
                          @Qualifier("exampleOneStep") Step exampleOneStep) {
        return new JobBuilder("exampleJob", jobRepository)
                .start(exampleOneStep)
                .build();
    }

    @Bean("exampleOneStep")
    public Step exampleOneStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               @Qualifier("stepOneReader") ItemReader<SampleOne> stepOneReader,
                               @Qualifier("stepOneProcessor") ItemProcessor<SampleOne, SampleTwo> stepOneProcessor,
                               @Qualifier("stepOneWriter") ItemWriter<SampleTwo> stepOneWriter) {
        return new StepBuilder("exampleOneStep", jobRepository)
                .<SampleOne, SampleTwo>chunk(10, transactionManager)
                .reader(stepOneReader)
                .processor(stepOneProcessor)
                .writer(stepOneWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JdbcTransactionManager transactionManager = new JdbcTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Override
    protected String getTablePrefix() {
        return "SCHEMA.BATCH_";
    }
}