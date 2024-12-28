package pl.itr.kamsoft2dbf.doc;

public class Document {
    private final String contractorName;
    private final String vatId;
    private final String docNo;

    public Document(String contractorName, String vatId, String docNo) {
        this.contractorName = contractorName;
        this.vatId = vatId;
        this.docNo = docNo;
    }

    public Document(String docNo) {
        this.contractorName = null;
        this.vatId = null;
        this.docNo = docNo;
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
}
