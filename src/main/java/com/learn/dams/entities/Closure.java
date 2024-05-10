package com.learn.dams.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="closure")
@Getter
@Setter
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Closure {

    @Column(name="ID", nullable=false)
    @Id
//    @GeneratedValue(generator="VAC22227818A83E427CC05C49")
//    @org.hibernate.annotations.GenericGenerator(name="VAC22227818A83E427CC05C49", strategy="native")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;


    @ManyToOne
//    @JoinColumns({ @JoinColumn(name="parentID", referencedColumnName="ID") })
    @JoinColumn(name = "parentID")
    private Concept  parent;
    @ManyToOne
//    @JoinColumns({ @JoinColumn(name="parentID", referencedColumnName="ID") })
    @JoinColumn(name = "childID")
    private Concept  child;

    @Column(name="Level", nullable=false, length=11)
    private int level;
}
