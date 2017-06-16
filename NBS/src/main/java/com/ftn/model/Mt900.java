
package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
 *         &lt;element name="podaci_o_banci_duznika" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
 *         &lt;element name="podaci_o_nalogu">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPodaciNalog">
 *                 &lt;attribute name="id_poruke_naloga">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;maxLength value="50"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
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
    "podaciOBanciDuznika",
    "podaciONalogu"
})
@XmlRootElement(name = "mt900", namespace = "http://www.ftn.uns.ac.rs/mt900")
@Entity
public class Mt900 {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;

    @XmlElement(name = "podaci_o_banci_duznika", namespace = "http://www.ftn.uns.ac.rs/mt900", required = true)
    @ManyToOne(optional = false)
    protected TPodaciBanka podaciOBanciDuznika;
    @XmlElement(name = "podaci_o_nalogu", namespace = "http://www.ftn.uns.ac.rs/mt900", required = true)
    @ManyToOne(optional = false)
    protected Mt900 .PodaciONalogu podaciONalogu;
    @XmlAttribute(name = "id_poruke")
    protected String idPoruke;

    public Mt900() {}

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
     * Gets the value of the podaciONalogu property.
     * 
     * @return
     *     possible object is
     *     {@link Mt900 .PodaciONalogu }
     *     
     */
    public Mt900 .PodaciONalogu getPodaciONalogu() {
        return podaciONalogu;
    }

    /**
     * Sets the value of the podaciONalogu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt900 .PodaciONalogu }
     *     
     */
    public void setPodaciONalogu(Mt900 .PodaciONalogu value) {
        this.podaciONalogu = value;
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
     *     &lt;extension base="{http://www.ftn.uns.ac.rs/tipovi}TPodaciNalog">
     *       &lt;attribute name="id_poruke_naloga">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;maxLength value="50"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    @Entity
    public static class PodaciONalogu
        extends TPodaciNalog
    {
        @Id
        @GeneratedValue
        @XmlTransient
        private long id;

        @XmlAttribute(name = "id_poruke_naloga")
        protected String idPorukeNaloga;

        public PodaciONalogu() {}

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
        /**
         * Gets the value of the idPorukeNaloga property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdPorukeNaloga() {
            return idPorukeNaloga;
        }

        /**
         * Sets the value of the idPorukeNaloga property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdPorukeNaloga(String value) {
            this.idPorukeNaloga = value;
        }

    }

}
