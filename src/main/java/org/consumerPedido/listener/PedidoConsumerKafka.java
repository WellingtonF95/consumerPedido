package org.consumerPedido.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.consumerPedido.config.KafkaConfig;
import org.consumerPedido.dto.PedidoDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumerKafka implements AcknowledgingMessageListener<String, PedidoDTO> {


    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "group",
            containerFactory = KafkaConfig.KAFKA_CONTAINER_FACTORY)
    public void onMessage(ConsumerRecord<String, PedidoDTO> data, Acknowledgment acknowledgment) {
        try {
            PedidoDTO dto = data.value();
            System.out.println("Pedido via Kafka recebido!");
            System.out.println("Pedido Recebido!");
            System.out.println("----------------");
            System.out.println(dto.getCliente());
            System.out.println(dto.getTotal());
            dto.getItems().forEach(item -> {
                System.out.println(item.getProduto());
                System.out.println(item.getQuantidade());
            });
            System.out.println("----------------");

        } catch (RuntimeException e) {
            throw e;
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
