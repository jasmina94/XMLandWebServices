
package com.ftn.model;

import com.ftn.util.DateAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 *         &lt;element name="podaci_o_dobavljacu" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciSubjekt"/>
 *         &lt;element name="podaci_o_kupcu" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciSubjekt"/>
 *         &lt;element name="vrednost_robe">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="vrednost_usluga">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ukupno_roba_i_usluga">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ukupan_rabat">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ukupan_porez">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="oznaka_valute">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Iznos_za_uplatu">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="uplata_na_racun">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\d{3}-\d{1,13}-\d{2}"/>
 *               &lt;length value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stavka_fakture" type="{http://www.ftn.uns.ac.rs/tipovi}TStavkaFaktura" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id_poruke">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="50"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="broj_racuna">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *             &lt;totalDigits value="6"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="datum_racuna" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="datum_valute" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "podaciODobavljacu",
    "podaciOKupcu",
    "vrednostRobe",
    "vrednostUsluga",
    "ukupnoRobaIUsluga",
    "ukupanRabat",
    "ukupanPorez",
    "oznakaValute",
    "iznosZaUplatu",
    "uplataNaRacun",
    "stavkaFakture"
})

@XmlRootElement(name = "faktura", namespace = "httl://www.ftn.uns.ac.rs/faktura")
@Entity
public class Faktura {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "podaci_o_dobavljacu", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @ManyToOne(optional = false)
    protected TPodaciSubjekt podaciODobavljacu;
    @XmlElement(name = "podaci_o_kupcu", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @ManyToOne(optional = false)
    protected TPodaciSubjekt podaciOKupcu;
    @XmlElement(name = "vrednost_robe", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal vrednostRobe;
    @XmlElement(name = "vrednost_usluga", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal vrednostUsluga;
    @XmlElement(name = "ukupno_roba_i_usluga", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal ukupnoRobaIUsluga;
    @XmlElement(name = "ukupan_rabat", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal ukupanRabat;
    @XmlElement(name = "ukupan_porez", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal ukupanPorez;
    @XmlElement(name = "oznaka_valute", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected TOznakaValute oznakaValute;
    @XmlElement(name = "Iznos_za_uplatu", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected BigDecimal iznosZaUplatu;
    @XmlElement(name = "uplata_na_racun", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @Column(nullable = false)
    protected String uplataNaRacun;
    @XmlElement(name = "stavka_fakture", namespace = "httl://www.ftn.uns.ac.rs/faktura", required = true)
    @OneToMany
    @JoinColumn(nullable = false)
    protected List<TStavkaFaktura> stavkaFakture;
    @XmlAttribute(name = "id_poruke")
    protected String idPoruke;
    @XmlAttribute(name = "broj_racuna")
    protected Long brojRacuna;
    @XmlAttribute(name = "datum_racuna")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date datumRacuna;
    @XmlAttribute(name = "datum_valute")
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date datumValute;

    public Faktura() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the podaciODobavljacu property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciSubjekt }
     *     
     */
    public TPodaciSubjekt getPodaciODobavljacu() {
        return podaciODobavljacu;
    }

    /**
     * Sets the value of the podaciODobavljacu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciSubjekt }
     *     
     */
    public void setPodaciODobavljacu(TPodaciSubjekt value) {
        this.podaciODobavljacu = value;
    }

    /**
     * Gets the value of the podaciOKupcu property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaciSubjekt }
     *     
     */
    public TPodaciSubjekt getPodaciOKupcu() {
        return podaciOKupcu;
    }

    /**
     * Sets the value of the podaciOKupcu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaciSubjekt }
     *     
     */
    public void setPodaciOKupcu(TPodaciSubjekt value) {
        this.podaciOKupcu = value;
    }

    /**
     * Gets the value of the vrednostRobe property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrednostRobe() {
        return vrednostRobe;
    }

    /**
     * Sets the value of the vrednostRobe property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrednostRobe(BigDecimal value) {
        this.vrednostRobe = value;
    }

    /**
     * Gets the value of the vrednostUsluga property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrednostUsluga() {
        return vrednostUsluga;
    }

    /**
     * Sets the value of the vrednostUsluga property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrednostUsluga(BigDecimal value) {
        this.vrednostUsluga = value;
    }

    /**
     * Gets the value of the ukupnoRobaIUsluga property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoRobaIUsluga() {
        return ukupnoRobaIUsluga;
    }

    /**
     * Sets the value of the ukupnoRobaIUsluga property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoRobaIUsluga(BigDecimal value) {
        this.ukupnoRobaIUsluga = value;
    }

    /**
     * Gets the value of the ukupanRabat property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanRabat() {
        return ukupanRabat;
    }

    /**
     * Sets the value of the ukupanRabat property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanRabat(BigDecimal value) {
        this.ukupanRabat = value;
    }

    /**
     * Gets the value of the ukupanPorez property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanPorez() {
        return ukupanPorez;
    }

    /**
     * Sets the value of the ukupanPorez property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanPorez(BigDecimal value) {
        this.ukupanPorez = value;
    }

    /**
     * Gets the value of the oznakaValute property.
     * 
     * @return
     *     possible object is
     *     {@link TOznakaValute }
     *     
     */
    public TOznakaValute getOznakaValute() {
        return oznakaValute;
    }

    /**
     * Sets the value of the oznakaValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOznakaValute }
     *     
     */
    public void setOznakaValute(TOznakaValute value) {
        this.oznakaValute = value;
    }

    /**
     * Gets the value of the iznosZaUplatu property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIznosZaUplatu() {
        return iznosZaUplatu;
    }

    /**
     * Sets the value of the iznosZaUplatu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIznosZaUplatu(BigDecimal value) {
        this.iznosZaUplatu = value;
    }

    /**
     * Gets the value of the uplataNaRacun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUplataNaRacun() {
        return uplataNaRacun;
    }

    /**
     * Sets the value of the uplataNaRacun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUplataNaRacun(String value) {
        this.uplataNaRacun = value;
    }

    /**
     * Gets the value of the stavkaFakture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkaFakture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkaFakture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TStavkaFaktura }
     * 
     * 
     */
    public List<TStavkaFaktura> getStavkaFakture() {
        if (stavkaFakture == null) {
            stavkaFakture = new ArrayList<TStavkaFaktura>();
        }
        return this.stavkaFakture;
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
     * Gets the value of the brojRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBrojRacuna() {
        return brojRacuna;
    }

    /**
     * Sets the value of the brojRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBrojRacuna(Long value) {
        this.brojRacuna = value;
    }

    /**
     * Gets the value of the datumRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDatumRacuna() {
        return datumRacuna;
    }

    /**
     * Sets the value of the datumRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDatumRacuna(Date value) {
        this.datumRacuna = value;
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

}
