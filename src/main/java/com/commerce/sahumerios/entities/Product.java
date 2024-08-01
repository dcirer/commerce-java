package com.commerce.sahumerios.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa un producto")
public class Product {
    @Schema(description = "Identificador unico del producto", example = "5")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Schema(description = "Nombre del producto", example = "Aritos")
    @NotNull(message = "La descripción no puede ser nula")@Getter @Setter private String description;
    @Schema(description = "Cantidad de stock", example = "234")
    @NotNull(message = "El stock no puede ser nulo")@Getter @Setter private Integer stock;
    @Schema(description = "Precio del producto", example = "1231.23")
    @NotNull(message = "El precio no puede ser nulo")@Getter @Setter private double price;

    //relacion un producto puede estar en varios carritos.
    @Schema(description = "Lista de carritos que contienen este producto. Esta lista puede incluir múltiples carritos que tienen el producto en cuestión.",example = "4")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter @Setter private List<Cart> carts;
}
