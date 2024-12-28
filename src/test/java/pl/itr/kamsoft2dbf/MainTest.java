package pl.itr.kamsoft2dbf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.itr.kamsoft2dbf.dbf.Dbf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        var inputFile = "sample2.XML";
        var outputFile = "fa121231.dbf";
        // when
        Main.main(new String[]{inputFile, outputFile});
        // then
        try {
            var docs = Dbf.read(outputFile);
            assert docs.getDocuments().size() == 3;

            var doc1 = docs.getDocuments().get(0);
            assertEquals("100001", doc1.getDocNo());
            assertEquals("8790017162", doc1.getVatId());
            assertEquals("NEUCA SPÓ?KA AKCYJNA", doc1.getContractorName());

            var doc2 = docs.getDocuments().get(1);
            assertEquals("100002", doc2.getDocNo());
            assertEquals("527-264-39-21", doc2.getVatId());
            assertEquals("POLSKA GRUPA FARMACEUTYCZNA S.A.", doc2.getContractorName());

            var doc3 = docs.getDocuments().get(2);
            assertEquals("100003", doc3.getDocNo());
            assertEquals("", doc3.getVatId());
            assertEquals("", doc3.getContractorName());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}