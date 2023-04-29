package com.phuongheh.spring.batch.demo9.job;

import com.phuongheh.spring.batch.demo9.dto.EmployeeDTO;
import com.phuongheh.spring.batch.demo9.handler.SkipRecordCallback;
import com.phuongheh.spring.batch.demo9.mapper.EmployeeFileRowMapper;
import com.phuongheh.spring.batch.demo9.model.Employee;
import com.phuongheh.spring.batch.demo9.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class Demo9 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeProcessor employeeProcessor;
    private DataSource dataSource;

    @Autowired
    public Demo9(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    @Qualifier(value = "demo9")
    @Bean
    public Job demo9Job() {
        return this.jobBuilderFactory.get("demo9")
                .start(step1Demo9())
                .build();
    }

    @Bean
    public Step step1Demo9() {
        return this.stepBuilderFactory.get("step1")
                .<EmployeeDTO, Employee>chunk(2)
                .reader(employeeReader())
                .processor(employeeProcessor)
                .writer(employeeDBWriterDefault())
                .build();
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
        reader.setLinesToSkip(1);
        reader.setSkippedLinesCallback(new SkipRecordCallback());
        reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {{
            setLineTokenizer(new FixedLengthTokenizer() {{
                setNames("employeeId", "firstName", "lastName", "email", "age");
                setColumns(new Range[]{new Range(1, 5), new Range(6, 10), new Range(11, 15), new Range(16, 30), new Range(31, 33)});
                setStrict(false);
            }});
            setFieldSetMapper(new EmployeeFileRowMapper());
        }});
        return reader;
    }

    @Bean
    @StepScope
    public Resource inputFileResource(@Value("#{jobParameters[fileName]}") String fileName) {
        return new ClassPathResource(fileName);
    }
}
