package main;

import handlers.SharedStringsHandler;
import handlers.SheetHandler;
import parser.DefaultXmlParser;
import parser.XmlParser;
import parser.row.Row;
import unpacking.DefaultXlsxUnpacking;
import unpacking.XlsxUnpacking;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
//        int i = 10;

//        while (i > 0) {
        long start = System.currentTimeMillis();

        XlsxUnpacking unpacking = new DefaultXlsxUnpacking(new File("/Users/19711995/Desktop/Сбер/6. Релизы/12. 50 Sprint/Справочник_21102022.xlsx"));
        unpacking.unpack();
        System.out.println("Временная директория с файлами: " + unpacking.unpackedDirectory().getAbsolutePath());
        System.out.println(unpacking.unpackedFiles());

        XmlParser parser = new DefaultXmlParser(new SheetHandler(), new SharedStringsHandler(unpacking.unpackedDirectory()), unpacking.unpackedFiles());
        long s1 = System.currentTimeMillis();
        List<File> sharedStrings = parser.parseSharedStrings();
        System.out.printf("s1: %s ms\n", System.currentTimeMillis() - s1);

        long s2 = System.currentTimeMillis();
        List<Row> rows = parser.parseSheet();
        System.out.printf("s2: %s ms\n", System.currentTimeMillis() - s2);

        System.out.printf("finish: %s ms | rows size: %s | stringsFiles size: %s\n", System.currentTimeMillis() - start, rows.size(), sharedStrings.size());
        unpacking.clean();

//            i--;
//        }
    }

}
