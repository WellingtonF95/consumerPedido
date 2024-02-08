package org.consumerPedido.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String X_DEAD_LETTER_EXCHANGE = "x_dead_letter_exchange";

    public static final String X_DEAD_LETTER_ROUTING_KEY = "x_dead_letter_routing_key";
    public static final String EXCHANGE_PEDIDO_DLX = "exchange.pedido.dlx";
    public static final String EXCHANGE_PEDIDO = "exchange.pedido";
    public static final String QUEUE_PEDIDO = "queue_pedido";
    public static final String QUEUE_PEDIDO_DLQ = "queue_pedido_dlq";

    public AmqpAdmin amqpAdmin;

    public RabbitMQConfig(final AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put(X_DEAD_LETTER_EXCHANGE, EXCHANGE_PEDIDO_DLX);
        Queue queue = new Queue(QUEUE_PEDIDO, true, false,false, args);
        queue.setAdminsThatShouldDeclare(amqpAdmin);
        return queue;
    }

    @Bean
    public Queue queuePedidoDlq() {
        Queue queueDlq = new Queue(QUEUE_PEDIDO_DLQ, true, false, false);
        queueDlq.setAdminsThatShouldDeclare(amqpAdmin);
        return queueDlq;
    }

    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange(EXCHANGE_PEDIDO);
        directExchange.setAdminsThatShouldDeclare(amqpAdmin);
        return directExchange;
    }

    @Bean
    public DirectExchange directExchangeDLX() {
        DirectExchange exchange = new DirectExchange(EXCHANGE_PEDIDO_DLX);
        exchange.setAdminsThatShouldDeclare(amqpAdmin);
        return exchange;
    }

    @Bean
    public Binding bindingQueuePedido() {
        Binding binding = BindingBuilder.bind(queue())
                .to(directExchange())
                .with(X_DEAD_LETTER_ROUTING_KEY);
        binding.setAdminsThatShouldDeclare(amqpAdmin);
        return binding;
    }

    @Bean
    public Binding bindingQueuePedidoDlq() {
        Binding binding = BindingBuilder.bind(queuePedidoDlq())
                .to(directExchangeDLX())
                .with(X_DEAD_LETTER_EXCHANGE);
        binding.setAdminsThatShouldDeclare(amqpAdmin);
        return binding;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
