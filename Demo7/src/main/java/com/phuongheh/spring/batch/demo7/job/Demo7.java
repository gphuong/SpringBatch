package com.phuongheh.spring.batch.demo7.job;

import com.phuongheh.spring.batch.demo7.dto.EmployeeDTO;
import com.phuongheh.spring.batch.demo7.listener.Demo7Listener;
import com.phuongheh.spring.batch.demo7.mapper.EmployeeFileRowMapper;
import com.phuongheh.spring.batch.demo7.model.Employee;
import com.phuongheh.spring.batch.demo7.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class Demo7 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeProcessor employeeProcessor;
    private DataSource dataSource;

    @Autowired

    public Demo7(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    @Qualifier(value = "demo7")
    @Bean
    public Job demo7Job() {
        return this.jobBuilderFactory.get("demo7")
                .start(step1Demo7())
                .listener(demo7Listener())
                .build();
    }

    private JobExecutionListener demo7Listener() {
        return new Demo7Listener();
    }

    @Bean
    public Step step1Demo7() {
        return this.stepBuilderFactory.get("step1")
                .<EmployeeDTO, Employee>chunk(1)
                .reader(employeeReader())
                .processor(employeeProcessor)
                .writer(employeeDBWriterDefault())
                .faultTolerant().skipPolicy(skipPolicy())
                .build();
    }

    @Bean
    public JobSkipPolicy skipPolicy() {
        return new JobSkipPolicy();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }


    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> employeeReader() {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputFileResource(null));
        reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("employeeId", "firstName", "lastName", "email", "age");
                setDelimiter(",");
            }});
            setFieldSetMapper(new EmployeeFileRowMapper());
        }});
        return reader;
    }

    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }
}
