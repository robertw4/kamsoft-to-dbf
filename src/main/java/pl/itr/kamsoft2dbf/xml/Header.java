package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@JacksonXmlRootElement(localName = "naglowek")
public class Header {
    @JacksonXmlProperty(localName = "kontrahent")
    private final Integer contractor;
    @JacksonXmlProperty(localName = "nr-dokumentu")
    private final String docNo;
    @JacksonXmlProperty(localName = "termin-platnosci")
    private final PaymentDeadline paymentDeadline;
    @JacksonXmlProperty(localName = "typ-dokumentu")
    private final String documentType;
    @JacksonXmlProperty(localName = "data-wystawienia")
    private final String documentDate;
    @JacksonXmlProperty(localName = "data-otrzymania")
    private final String receipmentDate;
    @JacksonXmlProperty(localName = "status-fiskalny")
    private final String fiscal;

    public Header(
            Integer contractor,
            String docNo,
            PaymentDeadline paymentDeadline,
            String documentType,
            String documentDate,
            String receipmentDate, String fiscal
    ) {
        this.contractor = contractor;
        this.docNo = docNo;
        this.paymentDeadline = paymentDeadline;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.receipmentDate = receipmentDate;
        this.fiscal = fiscal;
    }

    public Header() {
        this(null, null, null, null, null, null, null);
    }

    public Integer getContractor() {
        return contractor;
    }

    public String getDocNo() {
        return docNo;
    }

    public PaymentDeadline getPaymentDeatline() {
        return paymentDeadline;
    }

    public String toDocumentType() {
        return switch (documentType) {
            case "FZ" -> "FZV";
            case "SBKF" -> "SBK";
            case "FS" -> "FSV";
            case "KFZ" -> "KZV";
            case "KRFF" -> "KRF";
            default -> documentType;
        };
    }

    public Date getDocumentDate() {
        return parse(documentDate);
    }

    public Date getReceipmentDate() {
        return parse(receipmentDate);
    }

    public Date getPaymentDate() {
        return Optional.ofNullable(paymentDeadline)
                .map(PaymentDeadline::getPaymentDate)
                .map(this::parse)
                .orElse(null);
    }

    public String getFiscal() {
        return Optional.ofNullable(fiscal)
                .filter(it -> it.equals("P"))
                .map(it -> "1")
                .orElse(null);
    }

    private Date parse(String date) {
        return java.util.Date.from(LocalDate.parse(date).atStartOfDay()
                .atZone(ZoneId.of("Z"))
                .toInstant());
    }

    @Override
    public String toString() {
        return "Header{" +
                "contractor=" + contractor +
                ", docNo='" + docNo  +
                '}';
    }
}
