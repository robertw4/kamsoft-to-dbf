package pl.itr;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pl.itr.doc.Documents;
import pl.itr.xml.XmlDocuments;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        File file = new File("sample.XML");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            XmlDocuments xmlDocuments = xmlMapper.readValue(file, XmlDocuments.class);
            System.out.println(xmlDocuments.toDocuments());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}