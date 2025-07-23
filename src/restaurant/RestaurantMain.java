package restaurant;

import java.io.File;
import java.util.Scanner;

import process.ProcessMain;
import utils.LoggerUtil;

public class RestaurantMain {

    // CONSTANTS
    private static final int MAX_TABLES = 30;
    private static final int MAX_PERSONS_BY_TABLE = 6;
    private static final int MAX_ITEMS_BY_PERSON = 3;

    // MAIN ARRAYS
    private static int[] personsInTable = new int[MAX_TABLES];
    private static int[][] personItems = new int[MAX_TABLES][MAX_PERSONS_BY_TABLE];
    private static int[][][] itemQuants = new int[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];
    private static boolean[] tables = new boolean[MAX_TABLES];
    private static String[][] personNames = new String[MAX_TABLES][MAX_PERSONS_BY_TABLE];
    private static String[][][] itemNames = new String[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];
    private static double[][][] itemPrices = new double[MAX_TABLES][MAX_PERSONS_BY_TABLE][MAX_ITEMS_BY_PERSON];

    public static void init () {
        ProcessMain.initAllArrays(tables, personsInTable, personNames, personItems, itemNames, itemQuants, itemPrices);
        showMenu();
        ProcessMain.clearAllArrays(tables, personsInTable, personNames, personItems, itemNames, itemQuants, itemPrices);
    }

    private static void showMenu () {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE RESTAURANTE ===");
            System.out.println("1. Agregar comanda");
            System.out.println("2. Ver estado de todas las mesas");
            System.out.println("3. Buscar información por nombre");
            System.out.println("4. Buscar información por mesa");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opt = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            System.out.println(); // Salto de linea

            switch (opt) {
                case 1:
                    addOrder(scanner);
                    break;
                case 2:
                    showTables();
                    break;
                case 3:
                    searchInfo(scanner, "NAME");
                    break;
                case 4:
                    searchInfo(scanner, "TABLE");
                    break;
                case 5:
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    private static void addOrder (Scanner scanner) {
        int table = Restaurant.setTableNumber(scanner, tables, MAX_TABLES);
        int persons = Restaurant.setPersonsInTable(scanner, table, personsInTable, MAX_PERSONS_BY_TABLE);
        Restaurant.namesInTable(scanner, table, persons, personNames);

        for (int i = 0; i < persons; i++) {
            String name = personNames[table - 1][i];
            int items = Restaurant.setItemsPerson(scanner, personNames[table - 1], personItems[table - 1], i, MAX_ITEMS_BY_PERSON);

            for (int j = 0; j < items; j++) {
                System.out.println("\nItem " + (j + 1) + " - " + name.toUpperCase());
                Restaurant.setItemName(scanner, itemNames[table - 1][i], j);
                Restaurant.setItemQuant(scanner, itemQuants[table - 1][i], j);
                Restaurant.setItemPrice(scanner, itemPrices[table - 1][i], j);
            }
        }

        Restaurant.generateInvoice(table, persons, personNames, personItems, itemNames, itemQuants, itemPrices);
    }

    private static void showTables () {
        for (int i = 0; i < tables.length; i++) {
            Restaurant.showStatusTable(i, tables[i]);
        }
    }

    private static void searchInfo (Scanner scanner, String criteria) {
        String error_msg = "";
        File[] invoices = Restaurant.getTotalInvoices();

        if (invoices != null && invoices.length > 0) {
            switch (criteria) {
                case "NAME":
                    Restaurant.searchInfoByName(scanner, invoices);
                    break;
                case "TABLE":
                    Restaurant.searchInfoByTable(scanner, invoices, MAX_TABLES);
                    break;
                default:
                    error_msg = "Termino de busqueda invalido";
                    break;
            }
        } else {
            error_msg = "No hay facturas generadas";
        }

        if (!error_msg.equals("")) {
            LoggerUtil.logWarn(error_msg);
            System.out.println(error_msg.toUpperCase());
        }
    }
}