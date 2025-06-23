import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
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


    private static Boolean hasSpecChars(String value) {
        String invalids = "0123456789.,!#$%/()=?¡¨*[]_+-{}";

        for (int i = 0; i < value.length(); i++) {
            for (int j = 0; j < invalids.length(); j++) {
                if (value.charAt(i) == invalids.charAt(j)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String scanValidString(Scanner scanner, String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacio.");
                continue;
            }

            if (hasSpecChars(input)) {
                System.out.println("Error: El nombre solo puede contener letras y espacios.");
                continue;
            }

            return input;
        }
    }

    public static String checkDirectory(String route) throws IOException {
        String txt = "";

        if (route.trim().isEmpty()) {
            txt = "Manage-Error: Ruta esta vacia.";
            throw new IllegalArgumentException(txt);
        }

        File directory = new File(route);
        if (!directory.exists()) {
            txt = "Manage-Error: El directorio a guardar no existe.";
            throw new NoSuchFileException(txt);
        }

        return route;
    }
}
