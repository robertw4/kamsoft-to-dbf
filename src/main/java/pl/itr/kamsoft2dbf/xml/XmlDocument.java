package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Amount;
import pl.itr.kamsoft2dbf.doc.Document;
import pl.itr.kamsoft2dbf.doc.Vat;

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
                .flatMap(this::toDocument)
                .orElse(toDocument().orElse(null));
    }

    private Optional<Document> toDocument(Card card) {
        return toDocument()
                .map(document -> document.setContractorName(card.getFullName()))
                .map(document -> document.setVatId(card.getVatId()));
    }

    private Optional<Document> toDocument() {
        return Optional.ofNullable(header)
                .filter(header -> header.toDocumentType() != null)
                .map(header -> new Document(
                        header.getDocNo(),
                        getPaymentType(),
                        header.toDocumentType(),
                        header.getDocumentDate(),
                        header.getFiscalDate(),
                        header.getPaymentDate(),
                        header.getFiscal(),
                        getInternalDocNo(),
                        header.getInternalId(),
                        getAmount(Amounts::getTransactionAmount),
                        getAmount(Amounts::getRetailAmount),
                        getAmount(Amounts::getPurchaseAmount),
                        getVatAmounts(),
                        null,
                        null
                ));
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
                .map(summary -> summary.getAmount(getter))
                .orElse(null);
    }

    private Map<Vat, Amount> getVatAmounts() {
        return Optional.ofNullable(summary)
                .map(Summary::getVatAmounts)
                .orElse(Map.of());
    }

    @Override
    public String toString() {
        return "Document{" +
                "header=" + header +
                '}';
    }
}
