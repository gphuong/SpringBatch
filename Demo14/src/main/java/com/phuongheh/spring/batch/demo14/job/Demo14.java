package com.phuongheh.spring.batch.demo14.job;

import com.phuongheh.spring.batch.demo14.dto.EmployeeDTO;
import com.phuongheh.spring.batch.demo14.listener.ProcessListener;
import com.phuongheh.spring.batch.demo14.listener.ReaderListener;
import com.phuongheh.spring.batch.demo14.listener.WriterListener;
import com.phuongheh.spring.batch.demo14.mapper.EmployeeFileRowMapper;
import com.phuongheh.spring.batch.demo14.model.Employee;
import com.phuongheh.spring.batch.demo14.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
public class Demo14 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeProcessor employeeProcessor;
    private DataSource dataSource;

    @Autowired
    public Demo14(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessor employeeProcessor, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }

    @Qualifier(value = "demo14")
    @Bean
    public Job demo14Job() throws Exception {
        return this.jobBuilderFactory.get("demo14")
                .start(step1Demo14())
                .build();
    }

    @Bean
    public Step step1Demo14() throws Exception {
        return this.stepBuilderFactory.get("step1")
                .<EmployeeDTO, Employee>chunk(2)
                .reader(employeeReader())
                .processor(employeeProcessor)
                .writer(employeeDBWriterDefault())
                .faultTolerant().skipPolicy(skipPolicy())
                .listener(readerListener())
                .listener(processListener())
                .listener(writerListener())
                .build();
    }

    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
        return new ClassPathResource(fileName);
    }

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
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
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }

    @Bean
    public JobSkipPolicy skipPolicy() {
        return new JobSkipPolicy();
    }

    @Bean
    public ReaderListener readerListener(){
        return new ReaderListener();
    }

    @Bean
    public ProcessListener processListener(){
        return new ProcessListener();
    }

    @Bean
    public WriterListener writerListener(){
        return new WriterListener();
    }


}
