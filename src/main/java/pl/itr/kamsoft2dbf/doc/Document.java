package pl.itr.kamsoft2dbf.doc;

import java.util.Date;

public class Document {
    private final String contractorName;
    private final String vatId;
    private final String docNo;
    private final String paymentDeadlineType;
    private final String documentType;
    private final Date documentDate;
    private final Date receimpmentDate;
    private final Date paymentDate;
    private final String fiscal;

    public Document(String contractorName,
                    String vatId,
                    String docNo,
                    String paymentDeadlineType,
                    String documentType,
                    Date documentDate,
                    Date receimpmentDate, Date paymentDate, String fiscal
    ) {
        this.contractorName = contractorName;
        this.vatId = vatId;
        this.docNo = docNo;
        this.paymentDeadlineType = paymentDeadlineType;
        this.documentType = documentType;
        this.documentDate = documentDate;
        this.receimpmentDate = receimpmentDate;
        this.paymentDate = paymentDate;
        this.fiscal = fiscal;
    }

    public Document(
            String docNo,
            String paymentDeadlineType,
            String documentType,
            Date documentDate,
            Date receimpmentDate,
            Date paymentDate,
            String fiscal
    ) {
        this(null,
                null,
                docNo,
                paymentDeadlineType,
                documentType,
                documentDate,
                receimpmentDate,
                paymentDate,
                fiscal
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

    public Date getReceimpmentDate() {
        return receimpmentDate;
    }

    @Override
    public String toString() {
        return "Document{" +
                "contractorName='" + contractorName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getFiscal() {
        return fiscal;
    }
}
