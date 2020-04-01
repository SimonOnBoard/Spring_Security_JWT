package ru.itis.servlets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
@Configuration
public class ApplicationContextConfig {

    @Autowired
    private Environment environment;

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver
                = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(999999999);
        return multipartResolver;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig(FreeMarkerConfigurationFactoryBean configurationFactoryBean) {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(Objects.requireNonNull(configurationFactoryBean.getObject()));
        return freeMarkerConfigurer;
    }

    @Bean
    public freemarker.template.Configuration configuration(FreeMarkerConfigurationFactoryBean configurationFactoryBean) {
        return configurationFactoryBean.getObject();
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean freeMarkerFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerFactoryBean.setTemplateLoaderPaths("/WEB-INF/ftl/");
        freeMarkerFactoryBean.setPreferFileSystemAccess(true);
        return freeMarkerFactoryBean;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        config.setUsername(environment.getProperty("spring.datasource.username"));
        config.setPassword(environment.getProperty("spring.datasource.password"));
        config.setDriverClassName(environment.getProperty("db.driver"));
        return config;
    }

    @Bean
    public DataSource hikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Map<String, String> templateParameters() {
        Map<String, String> model = new HashMap<>();
        model.put("location", environment.getProperty("location"));
        model.put("signature", environment.getProperty("link"));
        return model;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("spring.mail.port")));

        mailSender.setUsername(environment.getProperty("spring.mail.username"));
        mailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", environment.getProperty("spring.mail.protocol"));
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));

        return mailSender;
    }

    @Bean
    public ExecutorService executorService() {
        int i = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(i);
        return executorService;
    }

    @Bean
    public String filePath() {
        return environment.getProperty("file.path");
    }

    @Bean
    public String serverAddress() {
        return environment.getProperty("server.address");
    }

    @Bean
    public String serverConfirmationAddress() {
        return environment.getProperty("server.confirmation.address");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
