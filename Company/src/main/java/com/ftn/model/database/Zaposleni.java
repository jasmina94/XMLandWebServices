package com.ftn.model.database;

import com.ftn.model.generated.tipovi.TPodaciSubjekt;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Created by Olivera on 17.6.2017..
 */
@Entity
@Data
@NoArgsConstructor
public class Zaposleni {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 13)
    private String jmbg;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    private String adresa;

    @Column(unique = true)
    private String korisnickoIme;

    @Column(unique = true)
    private String lozinka;

    @ManyToOne
    //TODO: Da li je obavezno?
   private TPodaciSubjekt tPodaciSubjekt;

}
