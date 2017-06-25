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

    @Column
    private String brojRacuna;

    @Column
    private Double saldo;

    @ManyToOne
    private Banka banka;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "racun")
    private List<DnevnoStanjeRacuna> dnevnoStanjeRacuna;


}
