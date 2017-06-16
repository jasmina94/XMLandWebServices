
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
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
 *         &lt;element name="podaci_o_duzniku" type="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice"/>
 *         &lt;element name="podaci_o_poveriocu" type="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice"/>
 *         &lt;element name="podaci_o_uplati">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="svrha_placanja">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="255"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="iznos" type="{http://www.ftn.uns.ac.rs/tipovi}TIznos"/>
 *                   &lt;element name="podaci_o_zaduzenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *                   &lt;element name="podaci_o_odobrenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *                   &lt;element name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="smer">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="K"/>
 *                       &lt;enumeration value="T"/>
 *                       &lt;length value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
    "podaciODuzniku",
    "podaciOPoveriocu",
    "podaciOUplati"
})
@XmlRootElement(name = "stavka_preseka", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka")
@Entity
public class StavkaPreseka {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "podaci_o_duzniku", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
    @ManyToOne(optional = false)
    protected TPravnoLice podaciODuzniku;
    @XmlElement(name = "podaci_o_poveriocu", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
    @ManyToOne(optional = false)
    protected TPravnoLice podaciOPoveriocu;
    @XmlElement(name = "podaci_o_uplati", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
    @ManyToOne(optional = false)
    protected PodaciOUplati podaciOUplati;

    public StavkaPreseka() {}

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
     * Gets the value of the podaciOUplati property.
     * 
     * @return
     *     possible object is
     *     {@link StavkaPreseka.PodaciOUplati }
     *     
     */
    public StavkaPreseka.PodaciOUplati getPodaciOUplati() {
        return podaciOUplati;
    }

    /**
     * Sets the value of the podaciOUplati property.
     * 
     * @param value
     *     allowed object is
     *     {@link StavkaPreseka.PodaciOUplati }
     *     
     */
    public void setPodaciOUplati(StavkaPreseka.PodaciOUplati value) {
        this.podaciOUplati = value;
    }


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
     *         &lt;element name="svrha_placanja">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="255"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="iznos" type="{http://www.ftn.uns.ac.rs/tipovi}TIznos"/>
     *         &lt;element name="podaci_o_zaduzenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
     *         &lt;element name="podaci_o_odobrenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
     *         &lt;element name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *       &lt;/sequence>
     *       &lt;attribute name="smer">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="K"/>
     *             &lt;enumeration value="T"/>
     *             &lt;length value="1"/>
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
        "svrhaPlacanja",
        "iznos",
        "podaciOZaduzenju",
        "podaciOOdobrenju",
        "datumNaloga",
        "datumValute"
    })
    @Entity
    public static class PodaciOUplati {

        @Id
        @GeneratedValue
        @XmlTransient
        private long id;
        @XmlElement(name = "svrha_placanja", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @Column(nullable = false)
        @Size(max = 255)
        protected String svrhaPlacanja;
        @XmlElement(namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @Column(nullable = false)
        protected BigDecimal iznos;
        @XmlElement(name = "podaci_o_zaduzenju", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @ManyToOne(optional = false)
        protected TPodaciPlacanje podaciOZaduzenju;
        @XmlElement(name = "podaci_o_odobrenju", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @ManyToOne(optional = false)
        protected TPodaciPlacanje podaciOOdobrenju;
        @XmlElement(name = "datum_naloga", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @XmlJavaTypeAdapter(DateAdapter.class)
        @Column(nullable = false)
        protected Date datumNaloga;
        @XmlElement(name = "datum_valute", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
        @XmlJavaTypeAdapter(DateAdapter.class)
        @Column(nullable = false, length = 1)
        protected Date datumValute;
        @XmlAttribute(name = "smer")
        //TODO: Ovde sme vrednost da bude ili T ili K
        protected String smer;

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
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getIznos() {
            return iznos;
        }

        /**
         * Sets the value of the iznos property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setIznos(BigDecimal value) {
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
         * Gets the value of the smer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSmer() {
            return smer;
        }

        /**
         * Sets the value of the smer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSmer(String value) {
            this.smer = value;
        }

    }

}
