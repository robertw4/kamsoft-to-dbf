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
        return Optional.ofNullable(header)
                .filter(it -> it.toDocumentType() != null)
                .map(it -> new Document(
                        card.getFullName(),
                        card.getVatId(),
                        it.getDocNo(),
                        getPaymentType(),
                        it.toDocumentType(),
                        it.getDocumentDate(),
                        it.getFiscalDate(),
                        it.getPaymentDate(),
                        it.getFiscal()
                )).orElse(null);
    }

    private Document toDocument() {
        return Optional.ofNullable(header)
                .filter(it -> it.toDocumentType() != null)
                .map(it -> new Document(
                        it.getDocNo(),
                        getPaymentType(),
                        it.toDocumentType(),
                        it.getDocumentDate(),
                        it.getFiscalDate(),
                        it.getPaymentDate(),
                        it.getFiscal()
                )).orElse(null);
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
