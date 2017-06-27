package com.ftn.model.dto;


import com.ftn.model.database.AnalitikaIzvoda;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by JELENA on 27.6.2017.
 */
@Data
@NoArgsConstructor
public class AnalitikaIzvodaDTO {

    private Long id;

    @NotNull
    private Date datumNaloga;

    @NotNull
    private boolean smer; //smer (true ulaz, false izlaz)

    @NotNull
    private String duznik;

    @NotNull
    private String poverilac;

    @NotNull
    private String svrhaPlacanja;

    @NotNull
    private Date datumValute;

    @NotNull
    private String racunDuznika;

    @NotNull
    private long modelZaduzenja;

    @NotNull
    private String pozivNaBrojZaduzenja;

    @NotNull
    private String racunPoverioca;

    @NotNull
    private long modelOdobrenja;

    @NotNull
    private String pozivNaBrojOdobrenja;

    @NotNull
    private BigDecimal iznos;



    private DnevnoStanjeRacunaDTO dnevnoStanjeRacuna;

    public AnalitikaIzvodaDTO(AnalitikaIzvoda analitikaIzvoda) {
        this(analitikaIzvoda, true);
    }

    public AnalitikaIzvodaDTO(AnalitikaIzvoda analitikaIzvoda, boolean cascade) {
        this.id = analitikaIzvoda.getId();
        this.datumNaloga = analitikaIzvoda.getDatumNaloga();
        this.smer = analitikaIzvoda.isSmer();
        this.duznik = analitikaIzvoda.getDuznik();
        this.poverilac = analitikaIzvoda.getPoverilac();
        this.svrhaPlacanja = analitikaIzvoda.getSvrhaPlacanja();
        this.datumValute = analitikaIzvoda.getDatumValute();
        this.racunDuznika = analitikaIzvoda.getRacunDuznika();
        this.modelZaduzenja = analitikaIzvoda.getModelZaduzenja();
        this.pozivNaBrojZaduzenja = analitikaIzvoda.getPozivNaBrojZaduzenja();
        this.racunPoverioca = analitikaIzvoda.getRacunPoverioca();
        this.modelOdobrenja = analitikaIzvoda.getModelOdobrenja();
        this.pozivNaBrojOdobrenja = analitikaIzvoda.getPozivNaBrojOdobrenja();
        this.iznos = analitikaIzvoda.getIznos();


        if (cascade) {
            this.dnevnoStanjeRacuna = analitikaIzvoda.getDnevnoStanjeRacuna() != null ? new DnevnoStanjeRacunaDTO(analitikaIzvoda.getDnevnoStanjeRacuna(), false) : null;
        }
    }

    public AnalitikaIzvoda construct() {
        final AnalitikaIzvoda analitikaIzvoda = new AnalitikaIzvoda();
        analitikaIzvoda.setDatumNaloga(datumNaloga);
        analitikaIzvoda.setSmer(smer);
        analitikaIzvoda.setDuznik(duznik);
        analitikaIzvoda.setPoverilac(poverilac);
        analitikaIzvoda.setSvrhaPlacanja(svrhaPlacanja);
        analitikaIzvoda.setDatumValute(datumValute);
        analitikaIzvoda.setRacunDuznika(racunDuznika);
        analitikaIzvoda.setModelZaduzenja(modelZaduzenja);
        analitikaIzvoda.setPozivNaBrojZaduzenja(pozivNaBrojZaduzenja);
        analitikaIzvoda.setRacunPoverioca(racunPoverioca);
        analitikaIzvoda.setModelOdobrenja(modelOdobrenja);
        analitikaIzvoda.setPozivNaBrojOdobrenja(pozivNaBrojOdobrenja);
        analitikaIzvoda.setIznos(iznos);
        analitikaIzvoda.setDnevnoStanjeRacuna(dnevnoStanjeRacuna != null ? dnevnoStanjeRacuna.construct() : null);

        return analitikaIzvoda;
    }
}
