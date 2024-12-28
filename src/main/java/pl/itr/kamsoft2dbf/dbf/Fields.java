package pl.itr.kamsoft2dbf.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

import java.util.Arrays;

import static com.linuxense.javadbf.DBFDataType.CHARACTER;

public enum Fields {
    NRDOK(CHARACTER, 20),
    NZWK(CHARACTER, 100),
    NIPK(CHARACTER, 15);

    private DBFDataType type;
    private Integer length;

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
