package no.solea.cargodispatcher.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    private Long id;
    private String name;
    private Double speed;
    private Integer capacity;
}
