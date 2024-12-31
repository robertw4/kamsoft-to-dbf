package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

import java.util.Arrays;

import static com.linuxense.javadbf.DBFDataType.CHARACTER;
import static com.linuxense.javadbf.DBFDataType.DATE;

public enum Fields {
    TYPK(CHARACTER, 1),
    TYPR(CHARACTER, 1),
    TYPD(CHARACTER, 6),
    DTA1(DATE),
    DTA2(DATE),
    DTA3(DATE),
    DTA4(DATE),
    NKNT(CHARACTER, 6),
    NRDOK(CHARACTER, 20),
    NDKR(CHARACTER, 20),
    NDAP(CHARACTER, 20),
    NZWK(CHARACTER, 100),
    NIPK(CHARACTER, 15);

    private final DBFDataType type;
    private final Integer length;

    Fields(DBFDataType type, Integer length) {
        this.type = type;
        this.length = length;
    }

    Fields(DBFDataType type) {
        this(type, 8);
    }

    public static DBFField[] getFields() {
        return Arrays.stream(Fields.values())
                .map(Fields::toDBFField)
                .toArray(DBFField[]::new);
    }

    private DBFField toDBFField() {
        return new DBFField(name(), type, length);
    }
}
