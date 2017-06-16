
package com.ftn.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.*;


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
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/zaglavljePreseka}zaglavlje_preseka"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/stavkaPreseka}stavka_preseka" maxOccurs="unbounded"/>
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
    "zaglavljePreseka",
    "stavkaPreseka"
})
@XmlRootElement(name = "presek", namespace = "http://www.ftn.uns.ac.rs/presek")
@Entity
public class Presek {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "zaglavlje_preseka", namespace = "http://www.ftn.uns.ac.rs/zaglavljePreseka", required = true)
    @ManyToOne(optional = false)
    protected ZaglavljePreseka zaglavljePreseka;
    @XmlElement(name = "stavka_preseka", namespace = "http://www.ftn.uns.ac.rs/stavkaPreseka", required = true)
    @OneToMany
    @JoinColumn(nullable = false)
    //TODO: proveriti sa ostalima za joinColumn
    protected List<StavkaPreseka> stavkaPreseka;

    public Presek() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the zaglavljePreseka property.
     * 
     * @return
     *     possible object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public ZaglavljePreseka getZaglavljePreseka() {
        return zaglavljePreseka;
    }

    /**
     * Sets the value of the zaglavljePreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public void setZaglavljePreseka(ZaglavljePreseka value) {
        this.zaglavljePreseka = value;
    }

    /**
     * Gets the value of the stavkaPreseka property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkaPreseka property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkaPreseka().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StavkaPreseka }
     * 
     * 
     */
    public List<StavkaPreseka> getStavkaPreseka() {
        if (stavkaPreseka == null) {
            stavkaPreseka = new ArrayList<StavkaPreseka>();
        }
        return this.stavkaPreseka;
    }

}
