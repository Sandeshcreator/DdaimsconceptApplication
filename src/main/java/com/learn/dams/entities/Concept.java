package com.learn.dams.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
 @Table(name="concept")

@NoArgsConstructor
@Getter
@Setter
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name="Identifier", nullable=true, length=255)

    private String identifier;

    @Column(name="Label", nullable=true)
    private String label;

    @Column(name="Active", nullable=false, length=1)
    private boolean active = true;
}
