package pl.itr.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "naglowek")
public class Header {
    @JacksonXmlProperty(localName = "kontrahent")
    private final Integer kontrahent;
    @JacksonXmlProperty(localName = "nr-dokumentu")
    private final String docNo;

    public Header() {
        this.docNo = null;
        this.kontrahent = null;
    }

    public Header(Integer kontrahent, String docNo) {
        this.kontrahent = kontrahent;
        this.docNo = docNo;
    }

    public Integer getKontrahent() {
        return kontrahent;
    }

    public String getDocNo() { return docNo; }

    @Override
    public String toString() {
        return "Header{" +
                "kontrahent=" + kontrahent +
                ", docNo='" + docNo  +
                '}';
    }
}
