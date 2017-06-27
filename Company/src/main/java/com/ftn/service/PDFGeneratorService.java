package com.ftn.service;

import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;

/**
 * Created by Jasmina on 6/27/17.
 */
public interface PDFGeneratorService {

    public void generisiFakturaPDF(Faktura faktura);

    public void generisiNalogZaPrenosPDF(NalogZaPrenos nalogZaPrenos);
}
