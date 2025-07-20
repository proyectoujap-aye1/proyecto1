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
        invoice.append("Cliente: ").append(name).append("\n");
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
}