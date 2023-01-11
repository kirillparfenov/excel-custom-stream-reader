package parser;

import parser.row.Row;

import java.io.File;
import java.util.List;

public interface XmlParser {
    List<Row> parseSheet() throws Exception;

    List<Row> parseSheet(File sheetXml) throws Exception;

    List<File> parseSharedStrings() throws Exception;

    List<File> parseSharedStrings(File sharedStringsXml) throws Exception;
}
