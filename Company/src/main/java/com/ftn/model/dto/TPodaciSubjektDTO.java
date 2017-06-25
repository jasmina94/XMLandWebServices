package com.ftn.model.dto;

import com.ftn.model.generated.tipovi.TPodaciSubjekt;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @NotNull
    protected String racunFirme;

    private List<TPodaciSubjektDTO> poslovniPartneri = new ArrayList<>();


    public TPodaciSubjektDTO(TPodaciSubjekt tPodaciSubjekt) {
        this(tPodaciSubjekt, true);
    }


    public TPodaciSubjektDTO(TPodaciSubjekt tPodaciSubjekt, boolean cascade) {
        this.id = tPodaciSubjekt.getId();
        this.naziv = tPodaciSubjekt.getNaziv();
        this.adresa = tPodaciSubjekt.getAdresa();
        this.pib = tPodaciSubjekt.getPib();
        this.racunFirme = tPodaciSubjekt.getRacunFirme();
        if(cascade) {
            this.poslovniPartneri = tPodaciSubjekt.getPoslovniPartneri().stream().map(poslovniPartner -> new TPodaciSubjektDTO(poslovniPartner, false)).collect(Collectors.toList());
           // this.poslovniPartneri = tPodaciSubjekt.getPoslovniPartneri().stream().map(poslovniPartner -> new TPodaciSubjektDTO(poslovniPartner)).collect(Collectors.toList());

        }
    }


    public TPodaciSubjekt construct() {
        final TPodaciSubjekt tPodaciSubjekt = new TPodaciSubjekt();
        tPodaciSubjekt.setNaziv(naziv);
        tPodaciSubjekt.setAdresa(adresa);
        tPodaciSubjekt.setPib(pib);
        tPodaciSubjekt.setRacunFirme(racunFirme);
        if(poslovniPartneri != null) {
            poslovniPartneri.forEach(poslovniPartnerDTO -> tPodaciSubjekt.getPoslovniPartneri().add(poslovniPartnerDTO.construct()));
        }

        return tPodaciSubjekt;
    }
}
