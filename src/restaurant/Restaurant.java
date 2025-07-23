package restaurant;

import java.io.File;
import java.util.Scanner;

import process.ProcessMain;
import utils.FileManager;
import utils.LoggerUtil;
import utils.Validate;

public class Restaurant {

    // -------------------------------------------------------------------------------------------------------------- ADD ORDER
    public static int setTableNumber (Scanner scanner, boolean[] tables, int MAX) {
        if (Validate.isValidArr(tables)) {
            String msg = "Ingrese el numero de mesa (1-" + MAX + "): ";
            int tableNumber = Validate.scanValidInteger(scanner, msg, MAX);
            while (tables[tableNumber - 1]) {
                System.out.println("Mesa " + tableNumber + " ya ocupada.\n");
                tableNumber = Validate.scanValidInteger(scanner, msg, MAX);
            }
            tables[tableNumber - 1] = true;
            return tableNumber;
        }

        return -1;
    }

    public static int setPersonsInTable (Scanner scanner, int tableNumber, int[] personsInTable, int MAX) {
        if (Validate.isValidArr(personsInTable)) {
            int persons = Validate.scanValidInteger(scanner, "Ingrese el numero de comensales (1-" + MAX + "): ", MAX);
            personsInTable[tableNumber - 1] = persons;
            return persons;
        }

        return -1;
    }

    public static void namesInTable (Scanner scanner, int tableNumber, int personsNumber, String[][] personNames) {
        if (Validate.isValidArr(personNames[0])) {
            System.out.println();
            for (int i = 0; i < personsNumber; i++)
                personNames[tableNumber - 1][i] = Validate.scanValidString(scanner, "Nombre de persona " + (i + 1) + ": ");
        }
    }

    public static int setItemsPerson (Scanner scanner, String[] names, int[] items, int index, int MAX) {
        if (Validate.isValidArr(names) && Validate.isValidArr(items)) {
            System.out.println();
            int itemsPerson = Validate.scanValidInteger(scanner, "Numero de items de " + names[index] + " (1-" + MAX + "): ", MAX);
            items[index] = itemsPerson;
            return itemsPerson;
        }

        return -1;
    }

    public static void setItemName (Scanner scanner, String itemNames[], int index) {
        if (Validate.isValidArr(itemNames)) {
            String name = Validate.scanValidString(scanner, "- Nombre item " + (index + 1) + ": ");
            itemNames[index] = name;
        }
    }

    public static void setItemQuant (Scanner scanner, int itemQuants[], int index) {
        if (Validate.isValidArr(itemQuants)) {
            int quant = Validate.scanValidInteger(scanner,  "- Cantidad item " + (index + 1) + ": ", 0);
            itemQuants[index] = quant;
        }
    }

    public static void setItemPrice (Scanner scanner, double itemPrices[], int index) {
        if (Validate.isValidArr(itemPrices)) {
            double price = Validate.scanValidDouble(scanner,  "- Precio unitario item " + (index + 1) + ": ", 0);
            itemPrices[index] = price;
        }
    }

    public static void generateInvoice(int table, int persons, String[][] personNames, int[][] personItems,
    String[][][] itemNames, int[][][] itemQuants, double[][][] itemPrices) {
        if (Validate.isValidArr(personNames[0]) && Validate.isValidArr(personItems[0]) && Validate.isValidArr(itemNames[0][0]) &&
        Validate.isValidArr(itemQuants[0][0]) && Validate.isValidArr(itemPrices[0][0])) {
            String invoiceText = ProcessMain.buildInvoiceText(table, persons, personNames, personItems, itemNames, itemQuants, itemPrices);
            String fileNameInvoice = FileManager.getInvoiceFileName();
            FileManager.writeInFile(fileNameInvoice, invoiceText);
            System.out.println("\nComanda generada con exito: " + fileNameInvoice);
        }
    }

    // -------------------------------------------------------------------------------------------------------------- SHOW TABLES
    public static void showStatusTable(int index, boolean isBusy) {
        System.out.printf("- Mesa %02d: %s\n", index + 1, isBusy ? "OCUPADA" : "LIBRE");
    }

    // -------------------------------------------------------------------------------------------------------------- SEARCH
    public static File[] getTotalInvoices () {
        String directoryString = FileManager.getInvoiceDirectory();
        File[] invoices = FileManager.getFilesInDirectory(directoryString);
        return invoices;
    }

    public static void searchInfoByName (Scanner scanner, File[] invoices) {
        String nameToSearch = Validate.scanValidString(scanner, "Ingresa el nombre a buscar: ").toLowerCase();
        LoggerUtil.log("Buscando informacion: Nombre " + nameToSearch);

        FileManager.listFiles(invoices);
        int selectedFile = Validate.scanValidInteger(scanner, "Selecionar archivo a leer: ", invoices.length);

        String resultText = ProcessMain.searchNameInFile(invoices[selectedFile - 1], nameToSearch);
        if (resultText != null && !resultText.isEmpty()) {
            String fileNameResult = FileManager.getResultFileName("NAME", nameToSearch);
            FileManager.writeInFile(fileNameResult, resultText);
            System.out.println("\nBusqueda completada con exito, resultado en: " + fileNameResult);
        } else {
            LoggerUtil.logError("Busqueda por nombre (" + nameToSearch + "): SIN RESULTADOS");
            System.out.println("\nNo se encontraron resultados por nombre: " + nameToSearch);
        }
    }

    public static void searchInfoByTable (Scanner scanner, File[] invoices, int MAX) {
        int tableToSearch = Validate.scanValidInteger(scanner, "Ingresa la mesa a buscar: ", MAX);
        LoggerUtil.log("Buscando informacion: Mesa " + tableToSearch);

        FileManager.listFiles(invoices);
        int selectedFile = Validate.scanValidInteger(scanner, "Selecionar archivo a leer: ", invoices.length);

        String resultText = ProcessMain.searchTableInFile(invoices[selectedFile - 1], tableToSearch);
        if (resultText != null && !resultText.isEmpty()) {
            String fileNameResult = FileManager.getResultFileName("TABLE", String.valueOf(tableToSearch));
            FileManager.writeInFile(fileNameResult, resultText);
            System.out.println("\nBusqueda completada con exito, resultado en: " + fileNameResult);
        } else {
            LoggerUtil.logError("Busqueda por mesa (" + tableToSearch + "): SIN RESULTADOS");
            System.out.println("\nNo se encontraron resultados por mesa: " + tableToSearch);
        }
    }
}