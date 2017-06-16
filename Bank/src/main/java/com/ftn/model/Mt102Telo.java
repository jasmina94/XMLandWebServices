
package com.ftn.model;


import com.ftn.util.DateAdapter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Date;


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
 *         &lt;element name="podaci_o_duzniku" type="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice"/>
 *         &lt;element name="podaci_o_poveriocu" type="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice"/>
 *         &lt;element name="svrha_placanja">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iznos">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.ftn.uns.ac.rs/tipovi>TIznos">
 *                 &lt;attribute name="valuta">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute">
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_zaduzenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *         &lt;element name="podaci_o_odobrenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *         &lt;element name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id_naloga">
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
    "podaciODuzniku",
    "podaciOPoveriocu",
    "svrhaPlacanja",
    "iznos",
    "podaciOZaduzenju",
    "podaciOOdobrenju",
    "datumNaloga"
})
@XmlRootElement(name = "mt102_telo", namespace = "http://www.ftn.uns.ac.rs/mt102body")
@Entity
public class Mt102Telo {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "podaci_o_duzniku", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @ManyToOne(optional = false)
    protected TPravnoLice podaciODuzniku;
    @XmlElement(name = "podaci_o_poveriocu", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @ManyToOne(optional = false)
    protected TPravnoLice podaciOPoveriocu;
    @XmlElement(name = "svrha_placanja", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @Size(max = 255)
    protected String svrhaPlacanja;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @ManyToOne(optional = false)
    protected Mt102Telo.Iznos iznos;
    @XmlElement(name = "podaci_o_zaduzenju", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @ManyToOne(optional = true)
    protected TPodaciPlacanje podaciOZaduzenju;
    @XmlElement(name = "podaci_o_odobrenju", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @ManyToOne(optional = false)
    protected TPodaciPlacanje podaciOOdobrenju;
    @XmlElement(name = "datum_naloga", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datumNaloga;
    @XmlAttribute(name = "id_naloga")
    @Size(max = 50)
    protected String idNaloga;

    public Mt102Telo() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the podaciODuzniku property.
     *
     * @return
     *     possible object is
     *     {@link TPravnoLice }
     *
     */
    public TPravnoLice getPodaciODuzniku() {
        return podaciODuzniku;
    }

    /**
     * Sets the value of the podaciODuzniku property.
     *
     * @param value
     *     allowed object is
     *     {@link TPravnoLice }
     *
     */
    public void setPodaciODuzniku(TPravnoLice value) {
        this.podaciODuzniku = value;
    }

    /**
     * Gets the value of the podaciOPoveriocu property.
     *
     * @return
     *     possible object is
     *     {@link TPravnoLice }
     *
     */
    public TPravnoLice getPodaciOPoveriocu() {
        return podaciOPoveriocu;
    }

    /**
     * Sets the value of the podaciOPoveriocu property.
     *
     * @param value
     *     allowed object is
     *     {@link TPravnoLice }
     *
     */
    public void setPodaciOPoveriocu(TPravnoLice value) {
        this.podaciOPoveriocu = value;
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
     * Gets the value of the iznos property.
     *
     * @return
     *     possible object is
     *     {@link Mt102Telo.Iznos }
     *
     */
    public Mt102Telo.Iznos getIznos() {
        return iznos;
    }

    /**
     * Sets the value of the iznos property.
     *
     * @param value
     *     allowed object is
     *     {@link Mt102Telo.Iznos }
     *
     */
    public void setIznos(Mt102Telo.Iznos value) {
        this.iznos = value;
    }

    /**
     * Gets the value of the podaciOZaduzenju property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciPlacanje }
     *     
     */
    public TPodaciPlacanje getPodaciOZaduzenju() {
        return podaciOZaduzenju;
    }

    /**
     * Sets the value of the podaciOZaduzenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciPlacanje }
     *     
     */
    public void setPodaciOZaduzenju(TPodaciPlacanje value) {
        this.podaciOZaduzenju = value;
    }

    /**
     * Gets the value of the podaciOOdobrenju property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciPlacanje }
     *     
     */
    public TPodaciPlacanje getPodaciOOdobrenju() {
        return podaciOOdobrenju;
    }

    /**
     * Sets the value of the podaciOOdobrenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciPlacanje }
     *     
     */
    public void setPodaciOOdobrenju(TPodaciPlacanje value) {
        this.podaciOOdobrenju = value;
    }

    /**
     * Gets the value of the datumNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
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
     * Gets the value of the idNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNaloga() {
        return idNaloga;
    }

    /**
     * Sets the value of the idNaloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNaloga(String value) {
        this.idNaloga = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.ftn.uns.ac.rs/tipovi>TIznos">
     *       &lt;attribute name="valuta">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute">
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    @Entity
    public static class Iznos {

        @Id
        @GeneratedValue
        @XmlTransient
        private long id;

        @XmlValue
        protected BigDecimal value;
        @XmlAttribute(name = "valuta")
        @Enumerated(EnumType.STRING)
        protected TOznakaValute valuta;

        public Iznos() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValue(BigDecimal value) {
            this.value = value;
        }

        /**
         * Gets the value of the valuta property.
         * 
         * @return
         *     possible object is
         *     {@link TOznakaValute }
         *     
         */
        public TOznakaValute getValuta() {
            return valuta;
        }

        /**
         * Sets the value of the valuta property.
         * 
         * @param value
         *     allowed object is
         *     {@link TOznakaValute }
         *     
         */
        public void setValuta(TOznakaValute value) {
            this.valuta = value;
        }

    }

}
