package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFWriter;
import pl.itr.kamsoft2dbf.doc.Document;
import pl.itr.kamsoft2dbf.doc.Documents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static eu.vitaliy.pl.charset.DOSCharsetProvider.MAZOVIA_CHARSET_NAME;
import static pl.itr.kamsoft2dbf.dbf.Fields.*;

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

    private static Object[] toRecord(Document document) {
        return new Object[] {
                "T",
                "D",
                document.getDocumentType(),
                null,
                document.getDocumentDate(),
                document.getReceimpmentDate(),
                document.getDocNo(),
                document.getContractorName(),
                document.getVatId()
        };
    }

    private static Document toDocument(DBFRow row) {
        return new Document(
                row.getString(NZWK.name()),
                row.getString(NIPK.name()),
                row.getString(NRDOK.name()),
                row.getString(TYPR.name()),
                row.getString(TYPD.name()),
                row.getDate(DTA2.name()),
                row.getDate(DTA3.name())
        );
    }
}
