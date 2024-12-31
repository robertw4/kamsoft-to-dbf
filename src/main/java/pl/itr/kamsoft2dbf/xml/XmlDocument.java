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
                .map(this::toDocument)
                .orElse(toDocument());
    }

    private Document toDocument(Card card) {
        return new Document(
                card.getFullName(),
                card.getVatId(),
                header.getDocNo(),
                getPaymentType(),
                header.toDocumentType(),
                header.getDocumentDate(),
                header.getFiscalDate(),
                header.getPaymentDate(),
                header.getFiscal()
        );
    }

    private Document toDocument() {
        return new Document(
                header.getDocNo(),
                getPaymentType(),
                header.toDocumentType(),
                header.getDocumentDate(),
                header.getFiscalDate(),
                header.getPaymentDate(),
                header.getFiscal()
        );
    }

    private String getPaymentType() {
        return Optional.ofNullable(header)
                .map(Header::getPaymentDeatline)
                .map(PaymentDeadline::getType).
                orElse(null);
    }

    @Override
    public String toString() {
        return "Document{" +
                "header=" + header +
                '}';
    }
}
