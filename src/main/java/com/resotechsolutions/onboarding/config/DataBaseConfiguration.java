package com.resotechsolutions.onboarding.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = {"com.resotechsolutions.onboarding.entity"},
        transactionManagerRef = "transactionManager"
)
public class DataBaseConfiguration {

    private final String ENTITY_PACKAGE = "com.resotechsolutions.onboarding.entity";

    @Autowired
    private Environment environment;

    private Properties mySqlJpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto","update");
//        properties.put("hibernate.show_sql","true");
        return properties;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    //dataSource
    @Bean
    @Primary
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setUrl(environment.getProperty("first.datasource.url"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("first.datasource.driver-class-name"));
        driverManagerDataSource.setUsername(environment.getProperty("first.datasource.username"));
        driverManagerDataSource.setPassword(environment.getProperty("first.datasource.password"));

        return driverManagerDataSource;
    }

    //entityManagerFactory
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
        localContainerEntityManagerFactoryBean.setJpaProperties(mySqlJpaProperties());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(getJpaVendorAdapter());

        return localContainerEntityManagerFactoryBean;
    }

    //platformTransactionManager
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }
}
