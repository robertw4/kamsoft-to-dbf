package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

import java.util.Arrays;

import static com.linuxense.javadbf.DBFDataType.*;

public enum Fields {
    TYPK(CHARACTER, 1),
    TYPR(CHARACTER, 1),
    TYPD(CHARACTER, 6),
    DTA1(DATE),
    DTA2(DATE),
    DTA3(DATE),
    DTA4(DATE),
    NKNT(CHARACTER, 6),
    NDOK(CHARACTER, 20),
    NDKR(CHARACTER, 20),
    NDAP(CHARACTER, 20),
    OPIS(CHARACTER, 50),
    OSID(CHARACTER, 23),
    P01(NUMERIC, 13, 2),
    P02(NUMERIC, 13, 2),
    P03(NUMERIC, 13, 2),
    P04(NUMERIC, 13, 2),
    P05(NUMERIC, 13, 2),
    P06(NUMERIC, 13, 2),
    P07(NUMERIC, 13, 2),
    P08(NUMERIC, 13, 2),
    P09(NUMERIC, 13, 2),
    P10(NUMERIC, 13, 2),
    P11(NUMERIC, 13, 2),
    P12(NUMERIC, 13, 2),
    P13(NUMERIC, 13, 2),
    P14(NUMERIC, 13, 2),
    P15(NUMERIC, 13, 2),
    P16(NUMERIC, 13, 2),
    P17(NUMERIC, 13, 2),
    P18(NUMERIC, 13, 2),
    P19(NUMERIC, 13, 2),
    P20(NUMERIC, 13, 2),
    P21(NUMERIC, 13, 2),
    P22(NUMERIC, 13, 2),
    P23(NUMERIC, 13, 2),
    P24(NUMERIC, 13, 2),
    P25(NUMERIC, 13, 2),
    P26(NUMERIC, 13, 2),
    P27(NUMERIC, 13, 2),
    P28(NUMERIC, 13, 2),
    P29(NUMERIC, 13, 2),
    P30(NUMERIC, 13, 2),
    P31(NUMERIC, 13, 2),
    P32(NUMERIC, 13, 2),
    P33(NUMERIC, 13, 2),
    P34(NUMERIC, 13, 2),
    NZWK(CHARACTER, 100),
    NIPK(CHARACTER, 15);

    private final DBFDataType type;
    private final Integer length;
    private final Integer precision;

    Fields(DBFDataType type, Integer length, Integer precision) {
        this.type = type;
        this.length = length;
        this.precision = precision;
    }

    Fields(DBFDataType type, Integer length) {
        this(type, length, 0);
    }

    Fields(DBFDataType type) {
        this(type, 8, 0);
    }

    static DBFField[] getFields() {
        return Arrays.stream(Fields.values())
                .map(Fields::toDBFField)
                .toArray(DBFField[]::new);
    }

    private DBFField toDBFField() {
        return new DBFField(name(), type, length, precision);
    }
}
