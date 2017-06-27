package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zlatan on 6/24/17.
 */
@Entity
@NoArgsConstructor
@Data
public class Mt102Model {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String idPoruke;

    @Column(length = 8)
    private String swiftBankeDuznika;

    @Column(length = 8)
    private String swiftBankePoverioca;

    @Column(length = 20)
    private String racunBankeDuznika;

    @Column(length = 20)
    private String racunBankePoverioca;

    @Column
    private double ukupanIznos;

    @Column
    private String sifraValute;

    @Column
    private Date datumValute;

    @Column
    private Date datumNaloga;

    @Column
    private boolean poslato;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mt102Model")
    private List<PojedinacniNalogZaPlacanje> listaNalogaZaPlacanje;
}
