package org.consumerPedido.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.consumerPedido.constants.RabbitConstants;
import org.consumerPedido.dto.PedidoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PedidoConsumer {

    @Autowired
    protected ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConstants.QUEUE_PEDIDO)
    private void recebePedido(Message message) throws IOException {
        PedidoDTO pedido = convertMessage(message, PedidoDTO.class);

        System.out.println("Pedido Recebido!");
        System.out.println("----------------");
        System.out.println("Cliente: " + pedido.getCliente());
        System.out.println("Total: " + pedido.getTotal());
        pedido.getItems().forEach(item -> {
            System.out.println("----------------");
            if(item.getQuantidade() >= 5) {
                throw new RuntimeException("Quantidade acima de 5 unidades para o produto: " + item.getProduto());
            }
            System.out.println("Produto: " + item.getProduto());
            System.out.println("Quantidade: " + item.getQuantidade());
            System.out.println("----------------");
        });

        System.out.println("----------------");

    }

    protected <T> T convertMessage(Message message, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(message.getBody(), clazz);
        } catch (IOException e) {
            System.out.println("Erro ao converter a mensagem: " + e.getMessage());
            throw e;
        }
    }
}
