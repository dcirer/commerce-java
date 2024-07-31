package com.commerce.sahumerios.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @NotNull(message = "La descripción no puede ser nula")@Getter @Setter private String description;
    @NotNull(message = "El stock no puede ser nulo")@Getter @Setter private Integer stock;
    @NotNull(message = "El precio no puede ser nulo")@Getter @Setter private double price;

    //relacion un producto puede estar en varios carritos.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter @Setter private List<Cart> carts;
}
