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
                .orElse(null);
    }
}
