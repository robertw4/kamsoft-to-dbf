package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Optional;

@JacksonXmlRootElement(localName = "podsumowanie-fk")
public class Summary {
    @JacksonXmlProperty(localName = "cechy")
    private final Features features;

    public Summary(Features features) {
        this.features = features;
    }

    public Summary() {
        this(null);
    }

    public String getInternalDocNo() {
        return Optional.ofNullable(features)
                .map(Features::getInternalDocNo)
                .orElse(null);
    }
}
