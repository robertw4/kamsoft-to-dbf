package pl.itr.kamsoft2dbf.doc;

public class Document {
    private final String contractorName;
    private final String vatId;
    private final String docNo;
    private final String paymentDeadlineType;
    private final String documentType;

    public Document(String contractorName,
                    String vatId,
                    String docNo,
                    String paymentDeadlineType,
                    String documentType
    ) {
        this.contractorName = contractorName;
        this.vatId = vatId;
        this.docNo = docNo;
        this.paymentDeadlineType = paymentDeadlineType;
        this.documentType = documentType;
    }

    public Document(String docNo, String paymentDeadlineType, String documentType) {
        this(null, null, docNo, paymentDeadlineType, documentType);
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

    @Override
    public String toString() {
        return "Document{" +
                "contractorName='" + contractorName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }

    public String getPaymentDeadlineType() {
        return paymentDeadlineType;
    }

    public String getDocumentType() {
        return documentType;
    }
}
