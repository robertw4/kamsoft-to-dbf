package pl.itr.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@JacksonXmlRootElement(localName = "kartoteki")
public class Carts {
    @JacksonXmlProperty(localName = "kartoteka")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Cart> carts;


    public Carts() {
        this.carts = null;
    }

    public Carts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "carts=" + carts +
                '}';
    }

    public Map<Integer, Cart> getCartMap() {
        return carts.stream().collect(Collectors.toMap(Cart::getId, identity()));
    }
}
