package no.solea.cargodispatcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class Order {
    private Long id;
    private Long planetId;
    private Double travelTime;
    private Double totalVolume;
    private List<OrderItem> items;
    private Vehicle assignedVehicle;
}
