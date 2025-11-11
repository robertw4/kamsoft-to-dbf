package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Vat;

import java.math.BigDecimal;
import java.util.EnumMap;
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

    protected Map<Vat, pl.itr.kamsoft2dbf.doc.Amount> getRetailVatAmounts() {
        Map<Vat, pl.itr.kamsoft2dbf.doc.Amount> result = new EnumMap<>(Vat.class);
        for (Vat vat : Vat.getVatRates()) {
            pl.itr.kamsoft2dbf.doc.Amount amount = getRetailVatAmount(vat);
            if (amount != null) {
                result.put(vat, amount);
            }
        }
        return result;
    }

    protected BigDecimal getPaymentAmount() {
        return getValue("kwd-zaplata");
    }

    protected BigDecimal getCzNet() {
        return getValue("kwd-netto-cz");
    }

    protected BigDecimal getCzRxNet() {
        return getValue("kwd-netto-cz-rx");
    }

    protected BigDecimal getCzRxwNet() {
        return getValue("kwd-netto-cz-rxw");
    }

    protected BigDecimal getCzOtcNet() {
        return getValue("kwd-netto-cz-otc");
    }

    protected BigDecimal getCzOtcwNet() {
        return getValue("kwd-netto-cz-otcw");
    }

    protected BigDecimal getRxNet() {
        return getValue("kwd-netto-rx");
    }

    protected BigDecimal getRxwNet() {
        return getValue("kwd-netto-rxw");
    }

    protected BigDecimal getOtcNet() {
        return getValue("kwd-netto-otc");
    }

    protected BigDecimal getOtcwNet() {
        return getValue("kwd-netto-otcw");
    }

    private pl.itr.kamsoft2dbf.doc.Amount getVatAmount(Vat vat) {
        return new pl.itr.kamsoft2dbf.doc.Amount(
                getValue("kwd-brutto-transakcji-" + vat.getVat()),
                getValue("kwd-netto-transakcji-" + vat.getVat()),
                getValue("kwd-vat-transakcji-" + vat.getVat())
        );
    }

    private pl.itr.kamsoft2dbf.doc.Amount getRetailVatAmount(Vat vat) {
        var brutto = getValue("kwd-brutto-det-" + vat.getVat());
        var netto = getValue("kwd-netto-det-" + vat.getVat());
        var vatValue = getValue("kwd-vat-det-" + vat.getVat());

        if (brutto == null && netto == null && vatValue == null) {
            return null;
        }

        return new pl.itr.kamsoft2dbf.doc.Amount(brutto, netto, vatValue);
    }

    private BigDecimal getValue(String id) {
        return amounts.stream()
                .filter(amount -> amount.getId().equals(id))
                .findFirst()
                .map(Amount::getValue)
                .orElse(null);
    }
}
