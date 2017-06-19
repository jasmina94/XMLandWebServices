package com.ftn.model.dto;

import com.ftn.model.Firma;
import com.ftn.model.Lokacija;
import com.ftn.model.Zaposleni;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Olivera on 18.6.2017..
 */
@Data
@NoArgsConstructor
public class ZaposleniDTO {

    private long id;

    @NotNull
    @Size(min = 13, max = 13)
    @Pattern(regexp = "[0-9]*")
    private String jmbg;

    @NotNull
    private String ime;

    @NotNull
    private String prezime;

    private String adresa;

    @NotNull
    private String korisnickoIme;

    private Lokacija lokacija;

    private Firma firma;

    public ZaposleniDTO(Zaposleni zaposleni) {
        this.jmbg = zaposleni.getJmbg();
        this.ime = zaposleni.getIme();
        this.prezime = zaposleni.getPrezime();
        this.adresa = zaposleni.getAdresa();
        this.korisnickoIme = zaposleni.getKorisnickoIme();
        //this.lokacija = zaposleni.getLokacija();
        //this.firma = zaposleni.getFirma();
    }
}
