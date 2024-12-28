package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Document;

import java.util.Map;
import java.util.Optional;

@JacksonXmlRootElement(localName = "dokument")
public class XmlDocument {
    @JacksonXmlProperty(localName = "naglowek")
    private final Header header;

    public XmlDocument() {
        this.header = null;
    }

    public XmlDocument(Header naglowek) {
        this.header = naglowek;
    }

    public Document toDocument(Map<Integer, Card> cardMap) {
        return Optional.ofNullable(header)
                .map(Header::getContractor)
                .map(cardMap::get)
                .map(d -> new Document(d.getName(), d.getVatId(), header.getDocNo()))
                .orElse( new Document(header.getDocNo()));
    }

    @Override
    public String toString() {
        return "Document{" +
                "header=" + header +
                '}';
    }
}
