
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
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
 *         &lt;element name="podaci_o_banci_duznika" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
 *         &lt;element name="podaci_o_banci_poverioca" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
 *         &lt;element name="ukupan_iznos">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="sifra_valute" type="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute"/>
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
    "podaciOBanciDuznika",
    "podaciOBanciPoverioca",
    "ukupanIznos",
    "datumValute",
    "datum",
    "sifraValute"
})
@XmlRootElement(name = "mt102_zaglavlje", namespace = "http://www.ftn.uns.ac.rs/mt102header")
@Entity
public class Mt102Zaglavlje {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "podaci_o_banci_duznika", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @ManyToOne(optional = false)
    protected TPodaciBanka podaciOBanciDuznika;
    @XmlElement(name = "podaci_o_banci_poverioca", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @ManyToOne(optional = false)
    protected TPodaciBanka podaciOBanciPoverioca;
    @XmlElement(name = "ukupan_iznos", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @Column(nullable = false)
    protected BigDecimal ukupanIznos;
    @XmlElement(name = "datum_valute", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datumValute;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(nullable = false)
    protected Date datum;
    @XmlElement(name = "sifra_valute", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @XmlSchemaType(name = "string")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TOznakaValute sifraValute;
    @XmlAttribute(name = "id_poruke")
    protected String idPoruke;

    public Mt102Zaglavlje() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the podaciOBanciDuznika property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciBanka }
     *     
     */
    public TPodaciBanka getPodaciOBanciDuznika() {
        return podaciOBanciDuznika;
    }

    /**
     * Sets the value of the podaciOBanciDuznika property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciBanka }
     *     
     */
    public void setPodaciOBanciDuznika(TPodaciBanka value) {
        this.podaciOBanciDuznika = value;
    }

    /**
     * Gets the value of the podaciOBanciPoverioca property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciBanka }
     *     
     */
    public TPodaciBanka getPodaciOBanciPoverioca() {
        return podaciOBanciPoverioca;
    }

    /**
     * Sets the value of the podaciOBanciPoverioca property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciBanka }
     *     
     */
    public void setPodaciOBanciPoverioca(TPodaciBanka value) {
        this.podaciOBanciPoverioca = value;
    }

    /**
     * Gets the value of the ukupanIznos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Sets the value of the ukupanIznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanIznos(BigDecimal value) {
        this.ukupanIznos = value;
    }

    /**
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
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
     * Gets the value of the sifraValute property.
     * 
     * @return
     *     possible object is
     *     {@link TOznakaValute }
     *     
     */
    public TOznakaValute getSifraValute() {
        return sifraValute;
    }

    /**
     * Sets the value of the sifraValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOznakaValute }
     *     
     */
    public void setSifraValute(TOznakaValute value) {
        this.sifraValute = value;
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
