package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private boolean primljeno;

    @Column(length = 8)
    private String SWIFTBankeDuznika;

    @Column(length = 8)
    private String SWIFTBankePoverioca;

    @Column(length = 20)
    private String racunBankeDuznika;

    @Column(length = 20)
    private String racunBankePoverioca;

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
    private int modelZaduzenja;

    @Column
    private String pozivNaBrojZaduzenja;

    @Column(length = 20)
    private String racunPoverioca;

    @Column
    private int modelOdobrenja;

    @Column
    private String pozivNaBrojOdobrenja;

    @Column
    private Double iznos;

    @Column
    private String sifraValute;

    @ManyToOne
    private DnevnoStanjeRacuna dnevnoStanjeRacuna;

}
