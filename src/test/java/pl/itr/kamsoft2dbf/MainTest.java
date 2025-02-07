package pl.itr.kamsoft2dbf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.itr.kamsoft2dbf.dbf.Dbf;
import pl.itr.kamsoft2dbf.doc.Amount;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.itr.kamsoft2dbf.doc.Vat.VAT_23;
import static pl.itr.kamsoft2dbf.doc.Vat.VAT_8;

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
            assertEquals("778/2024/PZ", doc1.getInternalDocNo());
            assertEquals("DOKF27305", doc1.getInternalId());
            assertEquals(new BigDecimal("18.82"), doc1.getTransactionAmount().map(Amount::getNetto).get());
            assertEquals(new BigDecimal("20.33"), doc1.getTransactionAmount().map(Amount::getBrutto).get());
            assertEquals(new BigDecimal("1.51"), doc1.getTransactionAmount().map(Amount::getVat).get());
            assertEquals(new BigDecimal("26.35"), doc1.getRetailAmount().map(Amount::getNetto).get());
            assertEquals(new BigDecimal("28.46"), doc1.getRetailAmount().map(Amount::getBrutto).get());
            assertEquals(new BigDecimal("2.11"), doc1.getRetailAmount().map(Amount::getVat).get());
            assertEquals(new BigDecimal("258.20"), doc1.getVatAmount(VAT_8).map(Amount::getNetto).get());
            assertEquals(new BigDecimal("20.65"), doc1.getVatAmount(VAT_8).map(Amount::getVat).get());
            assertEquals(new BigDecimal("9.03"), doc1.getVatAmount(VAT_23).map(Amount::getNetto).get());
            assertEquals(new BigDecimal("2.08"), doc1.getVatAmount(VAT_23).map(Amount::getVat).get());

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
            assertEquals("", doc2.getInternalDocNo());
            assertEquals("DOKF27315", doc2.getInternalId());

            var doc3 = docs.getDocuments().get(2);
            assertEquals("SBK 05.11.2024", doc3.getDocNo());
            assertEquals("", doc3.getVatId());
            assertEquals("", doc3.getContractorName());
            assertEquals("D", doc3.getPaymentDeadlineType());
            assertEquals("SBK", doc3.getDocumentType());
            assertEquals("2024-11-04", dateToString(doc3.getDocumentDate()));
            assertEquals("2024-11-05", dateToString(doc3.getFiscalDate()));
            assertEquals("2024-11-04", dateToString(doc3.getPaymentDate()));
            assertEquals("", doc3.getFiscal());
            assertEquals("", doc3.getInternalDocNo());
            assertEquals("SBKB20241105", doc3.getInternalId());

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
            assertEquals("7304/2024/WZ", doc4.getInternalDocNo());
            assertEquals("DOKF27318", doc4.getInternalId());
            assertEquals(new BigDecimal("45.33"), doc4.getTransactionAmount().map(Amount::getNetto).get());
            assertEquals(new BigDecimal("48.96"), doc4.getTransactionAmount().map(Amount::getBrutto).get());
            assertEquals(new BigDecimal("3.63"), doc4.getTransactionAmount().map(Amount::getVat).get());
            assertEquals(new BigDecimal("39.99"), doc4.getRetailAmount().map(Amount::getNetto).get());
            assertEquals(new BigDecimal("43.19"), doc4.getRetailAmount().map(Amount::getBrutto).get());
            assertEquals(new BigDecimal("3.20"), doc4.getRetailAmount().map(Amount::getVat).get());

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
            assertEquals("", doc5.getInternalDocNo());
            assertEquals("DOKF27371", doc5.getInternalId());

            var doc6 = docs.getDocuments().get(5);
            assertEquals("KRF 21.11.2024", doc6.getDocNo());
            assertEquals("", doc6.getVatId());
            assertEquals("", doc6.getContractorName());
            assertEquals("D", doc6.getPaymentDeadlineType());
            assertEquals("KRF", doc6.getDocumentType());
            assertEquals("2024-11-29", dateToString(doc6.getDocumentDate()));
            assertNull(doc6.getFiscalDate());
            assertEquals("2024-11-29", dateToString(doc6.getPaymentDate()));
            assertEquals("", doc6.getFiscal());
            assertEquals("", doc6.getInternalDocNo());
            assertEquals("KRFF20241105", doc6.getInternalId());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String dateToString(Date date) {
        return date.toInstant().atZone(ZoneId.of("CET")).toString().substring(0, 10);
    }
}