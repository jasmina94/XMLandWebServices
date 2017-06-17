package com.ftn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Created by Olivera on 17.6.2017..
 */
@Entity
@Data
@NoArgsConstructor
public class PoslovniPartner {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false, length = 9, unique = true)
    private long pib;

    private String adresa;

    @ManyToOne(optional = false)
    private Firma firma;

    @ManyToOne
    private Lokacija lokacija;

}
