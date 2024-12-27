package pl.itr.doc;

public class Document {
    private final String kontrahentName;
    private final String vatId;
    private final String docNo;

    public Document(String kontrahentName, String vatId, String docNo) {
        this.kontrahentName = kontrahentName;
        this.vatId = vatId;
        this.docNo = docNo;
    }

    public Document(String docNo) {
        this.kontrahentName = null;
        this.vatId = null;
        this.docNo = docNo;
    }

    @Override
    public String toString() {
        return "Document{" +
                "kontrahentName='" + kontrahentName + '\'' +
                ", vatId='" + vatId + '\'' +
                ", docNo='" + docNo + '\'' +
                '}';
    }
}
