package com.ftn.model.request;

import com.ftn.model.Mt900;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alex on 6/21/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getMt900Request", namespace = "http://www.ftn.uns.ac.rs/mt900")
public class Mt900Request {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt900", required = true)
    private Mt900 mt900;

    public Mt900 getMt900() {
        return mt900;
    }

    public void setMt900(Mt900 mt900) {
        this.mt900 = mt900;
    }
}