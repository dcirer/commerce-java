package com.commerce.sahumerios.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa una factura")
public class Invoice {
    @Schema(description = "Identificador unico de la factura", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Schema(description = "Fecha de la factura", example = "2024/4/12")
    @NotNull(message = "La fecha y hora no pueden ser nulas")@Getter @Setter private LocalDateTime dateTime;
    @Schema(description = "Total de la factura, se obtiene del resultado de multiplicar la cantidad y valor del producto en el carrito", example = "12312.12")
    @NotNull(message = "El total no puede ser nulo")@Getter @Setter private Double total;

    //relacion un cliente puede tener varias facturas.
    @Schema(description = "Cliente al que está asociado esta factura. Cada factura está vinculado a un solo cliente.", example = "2")
    @ManyToOne @JoinColumn (name = "client_id")
    @JsonBackReference
    @Getter @Setter private Client client;

}
