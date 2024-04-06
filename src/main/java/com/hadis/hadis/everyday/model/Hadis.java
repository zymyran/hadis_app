package com.hadis.hadis.everyday.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_hadisses")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hadis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;
}
