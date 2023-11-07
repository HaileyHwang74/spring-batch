package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DBJobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step1, Step step2){
        return new JobBuilder("job",jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }
    @Bean
    public Step step1(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(tasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Step step2(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager platformTransactionManager) {
        System.out.println("step1 was executed.");
        return new StepBuilder("step2", jobRepository)
                .tasklet(tasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("테스트1");

            return RepeatStatus.FINISHED;
        });
    }

}
