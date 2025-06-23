import java.util.Scanner;

public class Process {

    public static void requestNames (Scanner scanner, int tableNumber, int personsNumber, String[][] namePersons) {
        for (int i = 0; i < personsNumber; i++)
            namePersons[tableNumber - 1][i] = Validate.scanValidString(scanner, "Nombre de persona " + (i + 1) + ": ");
    }

    public static void requestItems (Scanner scanner, int tableNumber, int personsNumber, String[][] namePersons, int[][] itemPersons, String[][][] nameItems, int[][][] cantItems, double[][][] priceItems, int MAX_ITEMS_BY_PERSON) {
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
