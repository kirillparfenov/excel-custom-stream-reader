package unpacking;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public abstract class AbstractXlsxUnpacking implements XlsxUnpacking {

    private Short bufferSize = 8192;

    @Override
    public void changeBufferSizeTo(Short bufferSize) {
        this.bufferSize = bufferSize;
    }

    private final String[] values;
    private final File file;
    private final File tempDir = Files.createTempDirectory(UUID.randomUUID().toString()).toFile();
    private final List<File> xlsxFiles = new ArrayList<>();

    protected AbstractXlsxUnpacking(File file) throws IOException {
        this(file, "xl/sharedStrings.xml", "xl/worksheets/sheet");
    }

    protected AbstractXlsxUnpacking(File file, String... values) throws IOException {
        this.file = file;
        this.values = values;
    }

    @Override
    public void unpack() throws Exception {
        unpack(StandardCharsets.UTF_8);
    }

    @Override
    public void unpack(Charset charset) throws Exception {
        ZipEntry entry;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file), charset)) {
            while ((entry = zis.getNextEntry()) != null) {
                File file = createFile(entry);
                writeFile(zis, file);
                zis.closeEntry();
            }
        }
    }

    @Override
    public File unpackedDirectory() {
        return tempDir;
    }

    @Override
    public List<File> unpackedFiles() {
        return this.xlsxFiles;
    }

    private File createFile(ZipEntry entry) {
        String name;
        boolean hasDirectories;

        name = entry.getName();
        hasDirectories = name.contains("/");

        if (!containsValue(name)) return null;

        File target = new File(tempDir.getAbsolutePath() + File.separator + name);
        xlsxFiles.add(target);

        if (hasDirectories) createDirectories(target);

        return target;
    }

    private boolean createDirectories(File file) {
        return file.getParentFile().mkdirs();
    }

    private boolean containsValue(String entryName) {
        return Arrays.stream(values).anyMatch(entryName::contains);
    }

    private void writeFile(ZipInputStream zis, File temp) throws FileNotFoundException, IOException {
        if (Objects.isNull(temp)) return;

        int length;
        byte[] buffer = new byte[bufferSize];

        try (FileOutputStream fos = new FileOutputStream(temp)) {
            while ((length = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
        }
    }

    @Override
    public void clean() throws IOException {
        Path path = tempDir.toPath();
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
