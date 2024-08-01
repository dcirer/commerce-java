package com.commerce.sahumerios.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa un carrito")
public class Cart {
    @Schema(description = "identificador unico del carrito", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Schema(description = "Cantidad de producto", example = "22")
    @NotNull(message = "La cantidad no puede ser nula")@Getter @Setter private Integer amoun;
    @Schema(description = "Precio del producto", example = "2314.12")
    @NotNull(message = "El precio no puede ser nulo")@Getter @Setter private Double price;
    //se agrega is billed para diferenciar los carros que ya estan facturados de un cliente, para evitar duplicados.
    @Schema(description = "Esta facturado o no", example = "true")
    @Getter @Setter private boolean isBilled = false;

    //relacion muchos carritos para un cliente

    @Schema(description = "Identificador unico del cliente en el carrito", example = "4")
    @ManyToOne
    @JoinColumn (name = "client_id")
    @JsonBackReference
    @Getter @Setter private Client client;

    //relacion un producto puede estar en varios carritos.

    @Schema(description = "Identificador unico del producto en el carrito", example = "2")
    @ManyToOne
    @JoinColumn (name = "product_id")
    @JsonManagedReference
    @Getter @Setter private Product product;
}
