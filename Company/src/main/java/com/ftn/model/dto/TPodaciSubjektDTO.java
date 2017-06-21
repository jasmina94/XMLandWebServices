package com.ftn.model.dto;

import com.ftn.model.PoslovniPartner;
import com.ftn.model.TPodaciSubjekt;
import com.ftn.model.Zaposleni;
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
    private String mesto;

    private List<PoslovniPartner> poslovniPartneri = new ArrayList<>();

    private List<ZaposleniDTO> zaposleni = new ArrayList<>();

    public TPodaciSubjektDTO(TPodaciSubjekt tPodaciSubjekt) {
        this(tPodaciSubjekt, true);
    }


    public TPodaciSubjektDTO(TPodaciSubjekt tPodaciSubjekt, boolean cascade) {
        this.id = tPodaciSubjekt.getId();
        this.naziv = tPodaciSubjekt.getNaziv();
        this.adresa = tPodaciSubjekt.getAdresa();
        this.pib = tPodaciSubjekt.getPib();
        this.mesto = tPodaciSubjekt.getMesto();
        if(cascade) {
            //TODO: Izmeni ako se doda poslovniPartnerDTO
            //this.poslovniPartneri = tPodaciSubjekt.getPoslovniPartneri();
            this.zaposleni = tPodaciSubjekt.getZaposleni().stream().map(zaposleni -> new ZaposleniDTO(zaposleni, false)).collect(Collectors.toList());
        }
    }


    //TODO: Izmeni poslovne partnere ako se napravi PoslovniPartnerDTO
    public TPodaciSubjekt construct() {
        final TPodaciSubjekt tPodaciSubjekt = new TPodaciSubjekt();
        tPodaciSubjekt.setNaziv(naziv);
        tPodaciSubjekt.setAdresa(adresa);
        tPodaciSubjekt.setPib(pib);
        tPodaciSubjekt.setMesto(mesto);
      /*  if(poslovniPartneri != null) {
            tPodaciSubjekt.setPoslovniPartneri(poslovniPartneri);
        }*/
        if(zaposleni != null) {
            zaposleni.forEach(zaposleniDTO -> tPodaciSubjekt.getZaposleni().add(zaposleniDTO.construct()));
        }
        return tPodaciSubjekt;
    }
}
