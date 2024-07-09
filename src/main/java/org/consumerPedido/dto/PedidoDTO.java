package org.consumerPedido.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.avro.reflect.AvroDefault;

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
    private String documento;
    private String funcionario;
    private String numeroPedido;

    public PedidoDTO(Integer cliente, BigDecimal total, List<ItemPedidoDTO> items, String documento, String funcionario, String numeroPedido) {
        this.cliente = cliente;
        this.total = total;
        this.items = items;
        this.documento = documento;
        this.funcionario = funcionario;
        this.numeroPedido = numeroPedido;
    }
}
