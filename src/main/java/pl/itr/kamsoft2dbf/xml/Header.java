package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "naglowek")
public class Header {
    @JacksonXmlProperty(localName = "kontrahent")
    private final Integer contractor;
    @JacksonXmlProperty(localName = "nr-dokumentu")
    private final String docNo;

    public Header() {
        this.docNo = null;
        this.contractor = null;
    }

    public Header(Integer contractor, String docNo) {
        this.contractor = contractor;
        this.docNo = docNo;
    }

    public Integer getContractor() {
        return contractor;
    }

    public String getDocNo() {
        return docNo;
    }

    @Override
    public String toString() {
        return "Header{" +
                "contractor=" + contractor +
                ", docNo='" + docNo  +
                '}';
    }
}
