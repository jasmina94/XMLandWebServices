
package model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TStavkaFaktura complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TStavkaFaktura">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="redni_broj">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedShort">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="naziv_robe_usluge">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="kolicina">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="10"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="jedinica_mere">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="jedinicna_cena">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="10"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="vrednost">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="12"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="procenat_rabata">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="5"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="iznos_rabata">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="12"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="umanjeno_za_rabat">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="12"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ukupan_porez">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="12"/>
 *               &lt;fractionDigits value="2"/>
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
@XmlType(name = "TStavkaFaktura", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {
    "redniBroj",
    "nazivRobeUsluge",
    "kolicina",
    "jedinicaMere",
    "jedinicnaCena",
    "vrednost",
    "procenatRabata",
    "iznosRabata",
    "umanjenoZaRabat",
    "ukupanPorez"
})
@Entity
public class TStavkaFaktura {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "redni_broj", namespace = "http://www.ftn.uns.ac.rs/tipovi")
    @Column
    @Size(min = 1, max = 999)
    protected int redniBroj;
    @XmlElement(name = "naziv_robe_usluge", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    @Size(max = 120)
    protected String nazivRobeUsluge;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 10, fraction digits = 2
    protected BigDecimal kolicina;
    @XmlElement(name = "jedinica_mere", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    @Size(max = 6)
    protected String jedinicaMere;
    @XmlElement(name = "jedinicna_cena", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 10, fraction digits = 2
    protected BigDecimal jedinicnaCena;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 12, fraction digits = 2
    protected BigDecimal vrednost;
    @XmlElement(name = "procenat_rabata", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 5, fraction digits = 2
    protected BigDecimal procenatRabata;
    @XmlElement(name = "iznos_rabata", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 12, fraction digits = 2
    protected BigDecimal iznosRabata;
    @XmlElement(name = "umanjeno_za_rabat", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 12, fraction digits = 2
    protected BigDecimal umanjenoZaRabat;
    @XmlElement(name = "ukupan_porez", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    //TODO: Ogranicenje total digits = 12, fraction digits = 2
    protected BigDecimal ukupanPorez;

    public TStavkaFaktura () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the redniBroj property.
     * 
     */
    public int getRedniBroj() {
        return redniBroj;
    }

    /**
     * Sets the value of the redniBroj property.
     * 
     */
    public void setRedniBroj(int value) {
        this.redniBroj = value;
    }

    /**
     * Gets the value of the nazivRobeUsluge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivRobeUsluge() {
        return nazivRobeUsluge;
    }

    /**
     * Sets the value of the nazivRobeUsluge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivRobeUsluge(String value) {
        this.nazivRobeUsluge = value;
    }

    /**
     * Gets the value of the kolicina property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKolicina() {
        return kolicina;
    }

    /**
     * Sets the value of the kolicina property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKolicina(BigDecimal value) {
        this.kolicina = value;
    }

    /**
     * Gets the value of the jedinicaMere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJedinicaMere() {
        return jedinicaMere;
    }

    /**
     * Sets the value of the jedinicaMere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJedinicaMere(String value) {
        this.jedinicaMere = value;
    }

    /**
     * Gets the value of the jedinicnaCena property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJedinicnaCena() {
        return jedinicnaCena;
    }

    /**
     * Sets the value of the jedinicnaCena property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJedinicnaCena(BigDecimal value) {
        this.jedinicnaCena = value;
    }

    /**
     * Gets the value of the vrednost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVrednost() {
        return vrednost;
    }

    /**
     * Sets the value of the vrednost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVrednost(BigDecimal value) {
        this.vrednost = value;
    }

    /**
     * Gets the value of the procenatRabata property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProcenatRabata() {
        return procenatRabata;
    }

    /**
     * Sets the value of the procenatRabata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProcenatRabata(BigDecimal value) {
        this.procenatRabata = value;
    }

    /**
     * Gets the value of the iznosRabata property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIznosRabata() {
        return iznosRabata;
    }

    /**
     * Sets the value of the iznosRabata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIznosRabata(BigDecimal value) {
        this.iznosRabata = value;
    }

    /**
     * Gets the value of the umanjenoZaRabat property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUmanjenoZaRabat() {
        return umanjenoZaRabat;
    }

    /**
     * Sets the value of the umanjenoZaRabat property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUmanjenoZaRabat(BigDecimal value) {
        this.umanjenoZaRabat = value;
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

}
