
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
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/mt102header}mt102_zaglavlje"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/mt102body}mt102_telo" maxOccurs="unbounded"/>
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
    "mt102Zaglavlje",
    "mt102Telo"
})
@XmlRootElement(name = "mt102", namespace = "http://www.ftn.uns.ac.rs/mt102")
@Entity
public class Mt102 {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "mt102_zaglavlje", namespace = "http://www.ftn.uns.ac.rs/mt102header", required = true)
    @ManyToOne(optional = false)
    protected Mt102Zaglavlje mt102Zaglavlje;
    @XmlElement(name = "mt102_telo", namespace = "http://www.ftn.uns.ac.rs/mt102body", required = true)
    @OneToMany
    @JoinColumn(nullable = false)
    protected List<Mt102Telo> mt102Telo;

    public Mt102() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the mt102Zaglavlje property.
     * 
     * @return
     *     possible object is
     *     {@link Mt102Zaglavlje }
     *     
     */
    public Mt102Zaglavlje getMt102Zaglavlje() {
        return mt102Zaglavlje;
    }

    /**
     * Sets the value of the mt102Zaglavlje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt102Zaglavlje }
     *     
     */
    public void setMt102Zaglavlje(Mt102Zaglavlje value) {
        this.mt102Zaglavlje = value;
    }

    /**
     * Gets the value of the mt102Telo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mt102Telo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMt102Telo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Mt102Telo }
     * 
     * 
     */
    public List<Mt102Telo> getMt102Telo() {
        if (mt102Telo == null) {
            mt102Telo = new ArrayList<Mt102Telo>();
        }
        return this.mt102Telo;
    }

}
