package com.ftn.model.dto;

import com.ftn.model.database.Zaposleni;
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

    private String mesto;
    
    @NotNull
    private String korisnickoIme;

    private TPodaciSubjektDTO tPodaciSubjektDTO;

    public ZaposleniDTO(Zaposleni zaposleni) {
        this(zaposleni, true);
    }

    public ZaposleniDTO(Zaposleni zaposleni, boolean cascade) {
        this.id = zaposleni.getId();
        this.jmbg = zaposleni.getJmbg();
        this.ime = zaposleni.getIme();
        this.prezime = zaposleni.getPrezime();
        this.adresa = zaposleni.getAdresa();
        this.korisnickoIme = zaposleni.getKorisnickoIme();
        if(cascade) {
            this.tPodaciSubjektDTO = zaposleni.getTPodaciSubjekt() != null ? new TPodaciSubjektDTO(zaposleni.getTPodaciSubjekt()) : null;
        }
    }


    public Zaposleni construct() {
        final Zaposleni zaposleni = new Zaposleni();
        zaposleni.setJmbg(jmbg);
        zaposleni.setIme(ime);
        zaposleni.setPrezime(prezime);
        zaposleni.setAdresa(adresa);
        zaposleni.setPrezime(prezime);
        zaposleni.setTPodaciSubjekt(tPodaciSubjektDTO != null ? tPodaciSubjektDTO.construct() : null);

        return zaposleni;
    }


}
