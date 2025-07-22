package process;

public class Process {

    // ----------------------------------------------------------------------------------------------------------------------------- INITS

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

    // ----------------------------------------------------------------------------------------------------------------------------- INVOICE

    public static void buildInvoiceHeader (StringBuilder invoice, int tableNumber) {
        invoice.append("Factura\n");
        invoice.append("Mesa: ").append(tableNumber).append("\n\n");
    }

    public static void buildClientInvoiceHeader (StringBuilder invoice, String name) {
        invoice.append("Cliente: ").append(name.toLowerCase()).append("\n");
        invoice.append("-----------------------------------------------------------\n");
    }

    public static double buildInvoiceBody (StringBuilder invoice, String[] names, int[] quants, double[] prices, int index) {
        String name = names[index];
        int quant = quants[index];
        double price = prices[index];

        double summary = quant * price;
        invoice.append(name).append(" - Cantidad: ").append(quant).append(" - Precio: $").append(price)
            .append(" - Subtotal: $").append(summary).append("\n");

        return summary;
    }

    public static void buildClientInvoiceFooter (StringBuilder invoice, String name, double personTotal) {
        invoice.append("Total de ").append(name).append(": $").append(personTotal).append("\n\n");
    }

    public static void buildInvoiceFooter (StringBuilder invoice, double invoiceTotal) {
        invoice.append("-----------------------------------------------------------\n");
        invoice.append("Total General de la Mesa: $").append(invoiceTotal);
    }

    // ----------------------------------------------------------------------------------------------------------------------------- SEARCH

    public static String searchNameInText (String text, String name) {
        // Caso base: si el texto está vacío
        if (text.isEmpty()) {
            return null;
        }

        // Buscar el inicio de la sección del cliente actual
        int sectionStart = text.indexOf("Cliente: ");
        if (sectionStart == -1) {
            return null; // No hay más clientes
        }

        // Buscar el final del nombre del cliente actual
        int endName = text.indexOf("\n", sectionStart);
        String currentClient = text.substring(sectionStart + 9, endName).trim();

        // Buscar el inicio de la siguiente sección
        int nextSectionStart = text.indexOf("Cliente: ", endName);

        // Extraer la sección completa del cliente actual
        String clientSection = nextSectionStart == -1 ? text.substring(sectionStart) : text.substring(sectionStart, nextSectionStart);

        // Comprobar si es el cliente buscado
        if (currentClient.equals(name)) {
            return clientSection.trim();
        } else {
            // Llamada recursiva con el resto del texto
            String invoiceRest = nextSectionStart == -1 ? "" : text.substring(nextSectionStart);
            return searchNameInText(invoiceRest, name);
        }
    }

    public static String searchTableInText (String text, int table) {
        String[] lines = text.split("\n");
        int[] counters = new int[2]; // [0] = personas, [1] = artículos
        double[] total = new double[1];

        // Buscar la mesa y procesar
        String tableStr = processTable(lines, 0, table, counters, total);
        if (tableStr != null) {
            return String.format(
                "Mesa %s\n" +
                "Nro. de Personas: %d\n" +
                "Total de Items: %d\n" +
                "Total de la Comanda: $%.1f"
            , tableStr, counters[0], counters[1], total[0]);
        }

        return null;
    }

    private static String processTable(String[] lines, int index, int table, int[] counters, double[] total) {
        if (index >= lines.length)
            return null;

        if (lines[index].startsWith("Mesa: ")) {
            int currentTable = Integer.parseInt(lines[index].split(": ")[1].trim());
            if (currentTable == table) {
                String result = processClients(lines, index + 1, counters, total); // Llamada recursiva para procesar el resto del documento
                return (result != null) ? String.valueOf(currentTable) : null; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
            }

            return null;
        }

        String result = processTable(lines, index + 1, table, counters, total); // Llamada recursiva para seguir buscando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }

    // Recursividad no final
    private static String processClients(String[] lines, int index, int[] counters, double[] total) {
        if (index >= lines.length)
            return "fin";

        if (lines[index].startsWith("Cliente: ")) {
            counters[0]++; // Incrementar contador de personas
            String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para procesar artículos del cliente
            return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
        }

        String result = processClients(lines, index + 1, counters, total); // Llamada recursiva para seguir procesando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }

    // Recursividad no final
    private static String processItems(String[] lines, int index, int[] counters, double[] total) {
        if (index >= lines.length)
            return "fin";

        if (lines[index].contains("- Subtotal: $")) {
            String subtotalStr = lines[index].split("Subtotal: \\$")[1].split(" ")[0].trim();
            total[0] += Double.parseDouble(subtotalStr);
            counters[1]++; // Incrementar contador de artículos

            String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para procesar siguiente artículo
            return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
        }

        if (lines[index].contains("Total de ")) {
            // Volver a procesar clientes
            // Llamada cruzada entre funciones -> Recursividad indirecta
            return processClients(lines, index + 1, counters, total);
        }

        String result = processItems(lines, index + 1, counters, total); // Llamada recursiva para seguir procesando
        return result; // OPERACIÓN POSTERIOR a la llamada recursiva -> Recursión no final
    }
}