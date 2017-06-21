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
@XmlRootElement(name = "getMt103Response", namespace = "http://www.ftn.uns.ac.rs/mt1033")
public class Mt103Response {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt1033", required = true)
    private Mt103 mt1033;

    public Mt103 getMt1033() {
        return mt1033;
    }

    public void setMt1033(Mt103 mt1033) {
        this.mt1033 = mt1033;
    }
}
