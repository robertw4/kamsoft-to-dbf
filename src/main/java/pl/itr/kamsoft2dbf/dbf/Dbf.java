package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import pl.itr.kamsoft2dbf.doc.Document;
import pl.itr.kamsoft2dbf.doc.Documents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.linuxense.javadbf.DBFDataType.CHARACTER;

public class Dbf {
    private static final DBFField[] struct = {
            new DBFField("NRDOK", CHARACTER, 20),
            new DBFField("NZWK", CHARACTER, 100),
            new DBFField("NIPK", CHARACTER, 15),
    };

    public static void save(String fileName, Documents documents) throws FileNotFoundException {
        DBFWriter writer = new DBFWriter(new FileOutputStream(fileName));
        writer.setFields(struct);
        documents.getDocuments().forEach(document -> {
            writer.addRecord(toRecord(document));
        });
        writer.close();
    }

    private static Object[] toRecord(Document document) {
        return new Object[] {
                document.getDocNo(),
                document.getContractorName(),
                document.getVatId()
        };
    }
}
