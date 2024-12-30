package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;
import java.util.stream.Stream;

@JacksonXmlRootElement(localName = "kartoteka")
public class Card {
    @JacksonXmlProperty(localName = "lp", isAttribute = true)
    private final Integer id;
    @JacksonXmlProperty(localName = "nazwa1")
    private final String name;
    @JacksonXmlProperty(localName = "nazwa2")
    private final String name2;
    @JacksonXmlProperty(localName = "nip")
    private final String vatId;

    public Card(Integer id, String name, String name2, String vatId) {
        this.id = id;
        this.name = name;
        this.name2 = name2;
        this.vatId = vatId;
    }

    public Card() {
        this(null, null, null, null);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVatId() {
        return vatId;
    }

    public String getFullName() {
        return String.join(
                " ",
                Stream.of(name, name2).filter(Objects::nonNull).toList()
        );
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id  +
                ", name='" + name + '\'' +
                ", name2='" + name2 + '\'' +
                ", vatId='" + vatId + '\'' +
                '}';
    }
}
