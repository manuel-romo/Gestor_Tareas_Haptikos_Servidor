package com.haptikos.gestor_tareas_haptikos_servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestorTareasHaptikosServidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestorTareasHaptikosServidorApplication.class, args);
    }

}
