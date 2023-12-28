package org.consumerPedido.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ItemPedidoDTO implements Serializable {

    private Integer produto;
    private Integer quantidade;

}
