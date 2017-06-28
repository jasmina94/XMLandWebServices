package com.ftn.service.implementation;

import com.ftn.model.database.AnalitikaIzvoda;
import com.ftn.model.database.DnevnoStanjeRacuna;
import com.ftn.model.environment.EnvironmentProperties;
import com.ftn.model.generated.faktura.Faktura;
import com.ftn.service.PDFGeneratorService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jasmina on 6/27/17.
 */
@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    @Autowired
    private EnvironmentProperties environmentProperties;

    private static String FakturaFILE = "src/main/resources/faktura.pdf";
    private static String IzvodFILE = "src/main/resources/izvod.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    @Override
    public Faktura generisiFakturaPDF(Faktura faktura) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FakturaFILE));
            document.open();
            addMetaData(document);
            addTitlePage(document, faktura);
            document.close();
            return faktura;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DnevnoStanjeRacuna generisiIzvodPDF(DnevnoStanjeRacuna dnevnoStanjeRacuna, ArrayList<AnalitikaIzvoda> analitikaIzvodaList) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(IzvodFILE));
            document.open();
            addMetaIzvodPDF(document);
            addTitlePageIzvod(document, dnevnoStanjeRacuna, analitikaIzvodaList);
            document.close();
            return dnevnoStanjeRacuna;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void addTitlePageIzvod(Document document, DnevnoStanjeRacuna dnevnoStanjeRacuna, ArrayList<AnalitikaIzvoda> analitike) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Izvod za datum: " + dnevnoStanjeRacuna.getDatum(), catFont));

        addEmptyLine(preface, 2);

        preface.add(new Paragraph("Ukupan broj promena u korist: " + dnevnoStanjeRacuna.getBrojPromenaUKorist(), blackFont));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Ukupan broj promena na teret: " + dnevnoStanjeRacuna.getBrojPromenaNaTeret(), blackFont));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Ukupno na teret: " + dnevnoStanjeRacuna.getUkupnoNaTeret(), blackFont));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Ukupno na teret: " + dnevnoStanjeRacuna.getUkupnoUKorist(), blackFont));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Prethodno stanje: " + dnevnoStanjeRacuna.getPredhodnoStanje(), blackFont));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Novo stanje: " + dnevnoStanjeRacuna.getNovoStanje(), blackFont));
        addEmptyLine(preface, 1);


        PdfPTable tabelaStavki = new PdfPTable(8);

        PdfPCell c1 = new PdfPCell(new Phrase("Datum valute"));
        tabelaStavki.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase("Racun duznika"));
        tabelaStavki.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Duznik"));
        tabelaStavki.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Racun poverioca"));
        tabelaStavki.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Poverilac"));
        tabelaStavki.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Svrha placanja"));
        tabelaStavki.addCell(c6);

        PdfPCell c7 = new PdfPCell(new Phrase("Iznos"));
        tabelaStavki.addCell(c7);

        PdfPCell c8 = new PdfPCell(new Phrase("Smer"));
        tabelaStavki.addCell(c8);

        for(AnalitikaIzvoda analitikaIzvoda : analitike){
            tabelaStavki.addCell(analitikaIzvoda.getDatumValute().toString());
            tabelaStavki.addCell(analitikaIzvoda.getRacunDuznika());
            tabelaStavki.addCell(analitikaIzvoda.getDuznik());
            tabelaStavki.addCell(analitikaIzvoda.getRacunPoverioca());
            tabelaStavki.addCell(analitikaIzvoda.getPoverilac());
            tabelaStavki.addCell(analitikaIzvoda.getSvrhaPlacanja());
            tabelaStavki.addCell(analitikaIzvoda.getIznos().toString());
            tabelaStavki.addCell((analitikaIzvoda.isSmer()) ? "Ulaz" : "Izlaz");
        }


        addEmptyLine(preface, 4);



        document.add(preface);
        addEmptyLine(preface, 2);
        document.add(tabelaStavki);
        // Start a new page
        addEmptyLine(preface, 2);

        Paragraph preface2 = new Paragraph();

        preface2.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));
        preface2.add(new Paragraph(
                "Izvod generisan dana: " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));

        document.add(preface2);

    }

    private void addMetaIzvodPDF(Document document) {
        document.addTitle("Izvod PDF");
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
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
