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

    protected pl.itr.kamsoft2dbf.doc.Amount getTransactionAmount() {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-transakcji"),
                getValue("kwd-netto-transakcji"),
                getValue("kwd-vat-transakcji")
        );
    }

    protected pl.itr.kamsoft2dbf.doc.Amount getRetailAmount() {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-det"),
                getValue("kwd-netto-det"),
                getValue("kwd-vat-det")
        );
    }

    protected pl.itr.kamsoft2dbf.doc.Amount getPurchaseAmount() {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-zakupu"),
                getValue("kwd-netto-zakupu"),
                getValue("kwd-vat-zakupu")
        );
    }

    private BigDecimal getValue(String id) {
        return amounts.stream()
                .filter(amount -> amount.getId().equals(id))
                .findFirst()
                .map(Amount::getValue)
                .orElse(null);
    }
}
