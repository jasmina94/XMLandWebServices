package com.ftn.model.response;

import com.ftn.model.Mt102;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alex on 6/21/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getMt102Response", namespace = "http://www.ftn.uns.ac.rs/mt102")
public class Mt102Response {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt102", required = true)
    private Mt102 mt102;

    public Mt102 getMt102() {
        return mt102;
    }

    public void setMt102(Mt102 mt102) {
        this.mt102 = mt102;
    }
}