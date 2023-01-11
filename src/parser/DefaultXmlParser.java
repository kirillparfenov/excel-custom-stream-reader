package parser;

import handlers.SharedStringsHandler;
import handlers.SheetHandler;

import java.io.File;
import java.util.List;

public class DefaultXmlParser extends AbstractXmlParser {
    public DefaultXmlParser(SheetHandler handler, SharedStringsHandler stringsHandler, List<File> unpackedFiles) {
        super(handler, stringsHandler, unpackedFiles);
    }
}
