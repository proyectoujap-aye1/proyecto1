public class Process {
public static void initMatrix (boolean[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++)
                m[i] = false;
        }
    }

    public static void initMatrix (int[] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++)
                m[i] = 0;
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

    public static void initMatrix (int[][] m) {
        if (m != null) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++)
                    m[i][j] = 0;
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
}
