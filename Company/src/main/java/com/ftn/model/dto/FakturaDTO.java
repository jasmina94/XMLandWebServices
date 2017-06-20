package com.ftn.model.dto;

import com.ftn.model.Faktura;
import com.ftn.model.TOznakaValute;
import com.ftn.model.TStavkaFaktura;
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
    private  BigDecimal iznosZaUplatu;

    @NotNull
    private String uplataNaRacun;

    //TODO: zameniti sa DTO. Staviti NotNull?
    private List<TStavkaFaktura> stavkaFaktura;

    private String idPoruke;

    private Long brojRacuna;

    private Date datumRacuna;

    private Date datumValute;


    public FakturaDTO(Faktura faktura) {
<<<<<<< HEAD
        this(faktura, true);
    }

    public FakturaDTO(Faktura faktura, boolean cascade) {
=======
        this.id = faktura.getId();
>>>>>>> 2.1
        this.podaciODobavljacu = new TPodaciSubjektDTO(faktura.getPodaciODobavljacu());
        this.podaciOKupcu = new TPodaciSubjektDTO(faktura.getPodaciOKupcu());
        this.vrednostRobe = faktura.getVrednostRobe();
        this.vrednostUsluga = faktura.getVrednostUsluga();
        this.ukupnoRobaIUsluga = faktura.getUkupnoRobaIUsluga();
        this.ukupanRabat = faktura.getUkupanRabat();
        this.ukupanPorez = faktura.getUkupanPorez();
        this.oznakaValute = faktura.getOznakaValute();
        this.iznosZaUplatu = faktura.getIznosZaUplatu();
        this.uplataNaRacun = faktura.getUplataNaRacun();
        //za listu stavki dodati kad bude DTO
        this.idPoruke = faktura.getIdPoruke();
        this.brojRacuna = faktura.getBrojRacuna();
        this.datumRacuna = faktura.getDatumRacuna();
        this.datumValute = faktura.getDatumValute();
    }

    public Faktura construct() {
        final Faktura faktura = new Faktura();
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

        return faktura;
    }
}
