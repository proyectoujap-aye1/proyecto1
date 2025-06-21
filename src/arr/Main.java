package arr;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    // CONSTANTS
    private static final int MAX_TABLES = 30;
    private static final int MAX_PERSONS_BY_TABLE = 6;
    private static final int MAX_ITEMS_BY_PERSON = 3;
    private static final String FILE_NAME = "Factura";

     // MAIN ARRAYS
    private static boolean[] tables = new boolean[MAX_TABLES];
    private static int[] personsInTable = new int[MAX_TABLES];
    private static String[][] namePersons = new String[MAX_TABLES][MAX_PERSONS_BY_TABLE];
    private static int[][] itemPersons = new int[MAX_TABLES][MAX_PERSONS_BY_TABLE];
    private static String[][][] nameItems = new String[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];
    private static int[][][] cantItems = new int[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];
    private static double[][][] priceItems = new double[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];

    public static void main(String[] args) {
        int tableNumber, personsNumber;
        String anotherInvoice = "";
        Scanner scanner = new Scanner(System.in);

        // Inicializando matrices
        Process.initMatrix(tables);
        Process.initMatrix(personsInTable);
        Process.initMatrix(namePersons);
        Process.initMatrix(itemPersons);
        Process.initMatrix(nameItems);
        Process.initMatrix(cantItems);
        Process.initMatrix(priceItems);

        System.out.println("=== SISTEMA DE RESTAURANT ===\n");

        do {
            // Solicitando numero de mesa
            tableNumber = Validate.scanValidInteger(scanner, "Ingrese el numero de mesa (1-" + MAX_TABLES + "): ", MAX_TABLES);
            while (tables[tableNumber - 1]) {
                System.out.println("Mesa " + tableNumber + " ya ocupada.");
                tableNumber = Validate.scanValidInteger(scanner, "Ingrese el numero de mesa (1-" + MAX_TABLES + "): ", MAX_TABLES);
            }
            tables[tableNumber - 1] = true;

            // Solicitando numero de personas en la mesa
            personsNumber = Validate.scanValidInteger(scanner, "Ingrese el numero de comensales (1-" + MAX_PERSONS_BY_TABLE + "): ", MAX_PERSONS_BY_TABLE);
            personsInTable[tableNumber - 1] = personsNumber;
            System.out.println();

            // Solicitando nombres de personas en la mesa
            Process.requestNames(scanner, tableNumber, personsNumber, namePersons);

            // Solicitando items de personas en la mesa
            Process.requestItems(scanner, tableNumber, personsNumber, namePersons, itemPersons, nameItems, cantItems, priceItems, MAX_ITEMS_BY_PERSON);

            // Generando factura
            try {
                String route = Paths.get("").toRealPath().toString() + "\\src\\arr";
                String fileName = Validate.checkDirectory(route) + "\\" + Process.generateFileName(FILE_NAME) + ".txt";
                Process.generateInvoice(fileName, tableNumber, personsNumber, namePersons, itemPersons, nameItems, cantItems, priceItems);
                System.out.println("\n=== INFORMACION PROCESADA ===");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\nHa ocurrido un error generando la factura: " + e.getMessage());
            }

            anotherInvoice = Validate.scanValidString(scanner, "\n¿Desea agregar otra factura? (y/n): ");

        } while (anotherInvoice.equals("y"));

        // Eliminacion de instancias
        System.out.println("\n=== FIN DEL PROGRAMA ===");
        tables = null;
        personsInTable = null;
        namePersons = null;
        itemPersons = null;
        nameItems = null;
        cantItems = null;
        priceItems = null;
    }
}