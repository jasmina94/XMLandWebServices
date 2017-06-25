package com.ftn.model.dto;

import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Olivera on 22.6.2017..
 */
@Data
@NoArgsConstructor
public class NalogZaPrenosDTO {

    private long id;

    @NotNull
    @Size(max = 255)
    private String duznik;

    @NotNull
    @Size(max = 255)
    private String poverilac;

    @NotNull
    @Size(max = 255)
    private String svrhaPlacanja;

    @NotNull
    private TPodaciOPrenosuDTO podaciOPrenosuDTO;

    private Date datumNaloga;

    private Date datumValute;

    private boolean hitno;

    @Size(max = 50)
    private String idPoruke;

    public NalogZaPrenosDTO(NalogZaPrenos nalogZaPrenos) {
        this(nalogZaPrenos, true);
    }

    public NalogZaPrenosDTO(NalogZaPrenos nalogZaPrenos, boolean cascade) {
        this.id = nalogZaPrenos.getId();
        this.duznik = nalogZaPrenos.getDuznik();
        this.poverilac = nalogZaPrenos.getPoverilac();
        this.svrhaPlacanja = nalogZaPrenos.getSvrhaPlacanja();
        this.datumNaloga = nalogZaPrenos.getDatumNaloga();
        this.datumValute = nalogZaPrenos.getDatumValute();
        this.hitno = nalogZaPrenos.isHitno();
        this.idPoruke = nalogZaPrenos.getIdPoruke();
        if(cascade) {
            this.podaciOPrenosuDTO = nalogZaPrenos.getPodaciOPrenosu() != null ? new TPodaciOPrenosuDTO(nalogZaPrenos.getPodaciOPrenosu()) : null;
        }
    }


    public NalogZaPrenos construct() {
        final NalogZaPrenos nalogZaPrenos = new NalogZaPrenos();
        nalogZaPrenos.setDuznik(duznik);
        nalogZaPrenos.setPoverilac(poverilac);
        nalogZaPrenos.setSvrhaPlacanja(svrhaPlacanja);
        nalogZaPrenos.setDatumNaloga(datumNaloga);
        nalogZaPrenos.setDatumValute(datumValute);
        nalogZaPrenos.setHitno(hitno);
        nalogZaPrenos.setIdPoruke(idPoruke);
        nalogZaPrenos.setPodaciOPrenosu(podaciOPrenosuDTO != null ? podaciOPrenosuDTO.construct() : null);

        return nalogZaPrenos;
    }
}
