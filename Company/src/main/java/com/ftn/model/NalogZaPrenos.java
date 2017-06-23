
package com.ftn.model;

import com.ftn.util.DateAdapter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * <p>Java class for nalog_za_prenos complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="nalog_za_prenos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="duznik">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="poverilac">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="svrha_placanja">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_prenosu" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciOPrenosu"/>
 *       &lt;/sequence>
 *       &lt;attribute name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="hitno" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="id_poruke">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="50"/>
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
    "duznik",
    "poverilac",
    "svrhaPlacanja",
    "podaciOPrenosu"
})
@XmlRootElement(name = "nalog_za_prenos", namespace = "http://ftn.uns.ac.rs/nalog_za_prenos")
@Entity
public class NalogZaPrenos {

    @Id
    @GeneratedValue
    @XmlTransient
    public long id;
    @XmlElement(namespace = "http://ftn.uns.ac.rs/nalog_za_prenos", required = true)
    @Column(nullable = false)
    @Size(max = 255)
    protected String duznik;
    @XmlElement(namespace = "http://ftn.uns.ac.rs/nalog_za_prenos", required = true)
    @Column(nullable = false)
    @Size(max = 255)
    protected String poverilac;
    @XmlElement(name = "svrha_placanja", namespace = "http://ftn.uns.ac.rs/nalog_za_prenos", required = true)
    @Column(nullable = false)
    @Size(max = 255)
    protected String svrhaPlacanja;
    @XmlElement(name = "podaci_o_prenosu", namespace = "http://ftn.uns.ac.rs/nalog_za_prenos", required = true)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    protected TPodaciOPrenosu podaciOPrenosu;
    @XmlAttribute(name = "datum_naloga")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date datumNaloga;
    @XmlAttribute(name = "datum_valute")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date datumValute;
    @XmlAttribute(name = "hitno")
    protected Boolean hitno;
    @XmlAttribute(name = "id_poruke")
    @Size(max = 50)
    protected String idPoruke;

    public NalogZaPrenos() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the duznik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuznik() {
        return duznik;
    }

    /**
     * Sets the value of the duznik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuznik(String value) {
        this.duznik = value;
    }

    /**
     * Gets the value of the poverilac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoverilac() {
        return poverilac;
    }

    /**
     * Sets the value of the poverilac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoverilac(String value) {
        this.poverilac = value;
    }

    /**
     * Gets the value of the svrhaPlacanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSvrhaPlacanja() {
        return svrhaPlacanja;
    }

    /**
     * Sets the value of the svrhaPlacanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSvrhaPlacanja(String value) {
        this.svrhaPlacanja = value;
    }

    /**
     * Gets the value of the podaciOPrenosu property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciOPrenosu }
     *     
     */
    public TPodaciOPrenosu getPodaciOPrenosu() {
        return podaciOPrenosu;
    }

    /**
     * Sets the value of the podaciOPrenosu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciOPrenosu }
     *     
     */
    public void setPodaciOPrenosu(TPodaciOPrenosu value) {
        this.podaciOPrenosu = value;
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
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDatumValute() {
        return datumValute;
    }

    /**
     * Sets the value of the datumValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDatumValute(Date value) {
        this.datumValute = value;
    }

    /**
     * Gets the value of the hitno property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHitno() {
        return hitno;
    }

    /**
     * Sets the value of the hitno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHitno(Boolean value) {
        this.hitno = value;
    }

    /**
     * Gets the value of the idPoruke property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPoruke() {
        return idPoruke;
    }

    /**
     * Sets the value of the idPoruke property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPoruke(String value) {
        this.idPoruke = value;
    }

}
