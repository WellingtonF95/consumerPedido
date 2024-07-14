package org.consumerPedido.listener;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.consumerPedido.constants.RabbitConstants;
import org.consumerPedido.dto.PedidoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @Autowired
    protected ObjectMapper objectMapper;


    @RabbitListener(queues = RabbitConstants.QUEUE_PEDIDO)
    public void recebePedido(Message message) throws IOException {
        PedidoDTO pedido = convertMessage(message, PedidoDTO.class);
        System.out.println("Pedido Recebido!");
        System.out.println("----------------");
        System.out.println(pedido.getCliente());
        System.out.println(pedido.getTotal());
        pedido.getItems().forEach(item -> {
            System.out.println(item.getProduto());
            if(item.getQuantidade() >= 5) {
                throw new RuntimeException("Erro ao processar pedido. Quantidade acima de 5 unidades para o produto: "
                        + item.getProduto());
            }
            System.out.println(item.getQuantidade());
        });
        System.out.println("----------------");

    }

    private <T> T convertMessage(Message message, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(message.getBody(), clazz);
        } catch (IOException e) {
            System.out.println("Erro ao converter a mensagem: " + e.getMessage());
            throw e;
        }
    }
}
