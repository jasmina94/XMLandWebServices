package com.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zlatan on 6/24/17.
 */
@Data
@Entity
@NoArgsConstructor
public class AnalitikaIzvoda {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date datumNaloga;

    @Column(nullable = false)
    private boolean smer; //smer (true ulaz, false izlaz)

    @Column(nullable = false)
    private String duznik;

    @Column(nullable = false)
    private String poverilac;

    @Column(nullable = false)
    private String svrhaPlacanja;

    @Column(nullable = false)
    private Date datumValute;

    @Column(nullable = false)
    private String racunDuznika;

    @Column(nullable = false)
    private long modelZaduzenja;

    @Column(nullable = false)
    private String pozivNaBrojZaduzenja;

    @Column(nullable =  false, length = 20)
    private String racunPoverioca;

    @Column(nullable = false)
    private long modelOdobrenja;

    @Column(nullable = false)
    private String pozivNaBrojOdobrenja;

    @Column(nullable = false)
    private BigDecimal iznos;

    @ManyToOne
    private DnevnoStanjeRacuna dnevnoStanjeRacuna;

}
