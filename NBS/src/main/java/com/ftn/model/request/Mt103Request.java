package com.ftn.model.request;

import com.ftn.model.Mt103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alex on 6/21/17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getMt103Request", namespace = "http://www.ftn.uns.ac.rs/mt103")
public class Mt103Request {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
    private Mt103 mt103;

    public Mt103 getMt103() {
        return mt103;
    }

    public void setMt103(Mt103 mt103) {
        this.mt103 = mt103;
    }
}
