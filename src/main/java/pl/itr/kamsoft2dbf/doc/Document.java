package pl.itr.kamsoft2dbf.doc;

import java.util.Date;
import java.util.Optional;

public class Document {
    private final String docNo;
    private final String paymentDeadlineType;
    private final String documentType;
    private final Date documentDate;
    private final Date fiscalDate;
    private final Date paymentDate;
    private final String fiscal;
    private final String internalDocNo;
    private final String internalId;
    private final Amount transactionAmount;
    private final Amount retailAmount;
    private String contractorName;
    private String vatId;

    public Document(
            String docNo,
            String paymentDeadlineType,
            String documentType,
            Date documentDate,
            Date fiscalDate, Date paymentDate,
            String fiscal,
            String internalDocNo,
            String internalId,
            Amount transactionAmount,
            Amount retailAmount,
            Amount purchaceAmount,
            String contractorName,
            String vatId
    ) {
        this.docNo = docNo;
        this.paymentDeadlineType = paymentDeadlineType;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.fiscalDate = fiscalDate;
        this.paymentDate = paymentDate;
        this.fiscal = fiscal;
        this.internalDocNo = internalDocNo;
        this.internalId = internalId;
        this.transactionAmount = transactionAmount;
        this.retailAmount = isPurchaseDocument(documentType) ? retailAmount : purchaceAmount;
        this.contractorName = contractorName;
        this.vatId = vatId;
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

    public Optional<Amount> getTransactionAmount() {
        return Optional.ofNullable(transactionAmount);
    }

    public Optional<Amount> getRetailAmount() {
        return Optional.ofNullable(retailAmount);
    }

    public Document setContractorName(String contractorName) {
        this.contractorName = contractorName;
        return this;
    }

    public Document setVatId(String vatId) {
        this.vatId = vatId;
        return this;
    }

    private Boolean isPurchaseDocument(String type) {
        return switch (type) {
            case "FZV" -> true;
            case "KZV" -> true;
            default -> false;
        };
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
