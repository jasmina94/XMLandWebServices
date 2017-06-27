package com.ftn.service.implementation;

import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.model.generated.nalog_za_prenos.NalogZaPrenos;
import com.ftn.service.PDFGeneratorService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Jasmina on 6/27/17.
 */
@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    @Autowired
    private EnvironmentProperties environmentProperties;

    private static String FakturaFILE = "src/main/resources/faktura.pdf";
    private static String NalogZaPrenosFILE = "src/main/resources/nalogZaPrenos.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    @Override
    public void generisiFakturaPDF(Faktura faktura) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FakturaFILE));
            document.open();
            addMetaData(document);
            addTitlePage(document, faktura);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generisiNalogZaPrenosPDF(NalogZaPrenos nalogZaPrenos) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FakturaFILE));
            document.open();
            addMetaDataNalogZaPrenos(document);
            addTitlePageNalogZaPrenos(document, nalogZaPrenos);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitlePageNalogZaPrenos(Document document, NalogZaPrenos nalogZaPrenos) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Nalog Za Prenos", catFont));

//        addEmptyLine(preface, 2);
//        preface.add(new Paragraph(
//                "Broj Racuna fakture: " + faktura.getBrojRacuna(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Datum racuna: " + faktura.getDatumRacuna(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Datum valute: " + faktura.getDatumValute(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Id poruke: " + faktura.getIdPoruke(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Iznos za uplatu: " + faktura.getIznosZaUplatu(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Valuta: " + faktura.getOznakaValute(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Ukupan porez: " + faktura.getUkupanPorez(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Ukupan rabat: " + faktura.getUkupanRabat(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Ukupno roba i usluga: " + faktura.getUkupnoRobaIUsluga(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Uplata na racun: " + faktura.getUplataNaRacun(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Vrednost robe: " + faktura.getVrednostRobe(),
//                smallBold));
//        preface.add(new Paragraph(
//                "Vrednost usluga: " + faktura.getVrednostUsluga(),
//                smallBold));

        addEmptyLine(preface, 4);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));
        preface.add(new Paragraph(
                "Faktura generisana dana: " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));

        document.add(preface);
        // Start a new page
        document.newPage();

    }

    private void addMetaDataNalogZaPrenos(Document document) {
        document.addTitle("Nalog Za Prenos PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, Kompanija");
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Faktura PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, Kompanija");
    }

    private static void addTitlePage(Document document, Faktura faktura)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Faktura", catFont));

        addEmptyLine(preface, 2);
        preface.add(new Paragraph(
                "Broj Racuna fakture: " + faktura.getBrojRacuna(),
                smallBold));
        preface.add(new Paragraph(
                "Datum racuna: " + faktura.getDatumRacuna(),
                smallBold));
        preface.add(new Paragraph(
                "Datum valute: " + faktura.getDatumValute(),
                smallBold));
        preface.add(new Paragraph(
                "Id poruke: " + faktura.getIdPoruke(),
                smallBold));
        preface.add(new Paragraph(
                "Iznos za uplatu: " + faktura.getIznosZaUplatu(),
                smallBold));
        preface.add(new Paragraph(
                "Valuta: " + faktura.getOznakaValute(),
                smallBold));
        preface.add(new Paragraph(
                "Ukupan porez: " + faktura.getUkupanPorez(),
                smallBold));
        preface.add(new Paragraph(
                "Ukupan rabat: " + faktura.getUkupanRabat(),
                smallBold));
        preface.add(new Paragraph(
                "Ukupno roba i usluga: " + faktura.getUkupnoRobaIUsluga(),
                smallBold));
        preface.add(new Paragraph(
                "Uplata na racun: " + faktura.getUplataNaRacun(),
                smallBold));
        preface.add(new Paragraph(
                "Vrednost robe: " + faktura.getVrednostRobe(),
                smallBold));
        preface.add(new Paragraph(
                "Vrednost usluga: " + faktura.getVrednostUsluga(),
                smallBold));

        addEmptyLine(preface, 4);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));
        preface.add(new Paragraph(
                "Faktura generisana dana: " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
