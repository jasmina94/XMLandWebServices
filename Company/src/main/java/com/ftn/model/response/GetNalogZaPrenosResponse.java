
package com.ftn.model.response;

import com.ftn.model.NalogZaPrenos;

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
 *         &lt;element name="nalog_za_prenos" type="{http://ftn.uns.ac.rs/nalog_za_prenos}nalog_za_prenos"/>
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
    "nalogZaPrenos"
})
@XmlRootElement(name = "get_nalog_za_prenos_response", namespace = "http://ftn.uns.ac.rs/nalog_za_prenos")
public class GetNalogZaPrenosResponse {

    @XmlElement(name = "nalog_za_prenos", namespace = "http://ftn.uns.ac.rs/nalog_za_prenos", required = true)
    protected NalogZaPrenos nalogZaPrenos;

    /**
     * Gets the value of the nalogZaPrenos property.
     * 
     * @return
     *     possible object is
     *     {@link NalogZaPrenos }
     *     
     */
    public NalogZaPrenos getNalogZaPrenos() {
        return nalogZaPrenos;
    }

    /**
     * Sets the value of the nalogZaPrenos property.
     * 
     * @param value
     *     allowed object is
     *     {@link NalogZaPrenos }
     *     
     */
    public void setNalogZaPrenos(NalogZaPrenos value) {
        this.nalogZaPrenos = value;
    }

}
