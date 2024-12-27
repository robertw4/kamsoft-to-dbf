package pl.itr.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.doc.Document;

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

    public Document toDocument(Map<Integer, Cart> cartMap) {
        return Optional.ofNullable(header)
                .map(Header::getKontrahent)
                .map(cartMap::get)
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
