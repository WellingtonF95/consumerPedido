package org.consumerPedido.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Data
public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;

}
