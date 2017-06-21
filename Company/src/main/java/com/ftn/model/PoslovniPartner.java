package com.ftn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


/**
 * Created by Olivera on 17.6.2017..
 */
@Entity
@Data
@NoArgsConstructor
public class PoslovniPartner {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false, length = 11, unique = true)
    @Pattern(regexp = "\\d{11}")
    protected String pib;

    @Column(nullable = false)
    private String adresa;

    @Column(nullable = false)
    private String mesto;

    @ManyToOne(optional = false)
    private TPodaciSubjekt tPodaciSubjekt;

}
