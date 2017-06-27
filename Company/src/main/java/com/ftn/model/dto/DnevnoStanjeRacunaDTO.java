package com.ftn.model.dto;

import com.ftn.model.database.DnevnoStanjeRacuna;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 27.6.2017.
 */
@Data
@NoArgsConstructor
public class DnevnoStanjeRacunaDTO {

    private Long id;

    @NotNull
    private int brojPreseka;

    @NotNull
    private Date datum;

    @NotNull
    private double predhodnoStanje;

    @NotNull
    private int brojPromenaNaTeret;

    @NotNull
    private int brojPromenaUKorist;

    @NotNull
    private double ukupnoUKorist;

    @NotNull
    private double ukupnoNaTeret;

    @NotNull
    private double novoStanje;


    private List<AnalitikaIzvodaDTO> analitikeIzvoda = new ArrayList<>();

    public DnevnoStanjeRacunaDTO(DnevnoStanjeRacuna dnevnoStanjeRacuna) {
        this(dnevnoStanjeRacuna, true);
    }

    public DnevnoStanjeRacunaDTO(DnevnoStanjeRacuna dnevnoStanjeRacuna, boolean cascade) {
        this.id = dnevnoStanjeRacuna.getId();
        this.brojPreseka = dnevnoStanjeRacuna.getBrojPreseka();
        this.datum = dnevnoStanjeRacuna.getDatum();
        this.predhodnoStanje = dnevnoStanjeRacuna.getPredhodnoStanje();
        this.brojPromenaNaTeret = dnevnoStanjeRacuna.getBrojPromenaNaTeret();
        this.brojPromenaUKorist = dnevnoStanjeRacuna.getBrojPromenaUKorist();
        this.ukupnoUKorist = dnevnoStanjeRacuna.getUkupnoUKorist();
        this.ukupnoNaTeret = dnevnoStanjeRacuna.getUkupnoNaTeret();
        this.novoStanje = dnevnoStanjeRacuna.getNovoStanje();

        if (cascade) {
            this.analitikeIzvoda = dnevnoStanjeRacuna.getAnalitikeIzvoda().stream().map(analitikaIzvoda -> new AnalitikaIzvodaDTO(analitikaIzvoda, false)).collect(Collectors.toList());
        }
    }

    public DnevnoStanjeRacuna construct() {
        final DnevnoStanjeRacuna dnevnoStanjeRacuna = new DnevnoStanjeRacuna();

        dnevnoStanjeRacuna.setId(id);
        dnevnoStanjeRacuna.setBrojPreseka(brojPreseka);
        dnevnoStanjeRacuna.setDatum(datum);
        dnevnoStanjeRacuna.setPredhodnoStanje(predhodnoStanje);
        dnevnoStanjeRacuna.setBrojPromenaNaTeret(brojPromenaNaTeret);
        dnevnoStanjeRacuna.setBrojPromenaUKorist(brojPromenaUKorist);
        dnevnoStanjeRacuna.setUkupnoUKorist(ukupnoUKorist);
        dnevnoStanjeRacuna.setUkupnoNaTeret(ukupnoNaTeret);
        dnevnoStanjeRacuna.setNovoStanje(novoStanje);

        if (analitikeIzvoda != null) {
            analitikeIzvoda.forEach(analitikaIzvodaDTO -> dnevnoStanjeRacuna.getAnalitikeIzvoda().add(analitikaIzvodaDTO.construct()));
        }

        return dnevnoStanjeRacuna;
    }
}
