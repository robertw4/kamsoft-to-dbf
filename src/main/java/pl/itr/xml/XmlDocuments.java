package pl.itr.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.doc.Documents;

import java.util.List;

import static java.util.stream.Collectors.toList;

@JacksonXmlRootElement(localName = "dokumenty")
public class XmlDocuments {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "dokument")
    private final List<XmlDocument> documents;
    @JacksonXmlProperty(localName = "kartoteki")
    private final Carts carts;

    public XmlDocuments() {
        this.documents = null;
        this.carts = null;
    }

    public XmlDocuments(List<XmlDocument> documents, Carts carts) {
        this.documents = documents;
        this.carts = carts;
    }

    public Documents toDocuments() {
        return new Documents(
                documents
                        .stream()
                        .map(d -> d.toDocument(carts.getCartMap()))
                        .collect(toList())
        );
    }

    @Override
    public String toString() {
        return "Documents{" +
                "documents=" + documents +
                ", carts=" + carts +
                '}';
    }
}
