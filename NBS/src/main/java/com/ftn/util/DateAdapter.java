package com.ftn.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 * Created by JELENA on 16.6.2017.
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    public Date unmarshal(String value) {
        return (MyDatatypeConverter.parseDate(value));
    }

    public String marshal(Date value) {
        return (MyDatatypeConverter.printDate(value));
    }
}
