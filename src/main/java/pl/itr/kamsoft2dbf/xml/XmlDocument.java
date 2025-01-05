package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Amount;
import pl.itr.kamsoft2dbf.doc.Document;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@JacksonXmlRootElement(localName = "dokument")
public class XmlDocument {
    @JacksonXmlProperty(localName = "naglowek")
    private final Header header;
    @JacksonXmlProperty(localName = "podsumowanie-fk")
    private final Summary summary;

    public XmlDocument(Header naglowek, Summary summary) {
        this.header = naglowek;
        this.summary = summary;
    }

    public XmlDocument() {
        this(null, null);
    }

    protected Document toDocument(Map<Integer, Card> cardMap) {
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
                        it.getFiscal(),
                        getInternalDocNo(),
                        it.getInternalId(),
                        getAmount(Amounts::getTransactionAmount),
                        getAmount(Amounts::getRetailAmount),
                        getAmount(Amounts::getPurchaseAmount)
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
                        it.getFiscal(),
                        getInternalDocNo(),
                        it.getInternalId(),
                        getAmount(Amounts::getTransactionAmount),
                        getAmount(Amounts::getRetailAmount),
                        getAmount(Amounts::getPurchaseAmount)
                )).orElse(null);
    }

    private String getPaymentType() {
        return Optional.ofNullable(header)
                .map(Header::getPaymentDeadline)
                .map(PaymentDeadline::getType).
                orElse(null);
    }

    private String getInternalDocNo() {
        return Optional.ofNullable(summary)
                .flatMap(Summary::getInternalDocNo)
                .orElse(null);
    }

    private Amount getAmount(Function<Amounts, Amount> getter) {
        return Optional.ofNullable(summary)
                .map(it -> it.getAmount(getter))
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Document{" +
                "header=" + header +
                '}';
    }
}
