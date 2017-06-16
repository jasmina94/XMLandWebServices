
package com.ftn.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for TPodaciBanka complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodaciBanka">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="swift_kod">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="obracunski_racun" type="{http://www.ftn.uns.ac.rs/tipovi}TBrojRacuna"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodaciBanka", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {
    "swiftKod",
    "obracunskiRacun"
})
@Entity
public class TPodaciBanka {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "swift_kod", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false, length = 8)
    protected String swiftKod;
    @XmlElement(name = "obracunski_racun", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    @Column(nullable = false)
    protected String obracunskiRacun;

    public TPodaciBanka() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the value of the swiftKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSwiftKod() {
        return swiftKod;
    }

    /**
     * Sets the value of the swiftKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSwiftKod(String value) {
        this.swiftKod = value;
    }

    /**
     * Gets the value of the obracunskiRacun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObracunskiRacun() {
        return obracunskiRacun;
    }

    /**
     * Sets the value of the obracunskiRacun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObracunskiRacun(String value) {
        this.obracunskiRacun = value;
    }

}
