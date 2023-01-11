package unpacking;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public interface XlsxUnpacking {
    /**
     * Распаковка .xlsx файла во временную директорию.
     * <p>Кодировка UTF8</p>
     * */
    void unpack() throws Exception;

    /**
     * Распаковка .xlsx файла во временную директорию с указанием кодировки.
     * */
    void unpack(Charset charset) throws Exception;

    /**
     * Директория с распакованными файлами/директориями
     * */
    File unpackedDirectory();

    /**
    * Список распакованных файлов
    * */
    List<File> unpackedFiles();

    /**
     * Удалить директорию с распакованными файлами
     * */
    void clean() throws IOException;

    /**
     * Поменять размер буфера при записи файла
     * */
    void changeBufferSizeTo(Short bufferSize);
}
