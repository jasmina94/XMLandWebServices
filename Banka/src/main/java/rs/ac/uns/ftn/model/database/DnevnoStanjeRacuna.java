package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

/**
 * Created by zlatan on 6/24/17.
 */
@Entity
@Data
@NoArgsConstructor
public class DnevnoStanjeRacuna {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date datum;

    @Column
    private double predhodnoStanje;

    @Column
    private int brojPromenaNaTeret;

    @Column
    private int brojPromenaUKorist;

    @Column
    private double ukupnoUKorist;

    @Column
    private double ukupnoNaTeret;

    @Column
    private double novoStanje;

    @ManyToOne
    private Racun racun;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dnevnoStanjeRacuna")
    private List<AnalitikaIzvoda> analitikeIzvoda;
}