package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Olivera on 16.6.2017..
 */
@Entity
public class Proba {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    public Proba() {}


}
