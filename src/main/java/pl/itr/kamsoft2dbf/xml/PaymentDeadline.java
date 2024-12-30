package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "termin-platnosci")
public class PaymentDeadline {
    @JacksonXmlProperty(localName = "rodzaj")
    private final String type;
    @JacksonXmlProperty(localName = "data")
    private final String paymentDate;

    public PaymentDeadline(String type, String paymentDate) {
        this.type = type;
        this.paymentDate = paymentDate;
    }

    public PaymentDeadline() {
        this(null, null);
    }

    public String getType() {
        return type;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
}
