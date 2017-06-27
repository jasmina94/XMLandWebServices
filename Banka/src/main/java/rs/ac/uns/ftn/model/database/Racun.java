package rs.ac.uns.ftn.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zlatan on 6/24/17.
 */
@Entity
@NoArgsConstructor
@Data
public class Racun {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String brojRacuna;

    @Column
    private double saldo = 0.0;

    @ManyToOne
    private Banka banka;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "racun")
    private List<DnevnoStanjeRacuna> dnevnoStanjeRacuna;

    @Column
    private double rezervisanaSredstva = 0.0;
}
