package com.epam.esm.config;

import com.epam.esm.entity.BaseEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.epam.esm.dao")
public class RepositoryConfig {

    @Bean(name = "dataSource")
    @Profile("prod")
    public DataSource prodDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/gift_certificate_system?serverTimezone=Europe/Moscow");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
//        dataSource.setJdbcUrl("jdbc:mysql://amazon-database-1.cj7cc7ajgdgs.us-east-2.rds.amazonaws.com:3306/gift_certificate_system?serverTimezone=Europe/Moscow");
//        dataSource.setUsername("admin");
//        dataSource.setPassword("admin123");


//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/gift_certificate_system?serverTimezone=Europe/Moscow");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("admin");
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("dev")
    public DataSource devDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2://localhost:3306/gift_certificate_system?serverTimezone=Europe/Moscow");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    @Profile("prod")
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
//        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);

        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @Bean
    @Profile("prod")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setPackagesToScan(BaseEntity.class.getPackage().getName());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setJpaProperties(hibernateProperties());

        return emf;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.highlight_sql", "true");
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");

        return hibernateProperties;
    }

}
