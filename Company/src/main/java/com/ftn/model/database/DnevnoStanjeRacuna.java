package com.ftn.model.database;

import com.ftn.model.dto.DnevnoStanjeRacunaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zlatan on 6/24/17.
 */
@Entity
@Data
@NoArgsConstructor
public class DnevnoStanjeRacuna {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int brojPreseka;

    @Column(nullable = false)
    private Date datum;

    @Column(nullable = false)
    private double predhodnoStanje;

    @Column(nullable = false)
    private int brojPromenaNaTeret;

    @Column(nullable = false)
    private int brojPromenaUKorist;

    @Column(nullable = false)
    private double ukupnoUKorist;

    @Column(nullable = false)
    private double ukupnoNaTeret;

    @Column(nullable = false)
    private double novoStanje;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dnevnoStanjeRacuna", cascade = CascadeType.ALL)
    private List<AnalitikaIzvoda> analitikeIzvoda = new ArrayList<>();

    public void merge(DnevnoStanjeRacunaDTO dnevnoStanjeRacunaDTO) {
        this.brojPreseka = dnevnoStanjeRacunaDTO.getBrojPreseka();
        this.datum = dnevnoStanjeRacunaDTO.getDatum();
        this.predhodnoStanje = dnevnoStanjeRacunaDTO.getPredhodnoStanje();
        this.brojPromenaNaTeret = dnevnoStanjeRacunaDTO.getBrojPromenaNaTeret();
        this.brojPromenaUKorist = dnevnoStanjeRacunaDTO.getBrojPromenaUKorist();
        this.ukupnoUKorist = dnevnoStanjeRacunaDTO.getUkupnoUKorist();
        this.ukupnoNaTeret = dnevnoStanjeRacunaDTO.getUkupnoNaTeret();
        this.novoStanje = dnevnoStanjeRacunaDTO.getNovoStanje();
    }
}