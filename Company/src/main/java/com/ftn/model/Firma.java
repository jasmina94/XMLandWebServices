package com.ftn.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Olivera on 17.6.2017..
 */
@Entity
@Data
@NoArgsConstructor
public class Firma {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false, length = 9, unique = true)
    private long pib;

    private String adresa;

    @ManyToOne
    private Lokacija lokacija;

    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<PoslovniPartner> poslovniPartneri = new ArrayList<>();

    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Zaposleni> zaposleni = new ArrayList<>();
}

