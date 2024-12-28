package pl.itr.kamsoft2dbf.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@JacksonXmlRootElement(localName = "kartoteki")
public class Cards {
    @JacksonXmlProperty(localName = "kartoteka")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Card> cards;


    public Cards() {
        this.cards = null;
    }

    public Cards(List<Card> carts) {
        this.cards = carts;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "carts=" + cards +
                '}';
    }

    public Map<Integer, Card> getCartMap() {
        return cards.stream().collect(Collectors.toMap(Card::getId, identity()));
    }
}
