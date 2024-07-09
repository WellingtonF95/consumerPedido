package org.consumerPedido.listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.consumerPedido.PedidoAvro;
import org.consumerPedido.config.KafkaConfig;
import org.consumerPedido.dto.ItemPedidoDTO;
import org.consumerPedido.dto.PedidoDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumerKafka implements AcknowledgingMessageListener<String, PedidoAvro> {


    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "group",
            containerFactory = KafkaConfig.KAFKA_CONTAINER_FACTORY)
    public void onMessage(ConsumerRecord<String, PedidoAvro> data, Acknowledgment acknowledgment) {
        try {
            PedidoDTO dto = convertToDTO(data.value());
            System.out.println("Pedido Recebido do tópico pedidos!");
            System.out.println("Pedido Recebido!");
            System.out.println("----------------");
            System.out.println("Id do cliente: " + dto.getCliente());
            System.out.println("Total: " + dto.getTotal());
            dto.getItems().forEach(item -> {
                System.out.println("Id do produto: " + item.getProduto());
                System.out.println("Quantidade do produto: " + item.getQuantidade());
            });
            System.out.println("Documento: " + dto.getDocumento());
            System.out.println("Funcionario: " + dto.getFuncionario());
            System.out.println("----------------");

        } finally {
            acknowledgment.acknowledge();
        }
    }

    private PedidoDTO convertToDTO(PedidoAvro avro) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(avro.getCliente());
        pedidoDTO.setTotal(BigDecimal.valueOf(avro.getTotal()));
        pedidoDTO.setDocumento(Objects.nonNull(avro.getDocumento()) ? avro.getDocumento().toString() : null);
        pedidoDTO.setFuncionario(Objects.nonNull(avro.getFuncionario()) ? avro.getFuncionario().toString() : null);
        pedidoDTO.setNumeroPedido(Objects.nonNull(avro.getNumeroPedido()) ? avro.getNumeroPedido().toString() : null);

        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
        itemPedidoDTO.setProduto(avro.getProduto());
        itemPedidoDTO.setQuantidade(avro.getQuantidade());

        List<ItemPedidoDTO> items = new ArrayList<>();
        items.add(itemPedidoDTO);

        pedidoDTO.setItems(items);

        return pedidoDTO;
    }
}
