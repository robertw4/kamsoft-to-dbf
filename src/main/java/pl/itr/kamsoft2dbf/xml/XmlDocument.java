package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Amount;
import pl.itr.kamsoft2dbf.doc.Document;
import pl.itr.kamsoft2dbf.doc.Vat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@JacksonXmlRootElement(localName = "dokument")
public class XmlDocument {
    @JacksonXmlProperty(localName = "naglowek")
    private final Header header;
    @JacksonXmlProperty(localName = "naglowek-kor")
    private final CorrectionHeader correctionHeader;
    @JacksonXmlProperty(localName = "podsumowanie-fk")
    private final Summary summary;

    public XmlDocument(Header naglowek, CorrectionHeader correctionHeader, Summary summary) {
        this.header = naglowek;
        this.correctionHeader = correctionHeader;
        this.summary = summary;
    }

    public XmlDocument() {
        this(null, null, null);
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
                .map(document -> document.setVatId(card.getVatId()))
                .map(document -> document.setContractorInternalId(card.getInternalId()));
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
            resolvePaymentDate(),
                        header.getFiscal(),
                        getInternalDocNo(),
                        header.getInternalId(),
                        getAmount(Amounts::getTransactionAmount),
                        getAmount(Amounts::getRetailAmount),
                        getAmount(Amounts::getPurchaseAmount),
                        getVatAmounts(),
                        getRetailVatAmounts(),
                        getPaymentAmount(),
            getCzNet(),
            getCzRxNet(),
            getCzRxwNet(),
            getCzOtcNet(),
            getCzOtcwNet(),
            getRxNet(),
            getRxwNet(),
            getOtcNet(),
            getOtcwNet(),
                        null,
                        null,
                        null,
                        header.getRemarks()
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

    private Map<Vat, Amount> getRetailVatAmounts() {
        return Optional.ofNullable(summary)
                .map(Summary::getRetailVatAmounts)
                .orElse(Map.of());
    }

    private BigDecimal getPaymentAmount() {
        return Optional.ofNullable(summary)
                .map(Summary::getPaymentAmount)
                .orElse(null);
    }

    private BigDecimal getCzNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getCzNet)
                .orElse(null);
    }

    private BigDecimal getCzRxNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getCzRxNet)
                .orElse(null);
    }

    private BigDecimal getCzRxwNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getCzRxwNet)
                .orElse(null);
    }

    private BigDecimal getCzOtcNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getCzOtcNet)
                .orElse(null);
    }

    private BigDecimal getCzOtcwNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getCzOtcwNet)
                .orElse(null);
    }

    private BigDecimal getRxNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getRxNet)
                .orElse(null);
    }

    private BigDecimal getRxwNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getRxwNet)
                .orElse(null);
    }

    private BigDecimal getOtcNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getOtcNet)
                .orElse(null);
    }

    private BigDecimal getOtcwNet() {
        return Optional.ofNullable(summary)
                .map(Summary::getOtcwNet)
                .orElse(null);
    }

    private Date resolvePaymentDate() {
        Date paymentDate = Optional.ofNullable(header)
                .map(Header::getPaymentDate)
                .orElse(null);

        if (Optional.ofNullable(header).map(Header::isCorrection).orElse(false)) {
            Date correctionIssueDate = Optional.ofNullable(correctionHeader)
                    .flatMap(CorrectionHeader::getIssueDate)
                    .orElse(null);

            if (correctionIssueDate != null) {
                return correctionIssueDate;
            }
        }

        return paymentDate;
    }

    @Override
    public String toString() {
        return "Document{" +
                "header=" + header +
                '}';
    }
}
