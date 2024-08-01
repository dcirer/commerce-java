package com.commerce.sahumerios.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Representa un cliente")
public class Client {
    @Schema(description = "Identificador unico de cliente", example = "2")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Schema(description = "Nombre del cliente", example = "domy")
    @NotNull(message = "El nombre no puede ser nulo") @Getter @Setter private String name;
    @Schema(description = "Apellido del cliente", example = "Cirer")
    @NotNull(message = "El apellido no puede ser nulo") @Getter @Setter private String lastname;
    @Schema(description = "Numero de documento del cliente", example = "23212222")
    @NotNull(message = "El número de documento no puede ser nulo") @Getter @Setter private Integer docnumber;

    //relacion un cliente puede tener muchas facturas.
    @Schema(description = "Lista de facturas asociadas al cliente. Esta lista puede contener múltiples facturas vinculadas al cliente en particular.", example = "2")
    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Getter @Setter private List<Invoice> invoices;

    //relacion un cliente puede tener muchos carritos
    @Schema(description = "Lista de carritos asociados al cliente. Esta lista puede contener múltiples carritos vinculados al cliente en particular.", example = "3")
    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Getter @Setter private List<Cart> carts;


}
