package com.ftn.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Olivera on 26.6.2017..
 */
@Data
@NoArgsConstructor
public class ZahtevZaIzvodDTO {

    @NotNull
    private String brojRacuna;

    @NotNull
    private Date datum;

    @NotNull
    private int redniBroj;

    public ZahtevZaIzvodDTO()
}
