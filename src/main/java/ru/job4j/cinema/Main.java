package ru.job4j.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main - основной класс приложения
 * @SpringBootApplication - spring boot включит автоматическую настройку и отсканирует ресурсы в пакете
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/cinema");
    }
}
