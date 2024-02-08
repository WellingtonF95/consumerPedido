package org.consumerPedido.dto;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Data
public class PedidoDTO {

    private final Integer cliente;
    private final BigDecimal total;
    private final List<ItemPedidoDTO> items;

    public PedidoDTO(Integer cliente, BigDecimal total, List<ItemPedidoDTO> items) {
        this.cliente = cliente;
        this.total = total;
        this.items = items;
    }

    public PedidoDTO(PedidoDTOBuilder builder) {
        this.cliente = builder.cliente;
        this.total = builder.total;
        this.items = builder.items;
    }

    public static class PedidoDTOBuilder {
        private Integer cliente;
        private BigDecimal total;
        private List<ItemPedidoDTO> items;


        public PedidoDTOBuilder setCliente(Integer cliente) {
            this.cliente = cliente;
            return this;
        }

        public PedidoDTOBuilder setTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public PedidoDTOBuilder setItems(List<ItemPedidoDTO> items) {
            this.items = items;
            return this;
        }

        public PedidoDTO build() {
            return new PedidoDTO(this);
        }
    }

}
