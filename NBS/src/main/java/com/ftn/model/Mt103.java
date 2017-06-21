
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
 * &lt;complexType name="mt103">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="podaci_o_duzniku">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice">
 *                 &lt;sequence>
 *                   &lt;element name="podaci_o_banci" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="podaci_o_poveriocu">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice">
 *                 &lt;sequence>
 *                   &lt;element name="podaci_o_banci" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
 *                   &lt;element name="iznos">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.ftn.uns.ac.rs/tipovi>TIznos">
 *                           &lt;attribute name="valuta">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="RSD"/>
 *                                 &lt;enumeration value="USD"/>
 *                                 &lt;enumeration value="EUR"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="podaci_o_zaduzenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *                   &lt;element name="podaci_o_odobrenju" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciPlacanje"/>
 *                   &lt;element name="datum_naloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
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
    "podaciODuzniku",
    "podaciOPoveriocu",
    "podaciOUplati"
})
@XmlRootElement(name = "mt103", namespace = "http://www.ftn.uns.ac.rs/mt103")
@Entity
public class Mt103 {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "podaci_o_duzniku", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
    @ManyToOne(optional = false)
    protected Mt103 .PodaciODuzniku podaciODuzniku;
    @XmlElement(name = "podaci_o_poveriocu", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
    @ManyToOne(optional = false)
    protected Mt103 .PodaciOPoveriocu podaciOPoveriocu;
    @XmlElement(name = "podaci_o_uplati", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
    @ManyToOne(optional = false)
    protected Mt103 .PodaciOUplati podaciOUplati;
    @XmlAttribute(name = "id_poruke")
    @Size(max = 50)
    protected String idPoruke;

    public Mt103() {}

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
     *     {@link Mt103 .PodaciODuzniku }
     *     
     */
    public Mt103 .PodaciODuzniku getPodaciODuzniku() {
        return podaciODuzniku;
    }

    /**
     * Sets the value of the podaciODuzniku property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt103 .PodaciODuzniku }
     *     
     */
    public void setPodaciODuzniku(Mt103 .PodaciODuzniku value) {
        this.podaciODuzniku = value;
    }

    /**
     * Gets the value of the podaciOPoveriocu property.
     * 
     * @return
     *     possible object is
     *     {@link Mt103 .PodaciOPoveriocu }
     *     
     */
    public Mt103 .PodaciOPoveriocu getPodaciOPoveriocu() {
        return podaciOPoveriocu;
    }

    /**
     * Sets the value of the podaciOPoveriocu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt103 .PodaciOPoveriocu }
     *     
     */
    public void setPodaciOPoveriocu(Mt103 .PodaciOPoveriocu value) {
        this.podaciOPoveriocu = value;
    }

    /**
     * Gets the value of the podaciOUplati property.
     * 
     * @return
     *     possible object is
     *     {@link Mt103 .PodaciOUplati }
     *     
     */
    public Mt103 .PodaciOUplati getPodaciOUplati() {
        return podaciOUplati;
    }

    /**
     * Sets the value of the podaciOUplati property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt103 .PodaciOUplati }
     *     
     */
    public void setPodaciOUplati(Mt103 .PodaciOUplati value) {
        this.podaciOUplati = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice">
     *       &lt;sequence>
     *         &lt;element name="podaci_o_banci" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "podaciOBanci"
    })
    @Entity
    public static class PodaciODuzniku
        extends TPravnoLice
    {

        @Id
        @GeneratedValue
        @XmlTransient
        private long id;

        @XmlElement(name = "podaci_o_banci", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @ManyToOne(optional = false)
        protected TPodaciBanka podaciOBanci;

        public PodaciODuzniku() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        /**
         * Gets the value of the podaciOBanci property.
         * 
         * @return
         *     possible object is
         *     {@link TPodaciBanka }
         *     
         */
        public TPodaciBanka getPodaciOBanci() {
            return podaciOBanci;
        }

        /**
         * Sets the value of the podaciOBanci property.
         * 
         * @param value
         *     allowed object is
         *     {@link TPodaciBanka }
         *     
         */
        public void setPodaciOBanci(TPodaciBanka value) {
            this.podaciOBanci = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPravnoLice">
     *       &lt;sequence>
     *         &lt;element name="podaci_o_banci" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "podaciOBanci"
    })
    @Entity
    public static class PodaciOPoveriocu
        extends TPravnoLice
    {
        @Id
        @GeneratedValue
        @XmlTransient
        private long id;

        @XmlElement(name = "podaci_o_banci", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @ManyToOne(optional = false)
        protected TPodaciBanka podaciOBanci;

        public PodaciOPoveriocu() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        /**
         * Gets the value of the podaciOBanci property.
         * 
         * @return
         *     possible object is
         *     {@link TPodaciBanka }
         *     
         */
        public TPodaciBanka getPodaciOBanci() {
            return podaciOBanci;
        }

        /**
         * Sets the value of the podaciOBanci property.
         * 
         * @param value
         *     allowed object is
         *     {@link TPodaciBanka }
         *     
         */
        public void setPodaciOBanci(TPodaciBanka value) {
            this.podaciOBanci = value;
        }

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
     *         &lt;element name="iznos">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.ftn.uns.ac.rs/tipovi>TIznos">
     *                 &lt;attribute name="valuta">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="RSD"/>
     *                       &lt;enumeration value="USD"/>
     *                       &lt;enumeration value="EUR"/>
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
     *         &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "svrhaPlacanja",
        "iznos",
        "podaciOZaduzenju",
        "podaciOOdobrenju",
        "datumNaloga",
        "datumValute"
    })
    @Entity
    @Table(name = "mt103_podaci_o_uplati")
    public static class PodaciOUplati {

        @Id
        @GeneratedValue
        @XmlTransient
        private long id;

        @XmlElement(name = "svrha_placanja", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @Column(nullable = false)
        @Size(max = 255)
        protected String svrhaPlacanja;
        @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @ManyToOne(optional = false)
        protected Mt103 .PodaciOUplati.Iznos iznos;
        @XmlElement(name = "podaci_o_zaduzenju", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @ManyToOne(optional = false)
        protected TPodaciPlacanje podaciOZaduzenju;
        @XmlElement(name = "podaci_o_odobrenju", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @ManyToOne(optional = false)
        protected TPodaciPlacanje podaciOOdobrenju;
        @XmlElement(name = "datum_naloga", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @XmlJavaTypeAdapter(DateAdapter.class)
        @Column(nullable = false)
        protected Date datumNaloga;
        @XmlElement(name = "datum_valute", namespace = "http://www.ftn.uns.ac.rs/mt103", required = true)
        @XmlJavaTypeAdapter(DateAdapter.class)
        @Column(nullable = false)
        protected Date datumValute;

        public PodaciOUplati() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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
         *     {@link Mt103 .PodaciOUplati.Iznos }
         *     
         */
        public Mt103 .PodaciOUplati.Iznos getIznos() {
            return iznos;
        }

        /**
         * Sets the value of the iznos property.
         * 
         * @param value
         *     allowed object is
         *     {@link Mt103 .PodaciOUplati.Iznos }
         *     
         */
        public void setIznos(Mt103 .PodaciOUplati.Iznos value) {
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
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="RSD"/>
         *             &lt;enumeration value="USD"/>
         *             &lt;enumeration value="EUR"/>
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
        @Table(name = "mt103_podaci_o_uplati_iznos")
        public static class Iznos {

            @Id
            @GeneratedValue
            @XmlTransient
            private long id;

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "valuta")
            //TODO: Da li rucno zameniti enumeracijom koju nam je izgenerisao iz tipova?
            protected String valuta;

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
             *     {@link String }
             *     
             */
            public String getValuta() {
                return valuta;
            }

            /**
             * Sets the value of the valuta property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValuta(String value) {
                this.valuta = value;
            }

        }

    }

}
