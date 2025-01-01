package pl.itr.kamsoft2dbf.doc;

import java.util.Date;
import java.util.Optional;

public class Document {
    private final String contractorName;
    private final String vatId;
    private final String docNo;
    private final String paymentDeadlineType;
    private final String documentType;
    private final Date documentDate;
    private final Date fiscalDate;
    private final Date paymentDate;
    private final String fiscal;
    private final String internalDocNo;
    private final String internalId;
    private final Amount purchaceAmount;

    public Document(String contractorName,
                    String vatId,
                    String docNo,
                    String paymentDeadlineType,
                    String documentType,
                    Date documentDate,
                    Date fiscalDate, Date paymentDate,
                    String fiscal,
                    String internalDocNo,
                    String internalId,
                    Amount purchaceAmount
    ) {
        this.contractorName = contractorName;
        this.vatId = vatId;
        this.docNo = docNo;
        this.paymentDeadlineType = paymentDeadlineType;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.fiscalDate = fiscalDate;
        this.paymentDate = paymentDate;
        this.fiscal = fiscal;
        this.internalDocNo = internalDocNo;
        this.internalId = internalId;
        this.purchaceAmount = purchaceAmount;
    }

    public Document(
            String docNo,
            String paymentDeadlineType,
            String documentType,
            Date documentDate,
            Date fiscalDate,
            Date paymentDate,
            String fiscal,
            String internalDocNo,
            String internalId,
            Amount purchaceAmount
    ) {
        this(null,
                null,
                docNo,
                paymentDeadlineType,
                documentType,
                documentDate,
                fiscalDate,
                paymentDate,
                fiscal, internalDocNo,
                internalId, purchaceAmount
        );
    }

    public String getContractorName() {
        return contractorName;
    }

    public String getVatId() {
        return vatId;
    }

    public String getDocNo() {
        return docNo;
    }

    public String getPaymentDeadlineType() {
        return paymentDeadlineType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public Date getFiscalDate() {
        return fiscalDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getFiscal() {
        return fiscal;
    }

    public String getInternalDocNo() {
        return internalDocNo;
    }

    public String getInternalId() {
        return internalId;
    }

    public Optional<Amount> getPurchaceAmount() {
        return Optional.ofNullable(purchaceAmount);
    }

    @Override
    public String toString() {
        return "Document{" +
                "contractorName='" + contractorName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }
}
