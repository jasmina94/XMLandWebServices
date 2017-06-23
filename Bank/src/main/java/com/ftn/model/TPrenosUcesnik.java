
package com.ftn.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TPrenosUcesnik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPrenosUcesnik">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="racun_ucesnika" type="{http://www.ftn.uns.ac.rs/tipovi}TBrojRacuna"/>
 *         &lt;element name="model_prenosa" type="{http://www.ftn.uns.ac.rs/tipovi}TModel"/>
 *         &lt;element name="poziv_na_broj" type="{http://www.ftn.uns.ac.rs/tipovi}TPozivNaBroj"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPrenosUcesnik", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {

})
@Entity
public class TPrenosUcesnik {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "racun_ucesnika", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    protected String racunUcesnika;
    @XmlElement(name = "model_prenosa", namespace = "http://www.ftn.uns.ac.rs/tipovi")
    @XmlSchemaType(name = "unsignedInt")
    protected long modelPrenosa;
    @XmlElement(name = "poziv_na_broj", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    protected String pozivNaBroj;


    public TPrenosUcesnik() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the value of the racunUcesnika property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRacunUcesnika() {
        return racunUcesnika;
    }

    /**
     * Sets the value of the racunUcesnika property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRacunUcesnika(String value) {
        this.racunUcesnika = value;
    }

    /**
     * Gets the value of the modelPrenosa property.
     * 
     */
    public long getModelPrenosa() {
        return modelPrenosa;
    }

    /**
     * Sets the value of the modelPrenosa property.
     * 
     */
    public void setModelPrenosa(long value) {
        this.modelPrenosa = value;
    }

    /**
     * Gets the value of the pozivNaBroj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPozivNaBroj() {
        return pozivNaBroj;
    }

    /**
     * Sets the value of the pozivNaBroj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPozivNaBroj(String value) {
        this.pozivNaBroj = value;
    }

}
