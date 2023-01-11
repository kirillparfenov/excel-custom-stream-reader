package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SharedStringsHandler extends DefaultHandler {

    private static final Integer STRINGS_IN_FILE = 10_000;
    private final File unpackingDirectory;

    public SharedStringsHandler(File unpackingDirectory) {
        this.unpackingDirectory = unpackingDirectory;
    }

    private List<File> parsedStrings = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();
    private int counter = 0;
    private int fCounter = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (counter == STRINGS_IN_FILE) {
            stringsToFile();
            counter = 0;
            stringBuilder.setLength(0);
        }
        stringBuilder.append(new String(ch, start, length)).append("\n");
        counter++;
    }

    public List<File> parsedStrings() {
        return parsedStrings;
    }

    private void stringsToFile() {
        File stringsFile = new File(unpackingDirectory + File.separator + "strings" + File.separator + fCounter + ".txt");
        parsedStrings.add(stringsFile);
        fCounter++;

        stringsFile.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(stringsFile)) {
            long start = System.currentTimeMillis();
            fos.write(stringBuilder.toString().getBytes());
            fos.flush();
            System.out.printf("write file: %s ms\n", System.currentTimeMillis() - start);
        } catch (Exception e) {
            // exception
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(stringsFile))) {
            String line;
            long start = System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
                line = "1";
            }
            System.out.printf("reader: %s\n", System.currentTimeMillis() - start);
        } catch (Exception e) {
            // exception
        }
    }
}
