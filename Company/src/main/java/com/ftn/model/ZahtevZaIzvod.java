
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="broj_racuna" type="{http://www.ftn.uns.ac.rs/tipovi}TBrojRacuna"/>
 *         &lt;element name="datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="redni_broj_preseka">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;totalDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
    "brojRacuna",
    "datum",
    "redniBrojPreseka"
})
@XmlRootElement(name = "zahtev_za_izvod", namespace = "http://www.ftn.uns.ac.rs/zahtevZaIzvod")
@Entity
public class ZahtevZaIzvod {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "broj_racuna", namespace = "http://www.ftn.uns.ac.rs/zahtevZaIzvod", required = true)
    @Column(nullable = false)
    protected String brojRacuna;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/zahtevZaIzvod", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datum;
    @XmlElement(name = "redni_broj_preseka", namespace = "http://www.ftn.uns.ac.rs/zahtevZaIzvod", required = true)
    @Column(nullable = false)
    @Size(max = 2)
    protected BigInteger redniBrojPreseka;

    public ZahtevZaIzvod() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the brojRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojRacuna() {
        return brojRacuna;
    }

    /**
     * Sets the value of the brojRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojRacuna(String value) {
        this.brojRacuna = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDatum(Date value) {
        this.datum = value;
    }

    /**
     * Gets the value of the redniBrojPreseka property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRedniBrojPreseka() {
        return redniBrojPreseka;
    }

    /**
     * Sets the value of the redniBrojPreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRedniBrojPreseka(BigInteger value) {
        this.redniBrojPreseka = value;
    }

}
