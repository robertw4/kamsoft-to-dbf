package pl.itr.kamsoft2dbf.doc;

import java.math.BigDecimal;

public class Amount {
    private final BigDecimal brutto;
    private final BigDecimal netto;
    private final BigDecimal vat;

    public Amount(BigDecimal brutto, BigDecimal netto, BigDecimal vat) {
        this.brutto = brutto;
        this.netto = netto;
        this.vat = vat;

    }

    public BigDecimal getBrutto() {
        return brutto;
    }

    public BigDecimal getNetto() {
        return netto;
    }

    public BigDecimal getVat() {
        return vat;
    }
}
