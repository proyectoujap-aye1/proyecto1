import java.util.Scanner;

public class Validate {

    public static int scanValidInteger(Scanner scanner, String msg, int limit) {
        while (true) {
            try {
                System.out.print(msg);
                String enteredValue = scanner.nextLine();
                int value = Integer.parseInt(enteredValue);

                if (limit > 0) { // Si se recibe un valor mayor a 0, es porque el valor ingresado debe tener un limite
                    if (value < 1 || value > limit) {
                        System.out.println("Cantidad invalida. Debe ser entre 1 y " + limit);
                    } else {
                        return value;
                    }
                }
                else { // En caso de que sea 0, es porque el valor ingresado no tiene un limite
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
            }
        }
    }
    
    public static double scanValidDouble(Scanner scanner, String msg, int limit) {
        while (true) {
            try {
                System.out.print(msg);
                String enteredValue = scanner.nextLine();
                double value = Double.parseDouble(enteredValue);

                if (limit > 0) { // Si se recibe un valor mayor a 0, es porque el valor ingresado debe tener un limite
                    if (value < 1 || value > limit) {
                        System.out.println("Cantidad invalida. Debe ser entre 1 y " + limit);
                    } else {
                        return value;
                    }
                } else { // En caso de que sea 0, es porque el valor ingresado no tiene un limite
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
            }
        }
    }

}
