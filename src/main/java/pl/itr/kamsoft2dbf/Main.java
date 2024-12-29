package pl.itr.kamsoft2dbf;

import pl.itr.kamsoft2dbf.dbf.Dbf;
import pl.itr.kamsoft2dbf.xml.XmlDocuments;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar kamsoft2dbf.jar <inputFile> <outputFile>");
            return;
        }
        final var inputFile = args[0];
        final var outputFile = args[1];
        try {
            Dbf.save(outputFile, XmlDocuments.fromFile(inputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}