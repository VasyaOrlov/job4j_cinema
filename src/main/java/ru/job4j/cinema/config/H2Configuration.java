package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class H2Configuration {

    /**
     * loadPool - метод для загрузки в контекст пула переиспользуемых соединений для тестов
     * @return - пул соединений
     */
    public BasicDataSource loadPool() {
        Properties properties = loadProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(properties.getProperty("driver"));
        pool.setUrl(properties.getProperty("url"));
        pool.setUsername(properties.getProperty("username"));
        pool.setPassword(properties.getProperty("password"));
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
        try (InputStream in = new FileInputStream("db/db_test.properties")) {
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return properties;
    }
}
