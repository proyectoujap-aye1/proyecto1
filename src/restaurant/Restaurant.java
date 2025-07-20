package restaurant;

import java.util.Scanner;

import process.ProcessMain;
import utils.FileManager;
import utils.Validate;

public class Restaurant {

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
        System.out.println();
        int itemsPerson = Validate.scanValidInteger(scanner, "Numero de items de " + names[index] + " (1-" + MAX + "): ", MAX);
        items[index] = itemsPerson;
        return itemsPerson;
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
            System.out.println("Comanda generada con exito: " + fileNameInvoice);
        }
    }
}