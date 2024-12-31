package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "cecha")
public class Feature {
    @JacksonXmlProperty(localName = "symbol")
    private final String id;
    @JacksonXmlProperty(localName = "wartosc")
    private final String value;

    public Feature(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public Feature() {
        this(null, null);
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
