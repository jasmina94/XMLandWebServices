package com.ftn.model.request;

import com.ftn.model.Mt910;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alex on 6/21/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getMt910Request", namespace = "http://www.ftn.uns.ac.rs/mt910")
public class Mt910Request {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt910", required = true)
    private Mt910 mt910;

    public Mt910 getMt910() {
        return mt910;
    }

    public void setMt910(Mt910 mt910) {
        this.mt910 = mt910;
    }
}
