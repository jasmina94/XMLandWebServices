package com.ftn.model.dto;

import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.tipovi.TOznakaValute;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JELENA on 19.6.2017.
 */
@Data
@NoArgsConstructor
public class FakturaDTO {

    private long id;

    @NotNull
    private TPodaciSubjektDTO podaciODobavljacu;

    @NotNull
    private TPodaciSubjektDTO podaciOKupcu;

    @NotNull
    private BigDecimal vrednostRobe;

    @NotNull
    private BigDecimal vrednostUsluga;

    @NotNull
    private BigDecimal ukupnoRobaIUsluga;

    @NotNull
    private BigDecimal ukupanRabat;

    @NotNull
    private BigDecimal ukupanPorez;

    @NotNull
    private TOznakaValute oznakaValute;

    @NotNull
    private BigDecimal iznosZaUplatu;

    @NotNull
    private String uplataNaRacun;

    //TODO:Staviti NotNull?
    private List<TStavkaFakturaDTO> stavkaFakture;

    private String idPoruke;

    private Long brojRacuna;

    private Date datumRacuna;

    private Date datumValute;

    @NotNull
    private boolean poslato;

    @NotNull
    private boolean kreiranNalog;


    public FakturaDTO(Faktura faktura) {
        this(faktura, true);
    }

    public FakturaDTO(Faktura faktura, boolean cascade) {
        this.id = faktura.getId();
        this.vrednostRobe = faktura.getVrednostRobe();
        this.vrednostUsluga = faktura.getVrednostUsluga();
        this.ukupnoRobaIUsluga = faktura.getUkupnoRobaIUsluga();
        this.ukupanRabat = faktura.getUkupanRabat();
        this.ukupanPorez = faktura.getUkupanPorez();
        this.oznakaValute = faktura.getOznakaValute();
        this.iznosZaUplatu = faktura.getIznosZaUplatu();
        this.uplataNaRacun = faktura.getUplataNaRacun();
        this.idPoruke = faktura.getIdPoruke();
        this.brojRacuna = faktura.getBrojRacuna();
        this.datumRacuna = faktura.getDatumRacuna();
        this.datumValute = faktura.getDatumValute();
        this.poslato = faktura.isPoslato();
        this.kreiranNalog = faktura.isKreiranNalog();

        if (cascade) {

//          this.podaciODobavljacu = faktura.getPodaciODobavljacu() != null ? new TPodaciSubjektDTO(faktura.getPodaciODobavljacu(), false) : null;
//          this.podaciOKupcu = faktura.getPodaciOKupcu() != null ? new TPodaciSubjektDTO(faktura.getPodaciOKupcu(), false) : null;
            this.podaciODobavljacu = faktura.getPodaciODobavljacu() != null ? new TPodaciSubjektDTO(faktura.getPodaciODobavljacu()) : null;
            this.podaciOKupcu = faktura.getPodaciOKupcu() != null ? new TPodaciSubjektDTO(faktura.getPodaciOKupcu()) : null;
            this.stavkaFakture = faktura.getStavkaFakture().stream().map(stavka -> new TStavkaFakturaDTO(stavka)).collect(Collectors.toList());
        }
    }

    public Faktura construct() {
        final Faktura faktura = new Faktura();
        faktura.setPodaciODobavljacu(podaciODobavljacu != null ? podaciODobavljacu.construct() : null);
        faktura.setPodaciOKupcu(podaciOKupcu != null ? podaciOKupcu.construct() : null);
        faktura.setVrednostRobe(vrednostRobe);
        faktura.setVrednostUsluga(vrednostUsluga);
        faktura.setUkupnoRobaIUsluga(ukupnoRobaIUsluga);
        faktura.setUkupanRabat(ukupanRabat);
        faktura.setUkupanPorez(ukupanPorez);
        faktura.setOznakaValute(oznakaValute);
        faktura.setIznosZaUplatu(iznosZaUplatu);
        faktura.setUplataNaRacun(uplataNaRacun);
        faktura.setIdPoruke(idPoruke);
        faktura.setBrojRacuna(brojRacuna);
        faktura.setDatumRacuna(datumRacuna);
        faktura.setDatumValute(datumValute);
        faktura.setPoslato(poslato);
        faktura.setKreiranNalog(kreiranNalog);
        if (stavkaFakture != null) {
            stavkaFakture.forEach(tStavkaFakturaDTO -> faktura.getStavkaFakture().add(tStavkaFakturaDTO.construct()));
        }

        return faktura;
    }
}
