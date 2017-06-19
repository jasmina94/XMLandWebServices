package com.ftn.model.dto;

import com.ftn.model.TPodaciSubjekt;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by JELENA on 19.6.2017.
 */
@Data
@NoArgsConstructor
public class TPodaciSubjektDTO {

    private long id;

    @NotNull
    @Size(max = 255)
    private String naziv;

    @NotNull
    @Size(max = 255)
    private String adresa;

    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]*")
    private String pib;


    public TPodaciSubjektDTO(TPodaciSubjekt tPodaciSubjekt) {
        this.naziv = tPodaciSubjekt.getNaziv();
        this.adresa = tPodaciSubjekt.getAdresa();
        this.pib = tPodaciSubjekt.getPib();
    }


    public TPodaciSubjekt construct() {
        final TPodaciSubjekt tPodaciSubjekt = new TPodaciSubjekt();
        tPodaciSubjekt.setNaziv(naziv);
        tPodaciSubjekt.setAdresa(adresa);
        tPodaciSubjekt.setPib(pib);

        return tPodaciSubjekt;
    }
}
