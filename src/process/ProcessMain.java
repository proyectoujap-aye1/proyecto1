package process;

import java.io.File;

import utils.FileManager;
import utils.LoggerUtil;

public class ProcessMain {

    public static void initAllArrays (boolean[] tables, int[] personsInTable, String[][] personNames, int[][] personItems,
    String[][][] itemNames, int[][][] itemQuants, double[][][] itemPrices) {
        LoggerUtil.log("INICIO: Inicializando matrices");
        Process.initMatrix(tables);
        Process.initMatrix(personsInTable);
        Process.initMatrix(personNames);
        Process.initMatrix(personItems);
        Process.initMatrix(itemNames);
        Process.initMatrix(itemQuants);
        Process.initMatrix(itemPrices);
        LoggerUtil.log("FIN: Inicializando matrices");
    }

    public static void clearAllArrays (boolean[] tables, int[] personsInTable, String[][] personNames, int[][] personItems,
    String[][][] itemNames, int[][][] itemQuants, double[][][] itemPrices) {
        LoggerUtil.log("INICIO: Desreferenciando matrices");
        tables = null;
        personsInTable = null;
        personNames = null;
        personItems = null;
        itemNames = null;
        itemQuants = null;
        itemPrices = null;
        LoggerUtil.log("FIN: Desreferenciando matrices");
    }

    public static String buildInvoiceText (int tableNumber, int personsNumber, String[][] personNames, int[][] personItems,
    String[][][] itemNames, int[][][] itemQuants, double[][][] itemPrices) {

        StringBuilder invoice = new StringBuilder();
        double invoiceTotal = 0;

        Process.buildInvoiceHeader(invoice, tableNumber);
        for (int personIndex = 0; personIndex < personsNumber; personIndex++) {
            if (personNames[tableNumber - 1][personIndex] != null) {
                String personName = personNames[tableNumber - 1][personIndex];
                double personTotal = 0;

                Process.buildClientInvoiceHeader(invoice, personName);
                for (int itemIndex = 0; itemIndex < personItems[tableNumber - 1][personIndex]; itemIndex++) {
                    if (itemNames[tableNumber - 1][personIndex][itemIndex] != null) {
                        double summary = Process.buildInvoiceBody(invoice, itemNames[tableNumber - 1][personIndex],
                            itemQuants[tableNumber - 1][personIndex], itemPrices[tableNumber - 1][personIndex], itemIndex);
                        personTotal += summary;
                    }
                }

                Process.buildClientInvoiceFooter(invoice, personName, personTotal);
                invoiceTotal += personTotal;
            }
        }

        Process.buildInvoiceFooter(invoice, invoiceTotal);
        return invoice.toString();
    }

    public static String searchNameInFile (File file, String name) {
        String invoiceText = FileManager.getTextFromFile(file);
        String searchResult = Process.searchNameInText(invoiceText, name);
        return searchResult;
    }

    public static String searchTableInFile (File file, int table) {
        String invoiceText = FileManager.getTextFromFile(file);
        String searchResult = Process.searchTableInText(invoiceText, table);
        return searchResult;
    }
}