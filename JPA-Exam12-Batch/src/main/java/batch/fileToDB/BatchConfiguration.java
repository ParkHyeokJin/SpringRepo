package batch.fileToDB;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.validation.BindException;

import batch.dbToDB.JobCompletionNotificationListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job job(JobCompletionNotificationListener listener, Step step) {
        return jobBuilderFactory.get("myJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step)
            .end()
            .build();
    }

    @Bean
    public Step step(JpaItemWriter<Person> jpaWriter) throws Exception {
        return stepBuilderFactory.get("step")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(new PersonAvgProcess())
            .writer(jpaWriter)
            .build();
    }

	@Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
            .name("PersonReader")
            .resource(new ClassPathResource("sample.txt"))
            .delimited()
            .delimiter(",")
            .names(new String[] {"name", "studentId", "korean", "english", "math"})
            .fieldSetMapper(new FieldSetMapper<Person>() {
				@Override
				public Person mapFieldSet(FieldSet fieldSet) throws BindException {
					String name = fieldSet.readString(0);
					String studentId = fieldSet.readString(1);
					int korean = fieldSet.readInt(2);
					int english = fieldSet.readInt(3);
					int math = fieldSet.readInt(4);
					return new Person(name, studentId, korean, english, math, 0);
				}
			})
            .build();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO PERSON (name, student_Id, korean, english, math, avg) VALUES (:name, :studentId, :korean, :english, :math, :avg)")
            .dataSource(dataSource)
            .build();
    }
    
    
    private final EntityManagerFactory entityManagerFactory;
    
    @Bean
    public JpaItemWriter<Person> jpaWriter(DataSource dataSource){
    	JpaItemWriter<Person> jpaItemWriter = new JpaItemWriter<Person>();
    	jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    	return jpaItemWriter;
    }
}
