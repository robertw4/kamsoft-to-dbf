package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Amount;
import pl.itr.kamsoft2dbf.doc.Vat;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@JacksonXmlRootElement(localName = "podsumowanie-fk")
public class Summary {
    @JacksonXmlProperty(localName = "cechy")
    private final Features features;
    @JacksonXmlProperty(localName = "kwoty")
    private final Amounts amounts;

    public Summary(Features features, Amounts amounts) {
        this.features = features;
        this.amounts = amounts;
    }

    public Summary() {
        this(null, null);
    }

    protected Optional<String> getInternalDocNo() {
        return Optional.ofNullable(features)
                .flatMap(Features::getInternalDocNo);
    }

    protected Amount getAmount(Function<Amounts, Amount> getter) {
        return Optional.ofNullable(amounts)
                .map(getter)
                .orElse(null);
    }

    protected Map<Vat, Amount> getVatAmounts() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getVatAmounts)
                .orElse(Map.of());
    }

    protected Map<Vat, Amount> getRetailVatAmounts() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getRetailVatAmounts)
                .orElse(Map.of());
    }

    protected java.math.BigDecimal getPaymentAmount() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getPaymentAmount)
                .orElse(null);
    }

    protected java.math.BigDecimal getCzNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getCzNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getCzRxNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getCzRxNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getCzRxwNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getCzRxwNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getCzOtcNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getCzOtcNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getCzOtcwNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getCzOtcwNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getRxNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getRxNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getRxwNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getRxwNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getOtcNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getOtcNet)
                .orElse(null);
    }

    protected java.math.BigDecimal getOtcwNet() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getOtcwNet)
                .orElse(null);
    }
}
