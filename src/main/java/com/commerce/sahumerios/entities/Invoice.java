package com.commerce.sahumerios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Getter @Setter private LocalDateTime dateTime;
    @Getter @Setter private Double total;

    @ManyToOne @JoinColumn (name = "client_id")
    @Getter @Setter private Client client;

}
