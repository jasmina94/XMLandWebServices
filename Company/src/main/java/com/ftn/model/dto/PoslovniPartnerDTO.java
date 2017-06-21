package com.ftn.model.dto;

import com.ftn.model.PoslovniPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Olivera on 21.6.2017..
 */
@Data
@NoArgsConstructor
public class PoslovniPartnerDTO {

    private long id;

    @NotNull
    private String naziv;

    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = "[0-9]*")
    private String pib;

    private String adresa;

    private String mesto;

    @NotNull
    private TPodaciSubjektDTO tPodaciSubjektDTO;

    public PoslovniPartnerDTO(PoslovniPartner poslovniPartner) {
        this(poslovniPartner, true);
    }

    public PoslovniPartnerDTO(PoslovniPartner poslovniPartner, boolean cascade) {
        this.id = poslovniPartner.getId();
        this.naziv = poslovniPartner.getNaziv();
        this.pib = poslovniPartner.getPib();
        this.adresa = poslovniPartner.getAdresa();
        this.mesto = poslovniPartner.getMesto();
        if(cascade) {
            this.tPodaciSubjektDTO = poslovniPartner.getTPodaciSubjekt() != null ? new TPodaciSubjektDTO(poslovniPartner.getTPodaciSubjekt()) : null;
        }
    }

    public PoslovniPartner construct() {
        final PoslovniPartner poslovniPartner = new PoslovniPartner();
        poslovniPartner.setNaziv(naziv);
        poslovniPartner.setPib(pib);
        poslovniPartner.setAdresa(adresa);
        poslovniPartner.setMesto(mesto);
        poslovniPartner.setTPodaciSubjekt(tPodaciSubjektDTO != null ? tPodaciSubjektDTO.construct() : null);

        return poslovniPartner;
    }
}
