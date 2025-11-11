package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFWriter;
import pl.itr.kamsoft2dbf.doc.Amount;
import pl.itr.kamsoft2dbf.doc.Document;
import pl.itr.kamsoft2dbf.doc.Documents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static eu.vitaliy.pl.charset.DOSCharsetProvider.MAZOVIA_CHARSET_NAME;
import static java.math.BigDecimal.ZERO;
import static pl.itr.kamsoft2dbf.dbf.Fields.*;
import static pl.itr.kamsoft2dbf.doc.Vat.VAT_23;

public class Dbf {
    public static void save(String fileName, Documents documents) throws FileNotFoundException {
        DBFWriter writer = new DBFWriter(new FileOutputStream(fileName), Charset.forName(MAZOVIA_CHARSET_NAME));
        writer.setFields(getFields());
        documents.getDocuments().forEach(document -> {
        writer.addRecord(toRecord(document));
        });
        writer.close();
    }

    public static Documents read(String fileName) throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(fileName), Charset.forName(MAZOVIA_CHARSET_NAME));
        DBFRow row;

        List<Document> documents = new ArrayList<>();
        while ((row = reader.nextRow()) != null) {
            documents.add(toDocument(row));
        }
        reader.close();
        return new Documents(documents);
    }

    public static List<DBFRow> readRows(String fileName) throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(fileName), Charset.forName(MAZOVIA_CHARSET_NAME));
        DBFRow row;

        List<DBFRow> rows = new ArrayList<>();
        while ((row = reader.nextRow()) != null) {
            rows.add(row);
        }
        reader.close();
        return rows;
    }

    public static List<DBFRow> readRowsWhere(String fileName, String fieldName, Object value) throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(fileName), Charset.forName(MAZOVIA_CHARSET_NAME));
        DBFRow row;

        List<DBFRow> rows = new ArrayList<>();
        while ((row = reader.nextRow()) != null) {
            Object fieldValue = row.getObject(fieldName);
            if (fieldValue != null && fieldValue.equals(value)) {
                rows.add(row);
            } else if (fieldValue == null && value == null) {
                rows.add(row);
            }
        }
        reader.close();
        return rows;
    }

    private static Object[] toRecord(Document document) {
    var vat23 = document.getVatAmount(VAT_23);
    BigDecimal czNet = resolveCzNet(document);
    BigDecimal czVat = resolveCzVat(document, czNet);
        BigDecimal paymentAmount = document.getPaymentAmount().orElse(null);
        BigDecimal vat23Brutto = vat23.map(Amount::getBrutto).orElse(null);
        BigDecimal p36Value = paymentAmount != null ? paymentAmount : vat23Brutto;

        if (p36Value == null) {
            p36Value = ZERO;
        }

        return new Object[] {
                "T",
                "D",
                document.getDocumentType(),
                null,
                document.getDocumentDate(),
                document.getFiscalDate(),
                document.getPaymentDate(),
                document.getFiscal(),
                document.getDocNo(),
                document.getInternalDocNo(),
                document.getInternalDocNo(),
                null,
                document.getInternalId(),
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                document.getTransactionAmount().map(Amount::getBrutto).orElse(null),
                document.getTransactionAmount().map(Amount::getNetto).orElse(null),
                document.getTransactionAmount().map(Amount::getVat).orElse(null),
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                document.getRetailAmount().map(Amount::getBrutto).orElse(ZERO),
                document.getRetailAmount().map(Amount::getNetto).orElse(ZERO),
                document.getRetailAmount().map(Amount::getVat).orElse(ZERO),
                0.0,
                vat23.map(Amount::getNetto).orElse(ZERO),
                0.0,
                vat23.map(Amount::getVat).orElse(ZERO),
                0.0,
                czNet,
                0.0,
                czVat,
                0.0,  // P35
                p36Value,
                0.0,  // P37
                p36Value,
                0.0,  // P39
                0.0,  // P40 - VAT 0% netto
                0.0,  // P41
                0.0,  // P42 - VAT 0% podatek
                document.getContractorName(),
                document.getVatId()
        };
    }

    private static BigDecimal resolveCzNet(Document document) {
        if ("SBK".equals(document.getDocumentType())) {
            return document.getCzNet().orElse(ZERO);
        }
        return document.getRetailVatAmount(VAT_23)
                .map(Amount::getNetto)
                .orElse(ZERO);
    }

    private static BigDecimal resolveCzVat(Document document, BigDecimal czNet) {
        BigDecimal retailVat = document.getRetailVatAmount(VAT_23)
                .map(Amount::getVat)
                .orElse(ZERO);

        if (!"SBK".equals(document.getDocumentType())) {
            return retailVat;
        }

        if (document.getCzNet().isEmpty() || czNet == null) {
            return retailVat;
        }

        if (retailVat.signum() != 0) {
            return retailVat;
        }

        return czNet.multiply(BigDecimal.valueOf(0.08)).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private static Document toDocument(DBFRow row) {
        Map<pl.itr.kamsoft2dbf.doc.Vat, Amount> vatAmounts = new EnumMap<>(pl.itr.kamsoft2dbf.doc.Vat.class);
    BigDecimal vat23Net = row.getBigDecimal(P28.name());
    BigDecimal vat23Vat = row.getBigDecimal(P30.name());
    vatAmounts.put(VAT_23, new Amount(
        sumNullable(vat23Net, vat23Vat),
        vat23Net,
        vat23Vat));

        Map<pl.itr.kamsoft2dbf.doc.Vat, Amount> retailVatAmounts = new EnumMap<>(pl.itr.kamsoft2dbf.doc.Vat.class);
        BigDecimal retailNet23 = row.getBigDecimal(P32.name());
        BigDecimal retailVat23 = row.getBigDecimal(P34.name());
        BigDecimal retailBrutto23 = row.getBigDecimal(P38.name());
        if (retailNet23 != null || retailVat23 != null || retailBrutto23 != null) {
            retailVatAmounts.put(VAT_23, new Amount(retailBrutto23, retailNet23, retailVat23));
        }

    return new Document(
                row.getString(NDOK.name()),
                row.getString(TYPR.name()),
                row.getString(TYPD.name()),
                row.getDate(DTA2.name()),
                row.getDate(DTA3.name()),
                row.getDate(DTA4.name()),
                row.getString(NKNT.name()),
                row.getString(NDKR.name()),
                row.getString(OSID.name()),
                new Amount(row.getBigDecimal(P11.name()), row.getBigDecimal(P12.name()), row.getBigDecimal(P13.name())),
                new Amount(row.getBigDecimal(P24.name()), row.getBigDecimal(P25.name()), row.getBigDecimal(P26.name())),
                new Amount(row.getBigDecimal(P24.name()), row.getBigDecimal(P25.name()), row.getBigDecimal(P26.name())),
                vatAmounts,
                retailVatAmounts,
                row.getBigDecimal(P36.name()),
                row.getBigDecimal(P32.name()),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                row.getString(NZWK.name()),
                row.getString(NIPK.name()),
                row.getString(NKNT.name()),
                row.getString(OPIS.name())
                );
    }

    private static BigDecimal sumNullable(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return null;
        }
        BigDecimal left = first == null ? BigDecimal.ZERO : first;
        BigDecimal right = second == null ? BigDecimal.ZERO : second;
        return left.add(right);
    }
}
