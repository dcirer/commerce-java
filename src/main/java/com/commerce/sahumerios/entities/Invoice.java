package com.commerce.sahumerios.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @NotNull(message = "La fecha y hora no pueden ser nulas")@Getter @Setter private LocalDateTime dateTime;
    @NotNull(message = "El total no puede ser nulo")@Getter @Setter private Double total;

    //relacion un cliente puede tener varias facturas.
    @ManyToOne @JoinColumn (name = "client_id")
    @JsonBackReference
    @Getter @Setter private Client client;

}
