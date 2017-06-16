
package com.ftn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
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
 *         &lt;element name="podaci_o_banci_poverioca" type="{http://www.ftn.uns.ac.rs/tipovi}TPodaciBanka"/>
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
    "podaciOBanciPoverioca",
    "podaciONalogu"
})
@XmlRootElement(name = "mt910", namespace = "http://www.ftn.uns.ac.rs/mt910")
@Entity
public class Mt910 {

    @Id
    @GeneratedValue
    @XmlTransient
    private long id;
    @XmlElement(name = "podaci_o_banci_poverioca", namespace = "http://www.ftn.uns.ac.rs/mt910", required = true)
    @ManyToOne(optional = false)
    protected TPodaciBanka podaciOBanciPoverioca;
    @XmlElement(name = "podaci_o_nalogu", namespace = "http://www.ftn.uns.ac.rs/mt910", required = true)
    @ManyToOne(optional = false)
    protected Mt910 .PodaciONalogu podaciONalogu;
    @XmlAttribute(name = "id_poruke")
    @Size(max = 50)
    protected String idPoruke;

    public Mt910() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
     * Gets the value of the podaciONalogu property.
     * 
     * @return
     *     possible object is
     *     {@link Mt910 .PodaciONalogu }
     *     
     */
    public Mt910 .PodaciONalogu getPodaciONalogu() {
        return podaciONalogu;
    }

    /**
     * Sets the value of the podaciONalogu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt910 .PodaciONalogu }
     *     
     */
    public void setPodaciONalogu(Mt910 .PodaciONalogu value) {
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
    public static class PodaciONalogu extends TPodaciNalog
    {

        @Id
        @GeneratedValue
        @XmlTransient
        private long id;
        @XmlAttribute(name = "id_poruke_naloga")
        @Size(max = 50)
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
