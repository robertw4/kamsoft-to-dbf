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
    private final String recipientDate;
    @JacksonXmlProperty(localName = "data-sprzedazy")
    private final String saleDate;
    @JacksonXmlProperty(localName = "status-fiskalny")
    private final String fiscal;
    @JacksonXmlProperty(localName = "wewn-ident")
    private final String internalId;

    public Header(
            Integer contractor,
            String docNo,
            PaymentDeadline paymentDeadline,
            String documentType,
            String documentDate,
            String recipientDate,
            String saleDate,
            String fiscal, String internalId
    ) {
        this.contractor = contractor;
        this.docNo = docNo;
        this.paymentDeadline = paymentDeadline;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.recipientDate = recipientDate;
        this.saleDate = saleDate;
        this.fiscal = fiscal;
        this.internalId = internalId;
    }

    public Header() {
        this(null, null, null, null, null, null, null, null, null);
    }

    protected Integer getContractor() {
        return contractor;
    }

    protected String getDocNo() {
        return switch (documentType) {
            case "SBKB" -> "SBK " + dateToDocNo();
            case "KRFF" -> "KRF " + dateToDocNo();
            default -> docNo;
        };
    }

    protected PaymentDeadline getPaymentDeadline() {
        return paymentDeadline;
    }

    protected String toDocumentType() {
        return switch (documentType) {
            case "FZ" -> "FZV";
            case "SBKB" -> "SBK";
            case "FS" -> "FSV";
            case "KFZ" -> "KZV";
            case "KRFF" -> "KRF";
            default -> null;
        };
    }

    protected Date getDocumentDate() {
        return parse(documentDate).orElse(null);
    }

    protected Date getFiscalDate() {
        return parse(recipientDate)
                .orElse(parse(saleDate)
                        .orElse(null)
                );
    }

    protected Date getPaymentDate() {
        return Optional.ofNullable(paymentDeadline)
                .map(PaymentDeadline::getPaymentDate)
                .flatMap(this::parse)
                .orElse(getDocumentDate());
    }

    protected String getFiscal() {
        return Optional.ofNullable(fiscal)
                .filter(it -> it.equals("P"))
                .map(it -> "1")
                .orElse(null);
    }

    protected String getInternalId() {
        return internalId;
    }

    private Optional<Date> parse(String date) {
        return Optional.ofNullable(date)
                .map(LocalDate::parse)
                .map(it -> it.atStartOfDay().atZone(ZoneId.of("Z")).toInstant())
                .map(Date::from);
    }

    private String dateToDocNo() {
        return String.join(".",
                docNo.substring(6, 8),
                docNo.substring(4, 6),
                docNo.substring(0, 4)
        );
    }

    @Override
    public String toString() {
        return "Header{" +
                "contractor=" + contractor +
                ", docNo='" + docNo  +
                '}';
    }
}
