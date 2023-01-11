package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parser.cell.Cell;
import parser.row.Row;

import java.util.ArrayList;
import java.util.List;

public class SheetHandler extends DefaultHandler {

    private boolean isCell = false;
    private boolean isSharedStr = false;

    private boolean isValue = false;
    private boolean isFormula = false;
    private boolean isRow = false;
    private boolean buildRow = false;

    private Row currentRow;
    private Integer rowIndex;
    private final List<Row> parsedRows = new ArrayList<>();

    private String cellAddress;
    private String cellValue;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        updateElementStatus(qName, true);
        handleAttributes(attributes);
        buildRow();
        if (hasSharedStrings(attributes))
            isSharedStr = true;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        updateElementStatus(qName, false);
        end();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isCell && isValue) {
            cellValue = new String(ch, start, length).trim();
            buildCell();
        }
    }

    private void updateElementStatus(String element, boolean status) {
        switch (element.toLowerCase()) {
            case "row":
                isRow = status;
                buildRow = status;
                break;
            case "c":
                isCell = status;
                break;
            case "v":
                isValue = status;
                break;
            case "f":
                isFormula = status;
                break;
        }
    }

    private void handleAttributes(Attributes attributes) {
        if (buildRow) {
            rowIndex = Integer.valueOf(index(attributes));
        }

        if (isCell && !isValue) {
            cellAddress = index(attributes);
        }
    }

    private String index(Attributes attributes) {
        return attributes.getValue("r");
    }

    private boolean hasSharedStrings(Attributes attributes) {
        return attributes.getValue("t") != null && attributes.getValue("t").equals("s");
    }

    private void buildRow() {
        if (buildRow) {
            buildRow = false;
            currentRow = new Row();
            currentRow.setIndex(rowIndex);
        }
    }

    private void buildCell() {
        if (isCell && isValue) {
            Cell currentCell = new Cell();
            currentCell.setAddress(cellAddress);
            //TODO if(hasSharedString) -> дернуть из sharedStrings.xml -> cell.setValue(cellValue);
            if (isSharedStr) {
                isSharedStr = false;
                currentCell.setSharedAddress(Integer.parseInt(cellValue));
            }
            else
                currentCell.setValue(cellValue);

            currentRow.getCells().add(currentCell);
        }
    }

    private void end() {
        if (!isRow && currentRow != null) {
            parsedRows.add(currentRow);
            currentRow = null;
        }
    }

    public List<Row> parsedRows() {
        return parsedRows;
    }
}
