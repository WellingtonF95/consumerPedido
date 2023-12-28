package org.consumerPedido.dto;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
public class PedidoDTO implements Serializable {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;

}
