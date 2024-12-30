package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

import java.util.Arrays;

import static com.linuxense.javadbf.DBFDataType.CHARACTER;

public enum Fields {
    TYPK(CHARACTER, 1),
    TYPR(CHARACTER, 1),
    TYPD(CHARACTER, 6),
    NRDOK(CHARACTER, 20),
    NZWK(CHARACTER, 100),
    NIPK(CHARACTER, 15);

    private final DBFDataType type;
    private final Integer length;

    Fields(DBFDataType type, Integer length) {
        this.type = type;
        this.length = length;
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
