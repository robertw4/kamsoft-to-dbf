package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pl.itr.kamsoft2dbf.doc.Amount;

import java.util.Optional;

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

    protected Amount getTransactionAmount() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getTransactionAmount)
                .orElse(null);
    }

    protected Amount getRetailAmount() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getRetailAmount)
                .orElse(null);
    }

    protected Amount getPurchaceAmount() {
        return Optional.ofNullable(amounts)
                .map(Amounts::getPurchaceAmount)
                .orElse(null);
    }

}
