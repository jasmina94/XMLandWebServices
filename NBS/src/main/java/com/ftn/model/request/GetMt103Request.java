
package com.ftn.model.request;


import com.ftn.model.Mt103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mt103" type="{http://www.ftn.uns.ac.rs/mt103}mt103"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mt103"
})
@XmlRootElement(name = "getMt103Request", namespace = "http://www.ftn.uns.ac.rs/mt103")
public class GetMt103Request {

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
    protected Mt103 mt103;

    /**
     * Gets the value of the mt103 property.
     * 
     * @return
     *     possible object is
     *     {@link Mt103 }
     *     
     */
    public Mt103 getMt103() {
        return mt103;
    }

    /**
     * Sets the value of the mt103 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt103 }
     *     
     */
    public void setMt103(Mt103 value) {
        this.mt103 = value;
    }

}
