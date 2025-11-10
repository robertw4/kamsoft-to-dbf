package pl.itr.kamsoft2dbf;

import com.linuxense.javadbf.DBFRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.itr.kamsoft2dbf.dbf.Dbf;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.itr.kamsoft2dbf.doc.Vat.*;

public class XMLToDBFTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void should_convert_kamsoft_xml_file() {
        // given
        var inputFile = "KS-AOW_20251103210009_995782.XML";
        var outputFile = "FA251031.dbf";

        // when
        Main.main(new String[]{inputFile, outputFile});

        // then
        try {
            List<DBFRow> rows = Dbf.readRows(outputFile);
            assertEquals(128, rows.size());

            // Test dokumentu 1 - NDOK='1686098526'
            DBFRow doc1 = rows.get(0);
            assertEquals("1686098526", doc1.getString("NDOK"));
            assertEquals("1070047823", doc1.getString("NIPK"));
            assertEquals("NEUCA GRUPA VAT", doc1.getString("NZWK"));
            assertEquals("DOKF28463", doc1.getString("OSID"));
            assertEquals("684/2025/PZ", doc1.getString("NDKR"));
            assertEquals("2025-10-01", dateToString(doc1.getDate("DTA2")));
            assertEquals("2025-10-01", dateToString(doc1.getDate("DTA3")));
            assertEquals("2025-11-15", dateToString(doc1.getDate("DTA4")));
            assertEquals(new BigDecimal("494.18"), doc1.getBigDecimal("P11"));
            assertEquals(new BigDecimal("457.57"), doc1.getBigDecimal("P12"));
            assertEquals(new BigDecimal("36.61"), doc1.getBigDecimal("P13"));
            assertEquals(new BigDecimal("629.23"), doc1.getBigDecimal("P24"));
            assertEquals(new BigDecimal("582.62"), doc1.getBigDecimal("P25"));
            assertEquals(new BigDecimal("46.61"), doc1.getBigDecimal("P26"));
            // VAT 8%
            assertEquals(new BigDecimal("0.00"), doc1.getBigDecimal("P32"));
            assertEquals(new BigDecimal("0.00"), doc1.getBigDecimal("P34"));

            // Test dokumentu 3 - NDOK='FV/440687/25/PZ'
            DBFRow doc3 = rows.get(2);
            assertEquals("FV/440687/25/PZ", doc3.getString("NDOK"));
            assertEquals("527-264-39-21", doc3.getString("NIPK"));
            assertEquals("POLSKA GRUPA FARMACEUTYCZNA S.A.", doc3.getString("NZWK"));
            assertEquals("DOKF28465", doc3.getString("OSID"));
            assertEquals("686/2025/PZ", doc3.getString("NDKR"));
            assertEquals("2025-10-01", dateToString(doc3.getDate("DTA2")));
            assertEquals("2025-10-22", dateToString(doc3.getDate("DTA4")));
            assertEquals(new BigDecimal("144.35"), doc3.getBigDecimal("P11"));
            assertEquals(new BigDecimal("133.66"), doc3.getBigDecimal("P12"));
            assertEquals(new BigDecimal("10.69"), doc3.getBigDecimal("P13"));
            // VAT 8%
            assertEquals(new BigDecimal("133.66"), doc3.getBigDecimal("P32"));
            assertEquals(new BigDecimal("10.69"), doc3.getBigDecimal("P34"));

            // Test dokumentu 4 - NDOK='FM/125213/25/PZ' (z VAT 8%)
            DBFRow doc4 = rows.get(3);
            assertEquals("FM/125213/25/PZ", doc4.getString("NDOK"));
            assertEquals("527-264-39-21", doc4.getString("NIPK"));
            assertEquals("DOKF28466", doc4.getString("OSID"));
            assertEquals("685/2025/PZ", doc4.getString("NDKR"));
            assertEquals("2025-10-01", dateToString(doc4.getDate("DTA2")));
            assertEquals("2025-10-06", dateToString(doc4.getDate("DTA4")));
            assertEquals(new BigDecimal("683.42"), doc4.getBigDecimal("P11"));
            assertEquals(new BigDecimal("633.16"), doc4.getBigDecimal("P12"));
            assertEquals(new BigDecimal("50.26"), doc4.getBigDecimal("P13"));
            // VAT 8%
            assertEquals(new BigDecimal("620.13"), doc4.getBigDecimal("P32"));
            assertEquals(new BigDecimal("49.61"), doc4.getBigDecimal("P34"));

            // Test dokumentu 5 - NDOK='1686106209' (z VAT 23% i 8%)
            DBFRow doc5 = rows.get(4);
            assertEquals("1686106209", doc5.getString("NDOK"));
            assertEquals("1070047823", doc5.getString("NIPK"));
            assertEquals("DOKF28467", doc5.getString("OSID"));
            assertEquals("687/2025/PZ", doc5.getString("NDKR"));
            assertEquals("2025-10-02", dateToString(doc5.getDate("DTA2")));
            assertEquals("2025-11-16", dateToString(doc5.getDate("DTA4")));
            assertEquals(new BigDecimal("2027.87"), doc5.getBigDecimal("P11"));
            assertEquals(new BigDecimal("1875.57"), doc5.getBigDecimal("P12"));
            assertEquals(new BigDecimal("152.30"), doc5.getBigDecimal("P13"));
            // VAT 23% - dokument członka grupy VAT, więc VAT = 0.00
            assertEquals(new BigDecimal("0.00"), doc5.getBigDecimal("P28"));
            assertEquals(new BigDecimal("0.00"), doc5.getBigDecimal("P30"));
            // VAT 8% - dokument członka grupy VAT, więc VAT = 0.00
            assertEquals(new BigDecimal("0.00"), doc5.getBigDecimal("P32"));
            assertEquals(new BigDecimal("0.00"), doc5.getBigDecimal("P34"));

            // Test dokumentu 7 - NDOK='FM/125731/25/PZ' (z VAT 23% i 8%)
            DBFRow doc7 = rows.get(6);
            assertEquals("FM/125731/25/PZ", doc7.getString("NDOK"));
            assertEquals("527-264-39-21", doc7.getString("NIPK"));
            assertEquals("DOKF28469", doc7.getString("OSID"));
            assertEquals("689/2025/PZ", doc7.getString("NDKR"));
            assertEquals("2025-10-02", dateToString(doc7.getDate("DTA2")));
            assertEquals("2025-10-07", dateToString(doc7.getDate("DTA4")));
            assertEquals(new BigDecimal("1155.52"), doc7.getBigDecimal("P11"));
            assertEquals(new BigDecimal("1058.80"), doc7.getBigDecimal("P12"));
            assertEquals(new BigDecimal("96.72"), doc7.getBigDecimal("P13"));
            // VAT 23%
            assertEquals(new BigDecimal("82.54"), doc7.getBigDecimal("P28"));
            assertEquals(new BigDecimal("18.98"), doc7.getBigDecimal("P30"));
            // VAT 8%
            assertEquals(new BigDecimal("964.22"), doc7.getBigDecimal("P32"));
            assertEquals(new BigDecimal("77.14"), doc7.getBigDecimal("P34"));

            // Test dokumentu 8 - NDOK='GP735349FVT701/25' (z VAT 23% i 8%)
            DBFRow doc8 = rows.get(7);
            assertEquals("GP735349FVT701/25", doc8.getString("NDOK"));
            assertEquals("5252409576", doc8.getString("NIPK"));
            assertEquals("FARMACOL LOGISTYKA SP. Z O.O. ODDZIAŁ W ŻERNIKACH", doc8.getString("NZWK"));
            assertEquals("DOKF28470", doc8.getString("OSID"));
            assertEquals("690/2025/PZ", doc8.getString("NDKR"));
            assertEquals("2025-10-02", dateToString(doc8.getDate("DTA2")));
            assertEquals("2025-10-06", dateToString(doc8.getDate("DTA4")));
            assertEquals(new BigDecimal("73.16"), doc8.getBigDecimal("P11"));
            assertEquals(new BigDecimal("64.82"), doc8.getBigDecimal("P12"));
            // VAT 23%
            assertEquals(new BigDecimal("21.00"), doc8.getBigDecimal("P28"));
            assertEquals(new BigDecimal("4.83"), doc8.getBigDecimal("P30"));
            // VAT 8%
            assertEquals(new BigDecimal("43.82"), doc8.getBigDecimal("P32"));
            assertEquals(new BigDecimal("3.51"), doc8.getBigDecimal("P34"));

            // Test dokumentu 16 - NDOK='FV/K/028671/25'
            DBFRow doc16 = rows.get(15);
            assertEquals("FV/K/028671/25", doc16.getString("NDOK"));
            assertEquals("781-13-29-513", doc16.getString("NIPK"));
            assertEquals("P.P.H.U.CITO sp.z o.o.", doc16.getString("NZWK"));
            assertEquals("DOKF28480", doc16.getString("OSID"));
            assertEquals("698/2025/PZ", doc16.getString("NDKR"));
            assertEquals("2025-10-02", dateToString(doc16.getDate("DTA2")));
            assertEquals("2025-10-03", dateToString(doc16.getDate("DTA3")));
            assertEquals("2025-11-01", dateToString(doc16.getDate("DTA4")));
            assertEquals(new BigDecimal("559.16"), doc16.getBigDecimal("P11"));
            assertEquals(new BigDecimal("532.28"), doc16.getBigDecimal("P12"));
            assertEquals(new BigDecimal("26.88"), doc16.getBigDecimal("P13"));
            // VAT 8%
            assertEquals(new BigDecimal("8.82"), doc16.getBigDecimal("P32"));
            assertEquals(new BigDecimal("0.71"), doc16.getBigDecimal("P34"));

            // Test dokumentu 20 - NDOK='FM/127183/25/PZ'
            DBFRow doc20 = rows.get(19);
            assertEquals("FM/127183/25/PZ", doc20.getString("NDOK"));
            assertEquals("527-264-39-21", doc20.getString("NIPK"));
            assertEquals("POLSKA GRUPA FARMACEUTYCZNA S.A.", doc20.getString("NZWK"));
            assertEquals("DOKF28484", doc20.getString("OSID"));
            assertEquals("700/2025/PZ", doc20.getString("NDKR"));
            assertEquals("2025-10-06", dateToString(doc20.getDate("DTA2")));
            assertEquals("2025-10-13", dateToString(doc20.getDate("DTA4")));
            assertEquals(new BigDecimal("664.30"), doc20.getBigDecimal("P11"));
            assertEquals(new BigDecimal("615.22"), doc20.getBigDecimal("P12"));
            assertEquals(new BigDecimal("49.08"), doc20.getBigDecimal("P13"));
            // VAT 23%
            assertEquals(new BigDecimal("14.59"), doc20.getBigDecimal("P28"));
            assertEquals(new BigDecimal("3.36"), doc20.getBigDecimal("P30"));
            // VAT 8%
            assertEquals(new BigDecimal("522.70"), doc20.getBigDecimal("P32"));
            assertEquals(new BigDecimal("41.82"), doc20.getBigDecimal("P34"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void should_match_all_documents_with_original_dbf() {
        // given
        var inputFile = "KS-AOW_20251103210009_995782.XML";
        var outputFile = "FA251031_test.dbf";
        var originalFile = "../../../original/FA251031.dbf";

        // when
        Main.main(new String[]{inputFile, outputFile});

        // then
        try {
            List<DBFRow> generatedRows = Dbf.readRows(outputFile);
            assertEquals(128, generatedRows.size(), "Generated file should have 128 documents");

            // Sprawdź każdy dokument z wygenerowanego pliku
            for (DBFRow generatedRow : generatedRows) {
                String ndok = generatedRow.getString("NDOK");
                assertNotNull(ndok, "NDOK should not be null");

                // Znajdź odpowiadający dokument w oryginalnym pliku
                List<DBFRow> originalRows = Dbf.readRowsWhere(originalFile, "NDOK", ndok);
                assertEquals(1, originalRows.size(),
                        "Original file should contain exactly one document with OSID: " + ndok);

                DBFRow originalRow = originalRows.get(0);

                // Porównaj wszystkie kluczowe pola
                assertEquals(originalRow.getString("NDOK"), generatedRow.getString("NDOK"),
                        "NDOK mismatch for NDOK: " + ndok);
                assertEquals(originalRow.getString("NIPK"), generatedRow.getString("NIPK"),
                        "NIPK mismatch for NDOK: " + ndok);
                assertEquals(originalRow.getString("NZWK"), generatedRow.getString("NZWK"),
                        "NZWK mismatch for NDOK: " + ndok);
/*                assertEquals(
                        originalRow.getString("NDKR"),
                        generatedRow.getString("NDKR").substring(0, originalRow.getString("NDKR").length()),
                        "NDKR mismatch for OSID: " + ndok
                );*/

                // Porównaj daty
                assertEquals(dateToString(originalRow.getDate("DTA2")), dateToString(generatedRow.getDate("DTA2")),
                        "DTA2 mismatch for NDOK: " + ndok);

                if (originalRow.getDate("DTA3") != null) {
                    assertEquals(dateToString(originalRow.getDate("DTA3")), dateToString(generatedRow.getDate("DTA3")),
                            "DTA3 mismatch for NDOK: " + ndok);
                }

                assertEquals(dateToString(originalRow.getDate("DTA4")), dateToString(generatedRow.getDate("DTA4")),
                        "DTA4 mismatch for NDOK: " + ndok);

                // Porównaj kwoty - P11, P12, P13 (kwoty transakcyjne)
                assertBigDecimalEquals(originalRow.getBigDecimal("P11"), generatedRow.getBigDecimal("P11"),
                        "P11 mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P12"), generatedRow.getBigDecimal("P12"),
                        "P12 mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P13"), generatedRow.getBigDecimal("P13"),
                        "P13 mismatch for NDOK: " + ndok);

                // Porównaj P24, P25, P26 (kwoty księgowe)
                assertBigDecimalEquals(originalRow.getBigDecimal("P24"), generatedRow.getBigDecimal("P24"),
                        "P24 mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P25"), generatedRow.getBigDecimal("P25"),
                        "P25 mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P26"), generatedRow.getBigDecimal("P26"),
                        "P26 mismatch for NDOK: " + ndok);

                // Porównaj kwoty VAT 23% (P28, P30)
                assertBigDecimalEquals(originalRow.getBigDecimal("P28"), generatedRow.getBigDecimal("P28"),
                        "P28 (VAT 23% netto) mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P30"), generatedRow.getBigDecimal("P30"),
                        "P30 (VAT 23% podatek) mismatch for NDOK: " + ndok);

                // Porównaj kwoty VAT 8% (P32, P34)
                assertBigDecimalEquals(originalRow.getBigDecimal("P32"), generatedRow.getBigDecimal("P32"),
                        "P32 (VAT 8% netto) mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P34"), generatedRow.getBigDecimal("P34"),
                        "P34 (VAT 8% podatek) mismatch for NDOK: " + ndok);

                // Porównaj kwoty VAT 5% (P36, P38)
                assertBigDecimalEquals(originalRow.getBigDecimal("P36"), generatedRow.getBigDecimal("P36"),
                        "P36 (VAT 5% netto) mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P38"), generatedRow.getBigDecimal("P38"),
                        "P38 (VAT 5% podatek) mismatch for NDOK: " + ndok);

                // Porównaj kwoty zwolnione/nie podlegające (P40, P42)
                assertBigDecimalEquals(originalRow.getBigDecimal("P40"), generatedRow.getBigDecimal("P40"),
                        "P40 (zwolnione) mismatch for NDOK: " + ndok);
                assertBigDecimalEquals(originalRow.getBigDecimal("P42"), generatedRow.getBigDecimal("P42"),
                        "P42 (nie podlegające) mismatch for NDOK: " + ndok);
            }

            // Cleanup
            new java.io.File(outputFile).delete();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || actual == null) {
            assertEquals(expected, actual, message);
            return;
        }
        assertEquals(0, expected.compareTo(actual), message + " - Expected: " + expected + ", Actual: " + actual);
    }

    private String dateToString(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .toString();
    }
}
