package com.ftn.model.database;

import com.ftn.model.dto.RobaUslugaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by JELENA on 22.6.2017.
 */
@Entity
@Data
public class RobaUsluga {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Size(max = 120)
    private String naziv;

    @Column(nullable = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal cena;

    @Column(nullable = false)
    @Size(max = 6)
    private String jedinicaMere;

    @Column(nullable = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal procenatRabata;

    @Column(nullable = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal procenatPoreza;

    @Column(nullable = false)
    private boolean tip;

    public RobaUsluga() {}

    public void merge(RobaUslugaDTO robaUslugaDTO) {
       this.naziv = robaUslugaDTO.getNaziv();
       this.cena = robaUslugaDTO.getCena();
       this.jedinicaMere = robaUslugaDTO.getJedinicaMere();
       this.procenatRabata = robaUslugaDTO.getProcenatRabata();
       this.procenatPoreza = robaUslugaDTO.getProcenatPoreza();
       this.tip = robaUslugaDTO.isTip();
    }


}
