package com.commerce.sahumerios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode

public class Client {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String lastname;

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Invoice> invoices;

    @OneToMany (mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Cart> carts;


}
