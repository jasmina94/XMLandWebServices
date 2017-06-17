package com.ftn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivera on 17.6.2017..
 */
@Entity
@Data
@NoArgsConstructor
public class Lokacija {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private int ptt;

    @OneToMany(mappedBy = "lokacija", cascade = CascadeType.ALL)
    private List<Firma> firme = new ArrayList<>();

    @OneToMany(mappedBy = "lokacija", cascade = CascadeType.ALL)
    private List<PoslovniPartner> poslovniPartneri = new ArrayList<>();

    @OneToMany(mappedBy = "lokacija", cascade = CascadeType.ALL)
    private List<Zaposleni> zaposleni = new ArrayList<>();

}
