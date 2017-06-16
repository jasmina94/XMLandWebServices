
package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TPodaciSubjekt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodaciSubjekt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="naziv">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="adresa">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="pib">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="11"/>
 *               &lt;maxLength value="11"/>
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
@XmlType(name = "TPodaciSubjekt", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {
    "naziv",
    "adresa",
    "pib"
})
@Entity
public class TPodaciSubjekt {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    @Size(max = 255)
    protected String naziv;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    @Size(max = 255)
    protected String adresa;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false, length = 11)
    protected String pib;

    public TPodaciSubjekt () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the adresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Sets the value of the adresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdresa(String value) {
        this.adresa = value;
    }

    /**
     * Gets the value of the pib property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPib() {
        return pib;
    }

    /**
     * Sets the value of the pib property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPib(String value) {
        this.pib = value;
    }

}