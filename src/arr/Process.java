package arr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Process {

    private static final String ERROR_FILE_NAME = "ErrorLogs.log";
    private static final String INVOICE_FILE_NAME = "Factura";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void initMatrix (int[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++)
                m[i] = 0;
        }
    }

    public static void initMatrix (boolean[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++)
                m[i] = false;
        }
    }

    public static void initMatrix (int[][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++)
                    m[i][j] = 0;
            }
        }
    }

    public static void initMatrix (String[][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++)
                    m[i][j] = "";
            }
        }
    }

    public static void initMatrix (int[][][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    for (int k = 0; k < m[0][0].length; k++)
                        m[i][j][k] = 0;
                }
            }
        }
    }

    public static void initMatrix (double[][][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    for (int k = 0; k < m[0][0].length; k++)
                        m[i][j][k] = 0;
                }
            }
        }
    }

    public static void initMatrix (String[][][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    for (int k = 0; k < m[0][0].length; k++)
                        m[i][j][k] = "";
                }
            }
        }
    }

    public static void requestNames (Scanner scanner, int tableNumber, int personsNumber, String[][] namePersons) {
        if (Validate.isValidArr(namePersons[0])) {
            for (int i = 0; i < personsNumber; i++)
                namePersons[tableNumber - 1][i] = Validate.scanValidString(scanner, "Nombre de persona " + (i + 1) + ": ");
        }
    }

    public static void requestItems (Scanner scanner, int tableNumber, int personsNumber, String[][] namePersons, int[][] itemPersons, String[][][] nameItems, int[][][] cantItems, double[][][] priceItems, int MAX_ITEMS_BY_PERSON) {

        if (Validate.isValidArr(namePersons[0]) && Validate.isValidArr(itemPersons[0]) && Validate.isValidArr(nameItems[0][0]) &&
        Validate.isValidArr(cantItems[0][0]) && Validate.isValidArr(priceItems[0][0])) {
            for (int i = 0; i < personsNumber; i++) {
                String name = namePersons[tableNumber - 1][i];
                int items = Validate.scanValidInteger(scanner, "\nCantidad de items de " + name + " (1-" + MAX_ITEMS_BY_PERSON + "): ", MAX_ITEMS_BY_PERSON);
                itemPersons[tableNumber - 1][i] = items;

                for (int j = 0; j < itemPersons[tableNumber - 1][i]; j++) {
                    System.out.println("\nItem " + (j + 1) + " - " + name.toUpperCase());
                    nameItems[tableNumber - 1][i][j] = Validate.scanValidString(scanner, "- Nombre item " + (j + 1) + ": ");
                    cantItems[tableNumber - 1][i][j] = Validate.scanValidInteger(scanner,  "- Cantidad item " + (j + 1) + ": ", 0);
                    priceItems[tableNumber - 1][i][j] = Validate.scanValidDouble(scanner,  "- Precio unitario item " + (j + 1) + ": ", 0);
                }
            }
        }
    }

    public static String generateFileName(String name) {
        LocalDateTime actualDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        int rand = (int) (Math.random() * 1000) + 1;

        return name + "_" + actualDateTime.format(formatter) + "_" + "Serial" + rand;
    }

    public static void generateInvoice(int tableNumber, int personsNumber, String[][] namePersons, int[][] itemPersons, String[][][] nameItems, int[][][] cantItems, double[][][] priceItems) throws IOException {

        if (Validate.isValidArr(namePersons[0]) && Validate.isValidArr(itemPersons[0]) && Validate.isValidArr(nameItems[0][0]) &&
        Validate.isValidArr(cantItems[0][0]) && Validate.isValidArr(priceItems[0][0])) {
            // Generando texto de la factura
            String invoiceText = buildInvoiceText(tableNumber, personsNumber, namePersons, itemPersons, nameItems, cantItems, priceItems);

            // Escribiendo en el archivo
            String route = Paths.get("").toRealPath().toString() + "\\src\\arr";
            String fileName = Validate.checkDirectory(route) + "\\" + Process.generateFileName(INVOICE_FILE_NAME) + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(invoiceText);
                System.out.println("Factura generada con éxito.");
            } catch (IOException e) {
                logError("Error al escribir el archivo", e.getMessage(), e.getLocalizedMessage());
                throw e;
            }
        }
    }

    public static void logError(String error, String message, String localizedMessage) {
        try {
            String route = Paths.get("").toRealPath().toString() + "\\src\\arr\\" + ERROR_FILE_NAME;
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String content = String.format("[%s] [%s] [%s] [%s]\n", timestamp, error, message, localizedMessage);

            BufferedWriter bw = new BufferedWriter(new FileWriter(route, true));
            bw.write(content);
            bw.close();
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error escribiendo en archivo .log");
        }
    }

    private static String buildInvoiceText(int tableNumber, int personsNumber, String[][] namePersons, int[][] itemPersons, String[][][] nameItems, int[][][] cantItems, double[][][] priceItems) {
        StringBuilder invoice = new StringBuilder();
        double totalGeneral = 0;

        invoice.append("Factura\n");
        invoice.append("Mesa: ").append(tableNumber).append("\n\n");

        for (int personIndex = 0; personIndex < personsNumber; personIndex++) {
            if (namePersons[tableNumber - 1][personIndex] != null) {
                String personName = namePersons[tableNumber - 1][personIndex];
                double totalPersona = 0;

                invoice.append("Cliente: ").append(personName).append("\n");
                invoice.append("-----------------------------------------------------------\n");

                for (int itemIndex = 0; itemIndex < itemPersons[tableNumber - 1][personIndex]; itemIndex++) {
                    if (nameItems[tableNumber - 1][personIndex][itemIndex] != null) {
                        String itemName = nameItems[tableNumber - 1][personIndex][itemIndex];
                        int cantidad = cantItems[tableNumber - 1][personIndex][itemIndex];
                        double precio = priceItems[tableNumber - 1][personIndex][itemIndex];
                        double subtotal = cantidad * precio;

                        invoice.append(itemName)
                            .append(" - Cantidad: ").append(cantidad)
                            .append(" - Precio: $").append(precio)
                            .append(" - Subtotal: $").append(subtotal).append("\n");
                        totalPersona += subtotal;
                    }
                }

                invoice.append("Total de ").append(personName).append(": $").append(totalPersona).append("\n\n");
                totalGeneral += totalPersona;
            }
        }

        invoice.append("-----------------------------------------------------------\n");
        invoice.append("Total General de la Mesa: $").append(totalGeneral);

        return invoice.toString();
    }
}