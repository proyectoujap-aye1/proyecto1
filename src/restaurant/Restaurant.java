package restaurant;

import java.io.File;
import java.util.Scanner;

import models.Diner;
import models.Item;
import models.Table;
import process.ProcessMain;
import utils.FileManager;
import utils.LoggerUtil;
import utils.Validate;

public class Restaurant {

    // -------------------------------------------------------------------------------------------------------------- ADD ORDER
    public static Table setTableNumber(Scanner scanner, Table[] tables, int MAX) {
        if (Validate.isValidArr(tables)) {
            String msg = "Ingrese el numero de mesa (1-" + MAX + "): ";
            int tableNumber = Validate.scanValidInteger(scanner, msg, MAX);
            while (tables[tableNumber - 1].isBusy()) {
                System.out.println("Mesa " + tableNumber + " ya ocupada.\n");
                tableNumber = Validate.scanValidInteger(scanner, msg, MAX);
            }
            tables[tableNumber - 1].setBusy(true);
            return tables[tableNumber - 1];
        }

        return null;
    }

    public static int setPersonsInTable(Scanner scanner, Table table, int MAX) {
        int persons = Validate.scanValidInteger(scanner, "Ingrese el numero de comensales (1-" + MAX + "): ", MAX);
        table.setPersonsInTable(persons);
        return persons;
    }

    public static void namesInTable(Scanner scanner, Diner[] diners) {
        if (Validate.isValidArr(diners)) {
            System.out.println();
            for (int i = 0; i < diners.length; i++) {
                String name = Validate.scanValidString(scanner, "Nombre de persona " + (i + 1) + ": ");
                diners[i].setName(name);
            }
        }
    }

    public static int setItemsPerson(Scanner scanner, Diner diner, int MAX) {
        System.out.println();
        int itemsPerson = Validate.scanValidInteger(scanner, "Numero de items de " + diner.getName() + " (1-" + MAX + "): ", MAX);
        diner.setNumberOfItems(itemsPerson);
        return itemsPerson;
    }

    public static void setItemName(Scanner scanner, Item item, int index) {
        String name = Validate.scanValidString(scanner, "- Nombre item #" + (index + 1) + ": ");
        item.setName(name);
    }

    public static void setItemQuant(Scanner scanner, Item item, int index) {
        int quant = Validate.scanValidInteger(scanner,  "- Cantidad item #" + (index + 1) + ": ", 0);
        item.setQuantity(quant);
    }

    public static void setItemPrice(Scanner scanner, Item item, int index) {
        double price = Validate.scanValidDouble(scanner,  "- Precio unitario item #" + (index + 1) + ": ", 0);
        item.setPrice(price);
    }

    public static void generateInvoice(Table table) {
        if (table != null) {
            String invoiceText = ProcessMain.buildInvoiceText(table);
            String fileNameInvoice = FileManager.getInvoiceFileName();
            FileManager.writeInFile(fileNameInvoice, invoiceText);
            System.out.println("\nComanda generada con exito: " + fileNameInvoice);
        }
    }

    // -------------------------------------------------------------------------------------------------------------- SHOW TABLES
    public static void showStatusTable(Table table) {
        System.out.printf("- Mesa %02d: %s\n", table.getNumber(), table.isBusy() ? "OCUPADA" : "LIBRE");
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
            System.out.println("\nNo se encontraron resultados para el nombre " + nameToSearch);
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
            System.out.println("\nNo se encontraron resultados para la mesa " + tableToSearch);
        }
    }
}