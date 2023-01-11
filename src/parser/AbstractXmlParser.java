package parser;

import handlers.SharedStringsHandler;
import handlers.SheetHandler;
import parser.row.Row;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXmlParser implements XmlParser {

    private final SheetHandler sheetHandler;
    private final SharedStringsHandler sharedStringsHandler;
    private final List<File> unpackedFiles;

    protected AbstractXmlParser(final SheetHandler sheetHandler,
                                final SharedStringsHandler sharedStringsHandler,
                                final List<File> unpackedFiles) {
        this.sheetHandler = sheetHandler;
        this.sharedStringsHandler = sharedStringsHandler;
        this.unpackedFiles = unpackedFiles;
    }

    @Override
    public List<Row> parseSheet() throws Exception {
        List<Row> rows = new ArrayList<>();

        for (File file : unpackedFiles)
            if (file.getName().contains("sheet"))
                rows.addAll(parseSheet(file));

        return rows;
    }

    @Override
    public List<Row> parseSheet(File sheetXml) throws Exception {
        if (!sheetXml.getName().contains("sheet"))
            throw new Exception("Метод парсит только sheet.xml");

        var parser = SAXParserFactory.saxFactory();
        parser.parse(sheetXml, sheetHandler);
        return sheetHandler.parsedRows();
    }

    @Override
    public List<File> parseSharedStrings() throws Exception {
        List<File> sharedStrings = new ArrayList<>();

        for (File file : unpackedFiles)
            if (file.getName().contains("sharedStrings.xml"))
                sharedStrings.addAll(parseSharedStrings(file));

        return sharedStrings;
    }

    @Override
    public List<File> parseSharedStrings(File sharedStringsXml) throws Exception {
        if (!sharedStringsXml.getName().contains("sharedStrings.xml"))
            throw new Exception("Метод парсит только sharedStrings.xml");

        var parser = SAXParserFactory.saxFactory();
        parser.parse(sharedStringsXml, sharedStringsHandler);
        return sharedStringsHandler.parsedStrings();
    }

    private static class SAXParserFactory{
        public static SAXParser saxFactory() {
            try {
                return javax.xml.parsers.SAXParserFactory.newInstance().newSAXParser();
            } catch (Exception e) {
                throw new RuntimeException("Невозможно создать SAXParser");
            }
        }
    }
}
