package unpacking;

import java.io.File;
import java.io.IOException;

public class DefaultXlsxUnpacking extends AbstractXlsxUnpacking {
    public DefaultXlsxUnpacking(File file) throws IOException {
        super(file);
    }

    public DefaultXlsxUnpacking(File file, String... values) throws IOException {
        super(file, values);
    }
}
