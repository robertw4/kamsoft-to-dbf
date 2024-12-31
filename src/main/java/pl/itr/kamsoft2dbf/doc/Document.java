package pl.itr.kamsoft2dbf.doc;

import java.util.Date;

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

    public Document(String contractorName,
                    String vatId,
                    String docNo,
                    String paymentDeadlineType,
                    String documentType,
                    Date documentDate,
                    Date fiscalDate, Date paymentDate, String fiscal, String internalDocNo
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
    }

    public Document(
            String docNo,
            String paymentDeadlineType,
            String documentType,
            Date documentDate,
            Date fiscalDate,
            Date paymentDate,
            String fiscal, String internalDocNo
    ) {
        this(null,
                null,
                docNo,
                paymentDeadlineType,
                documentType,
                documentDate,
                fiscalDate,
                paymentDate,
                fiscal, internalDocNo
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

    @Override
    public String toString() {
        return "Document{" +
                "contractorName='" + contractorName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }

    public String getInternalDocNo() {
        return internalDocNo;
    }
}
