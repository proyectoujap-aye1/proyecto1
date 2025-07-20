package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    private static final String INVOICE_DIRECTORY = "src/data/";
    private static final String INVOICE_FILE_NAME = "Factura";

    public static String getInvoiceFileName () {
        if (!Files.exists(Paths.get(INVOICE_DIRECTORY))) {
            try {
                Files.createDirectory(Paths.get(INVOICE_DIRECTORY));
            } catch (IOException e) {
                LoggerUtil.logError("Error al crear directorio de comandas: " + e.getMessage());
            }
        }

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        int rand = (int) (Math.random() * 1000) + 1;

        return INVOICE_DIRECTORY + INVOICE_FILE_NAME + "_" + current.format(formatter) + "_" + "Serial" + rand + ".txt";
    }

    public static void writeInFile (String path, String content) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(content);
        } catch (IOException e) {
            LoggerUtil.logError("Error al escribir en archivo " + path + ": " + e.getMessage());
        }
    }
}