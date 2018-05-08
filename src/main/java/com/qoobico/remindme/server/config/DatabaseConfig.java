package com.qoobico.remindme.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.*;

@Configuration
@EnableJpaRepositories("com.qoobico.remindme.server")
@EnableTransactionManagement
@ComponentScan("com.qoobico.remindme.server")
@PropertySource("classpath:db.properties")
public class DatabaseConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());

        return em;
    }

    @Bean
    public DataSource dataSource(){

        BasicDataSource bds = new BasicDataSource();
        bds.setUrl(env.getRequiredProperty("db.url"));
        bds.setDriverClassName(env.getRequiredProperty("db.driver"));
        bds.setUsername(env.getRequiredProperty("db.username"));
        bds.setPassword(env.getRequiredProperty("db.password"));

        bds.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
        bds.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
        bds.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
        bds.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        bds.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        bds.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
        bds.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return bds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());

        return manager;
    }

    public Properties getHibernateProperties(){
        Properties properties = new Properties();

        try {
            Class.forName("org.postgresql.Driver");
            //on classpath
        } catch(ClassNotFoundException e) {
            System.out.println("org.postgresql.Driver not on classpath\n");
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
        try{
            properties.load(is);
            return properties;
        }
        catch (IOException ex){
            throw new IllegalArgumentException("Can not find 'hibernate.properties' in classpath", ex);
        }
    }
}
