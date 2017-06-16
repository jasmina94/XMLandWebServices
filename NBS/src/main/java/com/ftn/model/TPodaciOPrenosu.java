
package com.ftn.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPodaciOPrenosu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodaciOPrenosu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="duznik_u_prenosu" type="{http://www.ftn.uns.ac.rs/tipovi}TPrenosUcesnik"/>
 *         &lt;element name="poverilac_u_prenosu" type="{http://www.ftn.uns.ac.rs/tipovi}TPrenosUcesnik"/>
 *         &lt;element name="iznos">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.ftn.uns.ac.rs/tipovi}TIznos">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="oznaka_valute" type="{http://www.ftn.uns.ac.rs/tipovi}TOznakaValute" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodaciOPrenosu", namespace = "http://www.ftn.uns.ac.rs/tipovi", propOrder = {

})
public class TPodaciOPrenosu {

    @XmlElement(name = "duznik_u_prenosu", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    protected TPrenosUcesnik duznikUPrenosu;
    @XmlElement(name = "poverilac_u_prenosu", namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    protected TPrenosUcesnik poverilacUPrenosu;
    @XmlElement(namespace = "http://www.ftn.uns.ac.rs/tipovi", required = true)
    protected BigDecimal iznos;
    @XmlAttribute(name = "oznaka_valute")
    protected TOznakaValute oznakaValute;

    /**
     * Gets the value of the duznikUPrenosu property.
     * 
     * @return
     *     possible object is
     *     {@link TPrenosUcesnik }
     *     
     */
    public TPrenosUcesnik getDuznikUPrenosu() {
        return duznikUPrenosu;
    }

    /**
     * Sets the value of the duznikUPrenosu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPrenosUcesnik }
     *     
     */
    public void setDuznikUPrenosu(TPrenosUcesnik value) {
        this.duznikUPrenosu = value;
    }

    /**
     * Gets the value of the poverilacUPrenosu property.
     * 
     * @return
     *     possible object is
     *     {@link TPrenosUcesnik }
     *     
     */
    public TPrenosUcesnik getPoverilacUPrenosu() {
        return poverilacUPrenosu;
    }

    /**
     * Sets the value of the poverilacUPrenosu property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPrenosUcesnik }
     *     
     */
    public void setPoverilacUPrenosu(TPrenosUcesnik value) {
        this.poverilacUPrenosu = value;
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

}
