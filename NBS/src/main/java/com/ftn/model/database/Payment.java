package com.ftn.model.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Alex on 6/24/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Payment extends BaseModel {

    private String paymentId;

    private String paymentPurpose;

    private String creditorAccountNumber;

    private String debtorAccountNumber;

    private Date dateOfOrder;

    private double total;

    private String currency;

    @ManyToOne
    private PaymentBatch paymentBatch;
}
