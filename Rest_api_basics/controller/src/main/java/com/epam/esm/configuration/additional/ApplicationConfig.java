package com.epam.esm.configuration.additional;

;


///**
// * Created for JavaStudy.ru on 28.05.2016.
// * application-context.xml analogue
// */
//@Configuration
////@PropertySource(value = "classpath:util.properties") //<context:property-placeholder location=".." />
//@ComponentScan(basePackages = "com.epam.esm")
//@EnableScheduling //task:annotation-driven
//public class ApplicationConfig {
//
//    /**
//     * @PropertySource annotation does not automatically
//     * register a PropertySourcesPlaceholderConfigurer with Spring.
//     * So we need to initialize this bean.
//     */
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//    @Value("${jdbc.hsqldb.driverClass}")
//    private String driverClass;
//    @Value("${jdbc.hsqldb.url}")
//    private String jdbcUrl;
//    @Value("${jdbc.hsqldb.username}")
//    private String jdbcUserName;
//    @Value("${jdbc.hsqldb.password}")
//    private String jdbcPassword;
//
//    @Value("classpath:dbschema.sql")
//    private Resource dbschemaSqlScript;
//    @Value("classpath:test-data.sql")
//    private Resource testDataSqlScript;
//
//    /**
//     * <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
//     */
//    @Bean(name = "dataSource")
//    public DriverManagerDataSource getDriverManagerDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(jdbcUrl);
//        dataSource.setUsername(jdbcUserName);
//        dataSource.setPassword(jdbcPassword);
//        return dataSource;
//    }
//
//    /**
//     * <jdbc:initialize-database data-source="dataSource">
//     * initialize Embedded DataSource. Встроенная база данных
//     */
//    @Bean
//    public DataSourceInitializer dataSourceInitializer() {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(getDriverManagerDataSource());
//        initializer.setDatabasePopulator(getDatabasePopulator());
//        return initializer;
//    }
//
//    private DatabasePopulator getDatabasePopulator() {
//        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(dbschemaSqlScript);
//        populator.addScript(testDataSqlScript);
//        return populator;
//    }
//
//
//    /**
//     *  <bean id="restTemplate" class="org.springframework.web.client.RestTemplate"/>
//     */
//    @Bean(name = "restTemplate")
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
//
//}
