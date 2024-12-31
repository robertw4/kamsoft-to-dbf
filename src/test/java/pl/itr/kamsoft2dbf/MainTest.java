package pl.itr.kamsoft2dbf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.itr.kamsoft2dbf.dbf.Dbf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MainTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void should_show_usage() {
        // when
        Main.main(new String[]{});
        // then
        assert outputStreamCaptor.toString().contains("Usage: java -jar kamsoft2dbf.jar <inputFile> <outputFile>");
    }

    @Test
    void should_convert() {
        // given
        var inputFile = "sample.xml";
        var outputFile = "fa121231.dbf";
        // when
        Main.main(new String[]{inputFile, outputFile});
        // then
        try {
            var docs = Dbf.read(outputFile);
            assert docs.getDocuments().size() == 6;

            var doc1 = docs.getDocuments().get(0);
            assertEquals("100001", doc1.getDocNo());
            assertEquals("8790017162", doc1.getVatId());
            assertEquals("NEUCA SPÓŁKA AKCYJNA", doc1.getContractorName());
            assertEquals("D", doc1.getPaymentDeadlineType());
            assertEquals("FZV", doc1.getDocumentType());
            assertEquals("2024-10-31", dateToString(doc1.getDocumentDate()));
            assertEquals("2024-11-02", dateToString(doc1.getFiscalDate()));
            assertEquals("2024-12-16", dateToString(doc1.getPaymentDate()));
            assertEquals("", doc1.getFiscal());

            var doc2 = docs.getDocuments().get(1);
            assertEquals("100002", doc2.getDocNo());
            assertEquals("527-264-39-21", doc2.getVatId());
            assertEquals("POLSKA GRUPA FARMACEUTYCZNA S.A.", doc2.getContractorName());
            assertEquals("D", doc2.getPaymentDeadlineType());
            assertEquals("FZV", doc2.getDocumentType());
            assertEquals("2024-11-01", dateToString(doc2.getDocumentDate()));
            assertEquals("2024-11-01", dateToString(doc2.getFiscalDate()));
            assertEquals("2024-12-01", dateToString(doc2.getPaymentDate()));
            assertEquals("", doc2.getFiscal());

            var doc3 = docs.getDocuments().get(2);
            assertEquals("100003", doc3.getDocNo());
            assertEquals("", doc3.getVatId());
            assertEquals("", doc3.getContractorName());
            assertEquals("D", doc3.getPaymentDeadlineType());
            assertEquals("SBK", doc3.getDocumentType());
            assertEquals("2024-11-04", dateToString(doc3.getDocumentDate()));
            assertEquals("2024-11-05", dateToString(doc3.getFiscalDate()));
            assertNull(doc3.getPaymentDate());
            assertEquals("", doc3.getFiscal());

            var doc4 = docs.getDocuments().get(3);
            assertEquals("76/2024", doc4.getDocNo());
            assertEquals("", doc4.getVatId());
            assertEquals("Jan Kowalski", doc4.getContractorName());
            assertEquals("D", doc4.getPaymentDeadlineType());
            assertEquals("FSV", doc4.getDocumentType());
            assertEquals("2024-11-11", dateToString(doc4.getDocumentDate()));
            assertEquals("2024-11-11", dateToString(doc4.getFiscalDate()));
            assertEquals("2024-12-16", dateToString(doc4.getPaymentDate()));
            assertEquals("1", doc4.getFiscal());

            var doc5 = docs.getDocuments().get(4);
            assertEquals("2660707383", doc5.getDocNo());
            assertEquals("527-264-39-21", doc5.getVatId());
            assertEquals("POLSKA GRUPA FARMACEUTYCZNA S.A.", doc5.getContractorName());
            assertEquals("D", doc5.getPaymentDeadlineType());
            assertEquals("KZV", doc5.getDocumentType());
            assertEquals("2024-11-28", dateToString(doc5.getDocumentDate()));
            assertEquals("2024-11-28", dateToString(doc5.getFiscalDate()));
            assertEquals("2024-12-16", dateToString(doc5.getPaymentDate()));
            assertEquals("", doc5.getFiscal());

            var doc6 = docs.getDocuments().get(5);
            assertEquals("20241121", doc6.getDocNo());
            assertEquals("", doc6.getVatId());
            assertEquals("", doc6.getContractorName());
            assertEquals("D", doc6.getPaymentDeadlineType());
            assertEquals("KRF", doc6.getDocumentType());
            assertEquals("2024-11-29", dateToString(doc6.getDocumentDate()));
            assertNull(doc6.getFiscalDate());
            assertNull(doc6.getPaymentDate());
            assertEquals("", doc6.getFiscal());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String dateToString(Date date) {
        return date.toInstant().atZone(ZoneId.of("CET")).toString().substring(0, 10);
    }
}