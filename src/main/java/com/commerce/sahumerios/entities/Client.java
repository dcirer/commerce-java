package com.commerce.sahumerios.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode

public class Client {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @NotNull(message = "El nombre no puede ser nulo") @Getter @Setter private String name;
    @NotNull(message = "El apellido no puede ser nulo") @Getter @Setter private String lastname;
    @NotNull(message = "El número de documento no puede ser nulo") @Getter @Setter private Integer docnumber;

    //relacion un cliente puede tener muchas facturas.
    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Getter @Setter private List<Invoice> invoices;

    //relacion un cliente puede tener muchos carritos
    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Getter @Setter private List<Cart> carts;


}
