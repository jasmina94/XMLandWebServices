package com.ftn.model.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * Created by Alex on 6/24/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Bank extends BaseModel {

    private String name;

    private int code;

    private String swiftCode;

    private String accountNumber;

    private double accountBalance;

    private String url;
}
