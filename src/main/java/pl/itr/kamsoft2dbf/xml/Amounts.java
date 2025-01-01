package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;
import java.util.List;

@JacksonXmlRootElement(localName = "kwoty")
public class Amounts {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "kwota")
    private final List<Amount> amounts;

    public Amounts(List<Amount> amounts) {
        this.amounts = amounts;
    }

    public Amounts() {
        this(null);
    }

    public pl.itr.kamsoft2dbf.doc.Amount getPurchaceAmount() {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-zakupu"),
                getValue("kwd-netto-zakupu"),
                getValue("kwd-vat-zakupu")
        );
    }

    private BigDecimal getValue(String id) {
        return amounts.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .map(Amount::getValue)
                .orElse(null);
    }
}
