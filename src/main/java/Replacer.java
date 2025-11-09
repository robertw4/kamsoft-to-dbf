import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.stream.IntStream;

import static eu.vitaliy.pl.charset.DOSCharsetProvider.MAZOVIA_CHARSET_NAME;

public class Replacer {
    public static void main(String[] args) {
        //var fileName = "fa241132.dbf";
        var fileName = "/Users/robert.wozniak/Documents/apt/2025/FA251031.dbf";
        var outFileName = "FA251031.dbf";
        try {
            DBFReader reader = new DBFReader(new FileInputStream(fileName), Charset.forName(MAZOVIA_CHARSET_NAME));
            DBFWriter writer = new DBFWriter(new FileOutputStream(outFileName), Charset.forName(MAZOVIA_CHARSET_NAME));
            var dbFields = IntStream.range(0, reader.getFieldCount()).mapToObj(reader::getField).toArray(DBFField[]::new);
            writer.setFields(dbFields);
            Double zaknetto = 0.0;
            Double zakvat = 0.0;
            Double zakbrutto = 0.0;
            for (var i = 0; i < reader.getRecordCount(); i++) {
                var row = reader.nextRecord();
                //if (i >= 0 && i!=2 && i!=4) {
                //if(i!=0 && i!=5) {
                    row[5] = row[4];
                //}
                if (row[58].equals("8790017162")) {
                    row[58] = "1070047823";
                    row[55] = "NEUCA GRUPA VAT";
                }
                if (row[2].equals("FZV") || row[2].equals("KZV")) {
                    zaknetto += Double.parseDouble(row[24].toString());
                    zakbrutto += Double.parseDouble(row[23].toString());
                    zakvat += Double.parseDouble(row[25].toString());

                }
                writer.addRecord(row);
            }
            writer.close();
            reader.close();
            System.out.println("Zaknetto: " + zaknetto + " Zakvat: " + zakvat + " Zakbrutto: " + zakbrutto);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
