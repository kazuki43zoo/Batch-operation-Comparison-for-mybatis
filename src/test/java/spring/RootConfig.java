package spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@MapperScan( basePackages = "mybatisMappers", sqlSessionFactoryRef = "sqlSessionFactory")
public class RootConfig {

  @Bean
  PropertiesFactoryBean dataSourceProperties() {
    PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
    factoryBean.setLocation(new ClassPathResource("DataSourceRegister.properties"));
    return factoryBean;
  }

  @Bean
  public DataSource getDTS(Properties dataSourceProperties) throws Exception {
    return BasicDataSourceFactory.createDataSource(dataSourceProperties);
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
    return initializer;
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactory(DataSource dts) {
    SqlSessionFactoryBean fb=new SqlSessionFactoryBean();
    fb.setDataSource(dts);
    return fb;
  }

  @Bean
  public SqlSession simpleSqlSession(SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory, ExecutorType.SIMPLE);
  }

  @Bean
  public SqlSession batchSqlSession(SqlSessionFactory factory) {
    return new SqlSessionTemplate(factory, ExecutorType.BATCH);
  }

}


