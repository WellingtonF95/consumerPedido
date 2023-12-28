package org.consumerPedido.listener;

import org.consumerPedido.constants.RabbitConstants;
import org.consumerPedido.dto.PedidoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = RabbitConstants.QUEUE_PEDIDO)
    private void recebePedido(PedidoDTO pedido) {
        System.out.println("Pedido Recebido!");
        System.out.println("----------------");
        System.out.println(pedido.getCliente());
        System.out.println(pedido.getTotal());
        System.out.println(pedido.getItems().get(0));
        System.out.println("----------------");
    }
}
