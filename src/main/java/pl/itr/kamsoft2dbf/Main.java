package pl.itr.kamsoft2dbf;

import pl.itr.kamsoft2dbf.dbf.Dbf;
import pl.itr.kamsoft2dbf.xml.XmlDocuments;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Dbf.save(
                    "fa121212.dbf",
                    XmlDocuments.fromFile("sample.XML")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}