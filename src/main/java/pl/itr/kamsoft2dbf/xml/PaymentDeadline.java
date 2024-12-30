package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "termin-platnosci")
public class PaymentDeadline {
    @JacksonXmlProperty(localName = "rodzaj")
    private final String type;

    public PaymentDeadline(String type) {
        this.type = type;
    }

    public PaymentDeadline() {
        this(null);
    }

    public String getType() {
        return type;
    }
}
