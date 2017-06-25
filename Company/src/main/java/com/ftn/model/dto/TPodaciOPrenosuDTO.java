package com.ftn.model.dto;

import com.ftn.model.generated.tipovi.TOznakaValute;
import com.ftn.model.generated.tipovi.TPodaciOPrenosu;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Olivera on 22.6.2017..
 */
@Data
@NoArgsConstructor
public class TPodaciOPrenosuDTO {

    private long id;

    @NotNull
    protected TPrenosUcesnikDTO duznikUPrenosuDTO;

    @NotNull
    protected TPrenosUcesnikDTO poverilacUPrenosuDTO;

    @NotNull
    @Digits(integer=15, fraction=2)
    protected BigDecimal iznos;

    protected TOznakaValute oznakaValute;

    public TPodaciOPrenosuDTO(TPodaciOPrenosu tPodaciOPrenosu) {
        this(tPodaciOPrenosu, true);
    }

    public TPodaciOPrenosuDTO(TPodaciOPrenosu tPodaciOPrenosu, boolean cascade) {
        this.id = tPodaciOPrenosu.getId();
        this.iznos = tPodaciOPrenosu.getIznos();
        this.oznakaValute = tPodaciOPrenosu.getOznakaValute();
        if(cascade) {
            this.duznikUPrenosuDTO = tPodaciOPrenosu.getDuznikUPrenosu() != null ? new TPrenosUcesnikDTO(tPodaciOPrenosu.getDuznikUPrenosu()) : null;
            this.poverilacUPrenosuDTO = tPodaciOPrenosu.getPoverilacUPrenosu() != null ? new TPrenosUcesnikDTO(tPodaciOPrenosu.getPoverilacUPrenosu()) : null;
        }
    }

    public TPodaciOPrenosu construct() {
        final TPodaciOPrenosu tPodaciOPrenosu = new TPodaciOPrenosu();
        tPodaciOPrenosu.setId(id);
        tPodaciOPrenosu.setDuznikUPrenosu(duznikUPrenosuDTO != null ? duznikUPrenosuDTO.construct() : null);
        tPodaciOPrenosu.setPoverilacUPrenosu(poverilacUPrenosuDTO != null ? poverilacUPrenosuDTO.construct() : null);
        tPodaciOPrenosu.setIznos(iznos);
        tPodaciOPrenosu.setOznakaValute(oznakaValute);

        return tPodaciOPrenosu;
    }
}
