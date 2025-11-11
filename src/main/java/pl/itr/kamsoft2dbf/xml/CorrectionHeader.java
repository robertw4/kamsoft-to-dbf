package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

class CorrectionHeader {
    @JacksonXmlProperty(localName = "wewn-ident-kor")
    private final String internalId;
    @JacksonXmlProperty(localName = "nr-dokumentu-kor")
    private final String documentNumber;
    @JacksonXmlProperty(localName = "typ-dokumentu-kor")
    private final String documentType;
    @JacksonXmlProperty(localName = "pelna-nazwa-kor")
    private final String fullName;
    @JacksonXmlProperty(localName = "data-wystawienia-kor")
    private final String issueDate;

    CorrectionHeader(
            String internalId,
            String documentNumber,
            String documentType,
            String fullName,
            String issueDate
    ) {
        this.internalId = internalId;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.fullName = fullName;
        this.issueDate = issueDate;
    }

    CorrectionHeader() {
        this(null, null, null, null, null);
    }

    protected Optional<Date> getIssueDate() {
        return parse(issueDate);
    }

    private Optional<Date> parse(String date) {
        return Optional.ofNullable(date)
                .map(LocalDate::parse)
                .map(it -> it.atStartOfDay().atZone(ZoneId.of("Z")).toInstant())
                .map(Date::from);
    }
}
