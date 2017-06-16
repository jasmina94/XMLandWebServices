
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.crypto.Data;
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
 *         &lt;element name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="prethodno_stanje">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="korist" type="{http://www.ftn.uns.ac.rs/tipovi}TPromene"/>
 *         &lt;element name="teret" type="{http://www.ftn.uns.ac.rs/tipovi}TPromene"/>
 *         &lt;element name="novo_stanje">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="broj_preseka">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *             &lt;totalDigits value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
    "datumNaloga",
    "prethodnoStanje",
    "korist",
    "teret",
    "novoStanje"
})
@XmlRootElement(name = "zaglavlje_preseka", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka")
@Entity
public class ZaglavljePreseka {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "broj_racuna", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @Column(nullable = false)
    protected String brojRacuna;
    @XmlElement(name = "datum_naloga", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datumNaloga;
    @XmlElement(name = "prethodno_stanje", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @Column(nullable = false)
    @Digits(integer = 15, fraction = 2)
    protected BigDecimal prethodnoStanje;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @ManyToOne(optional = false)
    protected TPromene korist;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @ManyToOne(optional = false)
    protected TPromene teret;
    @XmlElement(name = "novo_stanje", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @Column(nullable = false)
    @Digits(integer = 15, fraction = 2)
    protected BigDecimal novoStanje;
    @XmlAttribute(name = "broj_preseka")
    @Size(max = 2)
    protected BigInteger brojPreseka;

    public ZaglavljePreseka() {}

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
     * Gets the value of the datumNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDatumNaloga() {
        return datumNaloga;
    }

    /**
     * Sets the value of the datumNaloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDatumNaloga(Date value) {
        this.datumNaloga = value;
    }

    /**
     * Gets the value of the prethodnoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrethodnoStanje() {
        return prethodnoStanje;
    }

    /**
     * Sets the value of the prethodnoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrethodnoStanje(BigDecimal value) {
        this.prethodnoStanje = value;
    }

    /**
     * Gets the value of the korist property.
     * 
     * @return
     *     possible object is
     *     {@link TPromene }
     *     
     */
    public TPromene getKorist() {
        return korist;
    }

    /**
     * Sets the value of the korist property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPromene }
     *     
     */
    public void setKorist(TPromene value) {
        this.korist = value;
    }

    /**
     * Gets the value of the teret property.
     * 
     * @return
     *     possible object is
     *     {@link TPromene }
     *     
     */
    public TPromene getTeret() {
        return teret;
    }

    /**
     * Sets the value of the teret property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPromene }
     *     
     */
    public void setTeret(TPromene value) {
        this.teret = value;
    }

    /**
     * Gets the value of the novoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNovoStanje() {
        return novoStanje;
    }

    /**
     * Sets the value of the novoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNovoStanje(BigDecimal value) {
        this.novoStanje = value;
    }

    /**
     * Gets the value of the brojPreseka property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPreseka() {
        return brojPreseka;
    }

    /**
     * Sets the value of the brojPreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPreseka(BigInteger value) {
        this.brojPreseka = value;
    }

}
