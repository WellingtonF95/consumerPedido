package org.consumerPedido.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class PedidoDTO implements Serializable {

    private  Integer cliente;
    private  BigDecimal total;
    private  List<ItemPedidoDTO> items;

    public PedidoDTO(Integer cliente, BigDecimal total, List<ItemPedidoDTO> items) {
        this.cliente = cliente;
        this.total = total;
        this.items = items;
    }
}
