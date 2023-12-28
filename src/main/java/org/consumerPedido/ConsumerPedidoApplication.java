package org.consumerPedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ConsumerPedidoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
       SpringApplication.run(ConsumerPedidoApplication.class, args);
    }
}