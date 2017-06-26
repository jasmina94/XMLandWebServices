package com.ftn.model.dto;

import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.tipovi.TStavkaFaktura;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Created by Olivera on 20.6.2017..
 */
@Data
@NoArgsConstructor
public class TStavkaFakturaDTO {

    private long id;

    @Size(min = 1, max = 999)
    protected int redniBroj;

    @NotNull
    @Size(max = 120)
    protected String nazivRobeUsluge;

    @NotNull
    @Digits(integer=10, fraction=2)
    protected BigDecimal kolicina;

    @NotNull
    @Size(max = 6)
    protected String jedinicaMere;

    @NotNull
    @Digits(integer=10, fraction=2)
    protected BigDecimal jedinicnaCena;

    @NotNull
    @Digits(integer=12, fraction=2)
    protected BigDecimal vrednost;

    @NotNull
    @Digits(integer=5, fraction=2)
    protected BigDecimal procenatRabata;

    @NotNull
    @Digits(integer=12, fraction=2)
    protected BigDecimal iznosRabata;

    @NotNull
    @Digits(integer=12, fraction=2)
    protected BigDecimal umanjenoZaRabat;

    @NotNull
    @Digits(integer=12, fraction=2)
    protected BigDecimal ukupanPorez;

    @NotNull
    private boolean roba;


    public TStavkaFakturaDTO(TStavkaFaktura tStavkaFaktura) {
        this(tStavkaFaktura, true);
    }

    public TStavkaFakturaDTO(TStavkaFaktura tStavkaFaktura, boolean cascade) {
        this.id = tStavkaFaktura.getId();
        this.redniBroj = tStavkaFaktura.getRedniBroj();
        this.nazivRobeUsluge = tStavkaFaktura.getNazivRobeUsluge();
        this.kolicina = tStavkaFaktura.getKolicina();
        this.jedinicaMere = tStavkaFaktura.getJedinicaMere();
        this.jedinicnaCena = tStavkaFaktura.getJedinicnaCena();
        this.vrednost = tStavkaFaktura.getVrednost();
        this.procenatRabata = tStavkaFaktura.getProcenatRabata();
        this.iznosRabata = tStavkaFaktura.getIznosRabata();
        this.umanjenoZaRabat = tStavkaFaktura.getUmanjenoZaRabat();
        this.ukupanPorez = tStavkaFaktura.getUkupanPorez();
        this.roba = tStavkaFaktura.isRoba();

    }

    public TStavkaFaktura construct() {
        final TStavkaFaktura tStavkaFaktura = new TStavkaFaktura();
        tStavkaFaktura.setRedniBroj(redniBroj);
        tStavkaFaktura.setNazivRobeUsluge(nazivRobeUsluge);
        tStavkaFaktura.setKolicina(kolicina);
        tStavkaFaktura.setJedinicaMere(jedinicaMere);
        tStavkaFaktura.setJedinicnaCena(jedinicnaCena);
        tStavkaFaktura.setVrednost(vrednost);
        tStavkaFaktura.setProcenatRabata(procenatRabata);
        tStavkaFaktura.setIznosRabata(iznosRabata);
        tStavkaFaktura.setUmanjenoZaRabat(umanjenoZaRabat);
        tStavkaFaktura.setUkupanPorez(ukupanPorez);
        tStavkaFaktura.setRoba(roba);
        return tStavkaFaktura;
    }
}
