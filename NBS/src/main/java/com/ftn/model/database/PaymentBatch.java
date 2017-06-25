package com.ftn.model.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 6/24/17.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PaymentBatch extends BaseModel {

    private String messageId;

    private String creditorSwift;

    private String creditorAccountNumber;

    private String debtorSwift;

    private String debtorAccountNumber;

    @Type(type = "text")
    private String mt102Model;

    private double total;

    private String currency;

    private Date dateOfValue;

    private Date dateOfPayment;

    private boolean cleared;

    @OneToMany(mappedBy="paymentBatch")
    private List<Payment> payments;
}
