package com.ftn.model.dto;

import com.ftn.model.generated.tipovi.TPrenosUcesnik;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by Olivera on 22.6.2017..
 */
@Data
@NoArgsConstructor
public class TPrenosUcesnikDTO {

    private long id;

    @NotNull
    protected String racunUcesnika;

    protected long modelPrenosa;

    @NotNull
    protected String pozivNaBroj;

    public TPrenosUcesnikDTO(TPrenosUcesnik tPrenosUcesnik) {
        this(tPrenosUcesnik, true);
    }

    public TPrenosUcesnikDTO(TPrenosUcesnik tPrenosUcesnik, boolean cascade) {
        this.id = tPrenosUcesnik.getId();
        this.racunUcesnika = tPrenosUcesnik.getRacunUcesnika();
        this.modelPrenosa = tPrenosUcesnik.getModelPrenosa();
        this.pozivNaBroj = tPrenosUcesnik.getPozivNaBroj();
    }

    public TPrenosUcesnik construct() {
        final TPrenosUcesnik tPrenosUcesnik = new TPrenosUcesnik();
        tPrenosUcesnik.setRacunUcesnika(racunUcesnika);
        tPrenosUcesnik.setModelPrenosa(modelPrenosa);
        tPrenosUcesnik.setPozivNaBroj(pozivNaBroj);

        return tPrenosUcesnik;
    }
}
