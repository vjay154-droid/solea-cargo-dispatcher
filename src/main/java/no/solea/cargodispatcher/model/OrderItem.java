package no.solea.cargodispatcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItem {
    private Long productId;
    private Integer quantity;
}
