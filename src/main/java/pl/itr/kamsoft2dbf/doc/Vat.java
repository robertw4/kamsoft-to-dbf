package pl.itr.kamsoft2dbf.doc;

import java.util.Arrays;
import java.util.List;

public enum Vat {
    VAT_23("23"), VAT_8("8");

    private final String vat;
    Vat(String vat) {
        this.vat = vat;
    }

    public String getVat() {
        return vat;
    }

    public static List<Vat> getVatRates() {
        return Arrays.stream(Vat.values()).toList();
    }
}
