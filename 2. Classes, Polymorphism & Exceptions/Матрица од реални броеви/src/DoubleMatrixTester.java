import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

class InsufficientElementsException extends Exception{
    public String getMessage(){
        return "Insufficient number of elements";
    }
}

class InvalidRowNumberException extends Exception{
    public String getMessage(){
        return "Invalid row number";
    }
}

class InvalidColumnNumberException extends Exception{
    public String getMessage(){
        return "Invalid column number";
    }
}

final class DoubleMatrix{
    private double a[][];
    private int m;
    private int n;

    DoubleMatrix(double a[], int m, int n) throws InsufficientElementsException {
        if(a.length < m * n)
            throw new InsufficientElementsException();
        this.m = m;
        this.n = n;
        this.a = new double[m][n];
        int asd = a.length - m*n;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                this.a[i][j] = a[asd];
                asd++;
            }
        }
    }

    public String getDimensions(){
        return ("[" + m + " x " + n + "]");
    }

    public int rows(){ return m; }
    public int columns() { return n; }

    public double maxElementAtRow(int row) throws InvalidRowNumberException{
        if(row > m || row <= 0)
            throw new InvalidRowNumberException();
        double max = a[row-1][0];
        for(int i = 0; i < n; i++){
            if(a[row-1][i] > max)
                max = a[row-1][i];
        }

        return max;
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException{
        if(column > n || column <= 0)
            throw new InvalidColumnNumberException();
        double max = a[0][column-1];
        for(int i = 0; i < m; i++){
            if(a[i][column-1] > max)
                max = a[i][column-1];
        }
        return max;
    }

    public double sum() {
        double t = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                t+= a[i][j];
            }
        }
        return t;
    }

    public double[] toSortedArray(){
        double[] newArray = new double[m*n];
        int arrC = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j  < n; j++){
                newArray[arrC++] = a[i][j] * -1;
            }
        }

        Arrays.sort(newArray);

        for(int i = 0; i < newArray.length; i++)
            newArray[i] *= -1;

        return newArray;
    }

    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = "";
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                s += decimalFormat.format(a[i][j]);
                if(j < n-1)
                    s+="\t";
            }
            if(i < m - 1)
                s+="\n";
        }
        return s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(a);
        result = prime * result + m;
        result = prime * result + n;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoubleMatrix other = (DoubleMatrix) obj;
        if (!Arrays.deepEquals(a, other.a))
            return false;
        if (m != other.m)
            return false;
        if (n != other.n)
            return false;
        return true;
    }
}

class MatrixReader{
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner sc = new Scanner(input);
        int m = sc.nextInt();
        int n = sc.nextInt();

        double a[] = new double[m*n];
        int i = 0;
        while(sc.hasNextDouble()) {
            a[i++] = sc.nextDouble();
        }

        DoubleMatrix dm = new DoubleMatrix(a, m, n);
        return dm;
    }
}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}
