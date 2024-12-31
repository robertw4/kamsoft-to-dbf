package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Documents;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "dokumenty")
public class XmlDocuments {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "dokument")
    private final List<XmlDocument> documents;
    @JacksonXmlProperty(localName = "kartoteki")
    private final Cards cards;

    public XmlDocuments() {
        this.documents = null;
        this.cards = null;
    }

    public XmlDocuments(List<XmlDocument> documents, Cards cards) {
        this.documents = documents;
        this.cards = cards;
    }

    public static Documents fromFile(String fileName) throws IOException {
        File file = new File(fileName);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper.readValue(file, XmlDocuments.class).toDocuments();
    }

    private Documents toDocuments() {
        return new Documents(
                documents
                        .stream()
                        .map(xmlDocument -> xmlDocument.toDocument(cards.getCardMap()))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    @Override
    public String toString() {
        return "Documents{" +
                "documents=" + documents +
                ", cards=" + cards +
                '}';
    }
}
