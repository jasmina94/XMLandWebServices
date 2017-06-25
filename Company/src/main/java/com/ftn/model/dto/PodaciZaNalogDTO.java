package com.ftn.model.dto;

import com.ftn.model.generated.faktura.Faktura;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by Olivera on 22.6.2017..
 */
@Data
@NoArgsConstructor
public class PodaciZaNalogDTO {

    private Faktura faktura;

    @NotNull
    protected String racunDuznika;

    protected long modelZaduzenja;

    @NotNull
    protected String pozivNaBrojZaduzenja;

    protected long modelOdobrenja;

    @NotNull
    protected String pozivNaBrojOdobrenja;

    private boolean hitno;

    public PodaciZaNalogDTO(PodaciZaNalogDTO podaciZaNalogDTO) {
        this(podaciZaNalogDTO, true);
    }

    public PodaciZaNalogDTO(PodaciZaNalogDTO podaciZaNalogDTO, boolean cascade) {
        this.faktura = podaciZaNalogDTO.getFaktura();
        this.racunDuznika = podaciZaNalogDTO.getRacunDuznika();
        this.modelZaduzenja = podaciZaNalogDTO.getModelZaduzenja();
        this.pozivNaBrojZaduzenja = podaciZaNalogDTO.getPozivNaBrojZaduzenja();
        this.modelOdobrenja = podaciZaNalogDTO.getModelOdobrenja();
        this.pozivNaBrojOdobrenja = podaciZaNalogDTO.getPozivNaBrojOdobrenja();
        this.hitno = podaciZaNalogDTO.isHitno();
        if(cascade) {
            this.faktura = podaciZaNalogDTO.getFaktura();
        }
    }

}
