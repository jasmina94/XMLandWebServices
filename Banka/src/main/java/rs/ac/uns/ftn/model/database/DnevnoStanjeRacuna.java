package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double predhodnoStanje;

    @Column
    private Double prometNaTeret;

    @Column
    private Double prometuKorist;

    @Column
    private Double novoStanje;

    @ManyToOne
    private Racun racun;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dnevnoStanjeRacuna")
    private List<AnalitikaIzvoda> analitikaIzvoda;

}
