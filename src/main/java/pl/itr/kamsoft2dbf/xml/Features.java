package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Optional;

@JacksonXmlRootElement(localName = "cechy")
public class Features {
    @JacksonXmlProperty(localName = "cecha")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Feature> features;

    public Features(List<Feature> features) {
        this.features = features;
    }

    public Features() {
        this(null);
    }

    protected Optional<String> getInternalDocNo() {
        return features.stream()
                .filter(it -> it.getId().equals("fk-nr-pz"))
                .findFirst()
                .map(Feature::getValue);
    }
}
