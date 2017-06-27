package com.ftn.service;

import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;

/**
 * Created by Jasmina on 6/27/17.
 */
public interface PDFGeneratorService {

    Faktura generisiFakturaPDF(Faktura faktura);

    void generisiNalogZaPrenosPDF(NalogZaPrenos nalogZaPrenos);
}
