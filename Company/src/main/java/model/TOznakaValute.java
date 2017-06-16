
package model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TOznakaValute.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TOznakaValute">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;pattern value="[A-Z]{3}"/>
 *     &lt;enumeration value="RSD"/>
 *     &lt;enumeration value="EUR"/>
 *     &lt;enumeration value="USD"/>
 *     &lt;enumeration value="CHF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TOznakaValute", namespace = "http://www.ftn.uns.ac.rs/tipovi")
@XmlEnum
public enum TOznakaValute {

    RSD,
    EUR,
    USD,
    CHF;

    public String value() {
        return name();
    }

    public static TOznakaValute fromValue(String v) {
        return valueOf(v);
    }

}
