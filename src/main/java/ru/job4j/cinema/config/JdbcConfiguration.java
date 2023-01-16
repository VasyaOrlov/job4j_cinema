package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Properties;

/**
 * JdbcConfiguration - конфигурационный класс приложения.
 * Configuration - аннотация для спринг для обозначения конфирационного класса
 */
@Configuration
public class JdbcConfiguration {

    /**
     * loadPool - метод для загрузки в контекст пула переиспользуемых соединений
     * Bean - указывает Spring загрузить объект BasicDataSource в контекст
     * @return - пул соединений
     */
    @Bean
    public BasicDataSource loadPool() {
        Properties properties = loadProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(properties.getProperty("jdbc.driver"));
        pool.setUrl(properties.getProperty("jdbc.url"));
        pool.setUsername(properties.getProperty("jdbc.username"));
        pool.setPassword(properties.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    /**
     * loadProperties - метод для загрузки настроек в служебный класс Properties
     * @return Properties с настройками в виде ключ-значение
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream("db/db.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return properties;
    }
}
