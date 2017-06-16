
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TPodaciNalog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodaciNalog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="iznos">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.ftn.uns.ac.rs/tipovi>TIznos">
 *                 &lt;attribute name="valuta" type="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
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
@XmlType(name = "TPodaciNalog", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {
    "datumValute",
    "iznos"
})
@Entity
public class TPodaciNalog {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "datum_valute", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datumValute;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @ManyToOne(optional = false)
    protected TPodaciNalog.Iznos iznos;


    public TPodaciNalog() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
     * Gets the value of the iznos property.
     *
     * @return
     *     possible object is
     *     {@link TPodaciNalog.Iznos }
     *
     */
    public TPodaciNalog.Iznos getIznos() {
        return iznos;
    }

    /**
     * Sets the value of the iznos property.
     *
     * @param value
     *     allowed object is
     *     {@link TPodaciNalog.Iznos }
     *
     */
    public void setIznos(TPodaciNalog.Iznos value) {
        this.iznos = value;
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
     *       &lt;attribute name="valuta" type="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute" />
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
    @Table(name = "tpodaci_nalog_iznos")
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
