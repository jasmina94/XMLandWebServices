package com.ftn.model.dto;

import com.ftn.model.TStavkaFaktura;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

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


    public TStavkaFakturaDTO(TStavkaFaktura tStavkaFaktura) {
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
    }
}