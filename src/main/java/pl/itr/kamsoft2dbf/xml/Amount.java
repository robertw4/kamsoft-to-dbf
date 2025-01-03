package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;

@JacksonXmlRootElement(localName = "kwota")
public class Amount {
    @JacksonXmlProperty(localName = "symbol")
    private final String id;
    @JacksonXmlProperty(localName = "wartosc")
    private final BigDecimal value;
    @JacksonXmlProperty(localName = "stawka-vat")
    private final String vat;

    public Amount(String id, BigDecimal value, String vat) {
        this.id = id;
        this.value = value;
        this.vat = vat;
    }

    public Amount() {
        this(null, null, null);
    }

    protected String getId() {
        return id;
    }

    protected BigDecimal getValue() {
        return value;
    }

    protected String getVat() {
        return vat;
    }
}
