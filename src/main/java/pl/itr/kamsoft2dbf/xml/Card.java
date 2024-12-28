package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "kartoteka")
public class Card {
    @JacksonXmlProperty(localName = "lp", isAttribute = true)
    private final Integer id;
    @JacksonXmlProperty(localName = "nazwa1")
    private final String name;
    @JacksonXmlProperty(localName = "nip")
    private final String vatId;

    public Card() {
        this.id = null;
        this.name = null;
        this.vatId = null;
    }
    public Card(Integer id, String name, String vatId) {
        this.id = id;
        this.name = name;
        this.vatId = vatId;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getVatId() { return vatId; }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id  +
                ", name='" + name + '\'' +
                ", vatId='" + vatId + '\'' +
                '}';
    }
}
