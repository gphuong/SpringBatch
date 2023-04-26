package com.phuongheh.batch.demo5.job;

import com.phuongheh.batch.demo5.dto.EmployeeDTO;
import com.phuongheh.batch.demo5.mapper.EmployeeDBRowMapper;
import com.phuongheh.batch.demo5.mapper.EmployeeFileRowMapper;
import com.phuongheh.batch.demo5.model.Employee;
import com.phuongheh.batch.demo5.processor.EmployeeProcessor;
import com.phuongheh.batch.demo5.writer.EmailSenderWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class Demo5 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeProcessor employeeProcessor;
    private DataSource dataSource;

    @Autowired
    public Demo5(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    private Resource outputResource = new FileSystemResource("output/employee_output.csv");

    @Qualifier(value = "demo5")
    @Bean
    public Job demo5Job() {
        return this.jobBuilderFactory.get("demo5")
                .start(step1Demo5())
                .next(step2Demo5())
                .build();
    }

    private Step step2Demo5() {
        return this.stepBuilderFactory.get("step2")
                .<Employee, EmployeeDTO>chunk(10)
                .reader(employeeDBReader())
                .writer(emailSenderWriter())
                .build();
    }

    @Bean
    ItemWriter<? super EmployeeDTO> emailSenderWriter() {
        return new EmailSenderWriter();
    }

    @Bean
    public ItemStreamReader<Employee> employeeDBReader() {
        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("select * from employee");
        reader.setRowMapper(new EmployeeDBRowMapper());
        return reader;
    }

    @Bean
    public Step step1Demo5() {
        return this.stepBuilderFactory.get("step1")
                .<EmployeeDTO, Employee>chunk(10)
                .reader(employeeReader())
                .writer(employeeDBWriterDefault())
                .processor(employeeProcessor)
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
    public FlatFileItemReader<? extends EmployeeDTO> employeeReader() {
        FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
        reader.setResource(inputFileResource(null));
        reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("employeeId", "firstName", "lastName", "email", "age");
                setFieldSetMapper(new EmployeeFileRowMapper());
            }});
        }});
        return reader;
    }

    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }
}
