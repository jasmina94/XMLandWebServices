package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zlatan on 6/24/17.
 */
@Data
@Entity
@NoArgsConstructor
public class AnalitikaIzvoda {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date datumNaloga;

    @Column
    private boolean primljeno; //smer (true ulaz, false izlaz)

    @Column
    private String duznik;

    @Column
    private String poverilac;

    @Column
    private String svrhaPlacanja;

    @Column
    private Date datumValute;

    @Column(length = 20)
    private String racunDuznika;

    @Column
    private long modelZaduzenja;

    @Column
    private String pozivNaBrojZaduzenja;

    @Column(length = 20)
    private String racunPoverioca;

    @Column
    private long modelOdobrenja;

    @Column
    private String pozivNaBrojOdobrenja;

    @Column
    private BigDecimal iznos;

    @Column
    private String sifraValute;

    @ManyToOne
    private DnevnoStanjeRacuna dnevnoStanjeRacuna;

}
