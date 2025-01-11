package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Vat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

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

    protected Map<Vat, pl.itr.kamsoft2dbf.doc.Amount> getVatAmounts() {
        return Vat.getVatRates().stream()
                .collect(Collectors.toMap(identity(), this::getVatAmount));
    }

    private pl.itr.kamsoft2dbf.doc.Amount getVatAmount(Vat vat) {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-transakcji-" + vat.getVat()),
                getValue("kwd-netto-transakcji-" + vat.getVat()),
                getValue("kwd-vat-transakcji-" + vat.getVat())
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
