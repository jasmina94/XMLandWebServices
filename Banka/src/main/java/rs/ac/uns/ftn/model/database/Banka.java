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
public class Banka {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String naziv;

    @Column(length = 8)
    private String SWIFTkod;

    @Column(name = "racun_banke", length = 20)
    private String obracunskiRacun;

    @Column
    private int sifra; //sifra banke prva tri broja

    @OneToMany(mappedBy = "banka")
    private List<Racun> racuni;
}
