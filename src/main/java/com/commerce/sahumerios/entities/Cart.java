package com.commerce.sahumerios.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @NotNull(message = "La cantidad no puede ser nula")@Getter @Setter private Integer amoun;
    @NotNull(message = "El precio no puede ser nulo")@Getter @Setter private Double price;
    @ManyToOne
    @JoinColumn (name = "client_id")
    @Getter @Setter private Client client;

    @ManyToOne
    @JoinColumn (name = "product_id")
    @Getter @Setter private Product product;
}
