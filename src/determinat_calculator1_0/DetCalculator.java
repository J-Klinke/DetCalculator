package determinat_calculator1_0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DetCalculator {
    private double[][] matrix;

    /**
     * reads the user input in order to generate a two-dimensional square double array, the matrix, which determinant
     * will be calculated. Since the size of the array isn't given in the beginning, this method generates an ArrayList
     * which the helper method convertArrayListToArray will convert to the two-dimensional array.
     * @return matrix, the generated matrix, a two-dimensional double array
     */
    public double[][] inputReader() {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Hallo, dieser Rechner kann die Determinante einer Matrix errechnen.");
        System.out.println("Beginne mit der Eingabe der ersten Spalte der Matrix, trenne jeden Eintrag mit einem Komma. " +
                            "Kommazahlen werden so eingegeben: 4.4");
        int matrixSize = 1;
        ArrayList<Double> matrixAsArrayList = new ArrayList<>();

        for (int i = 0; i < matrixSize; i++) {
            try {
                if (i == 1) {
                    System.out.println("Die Größe der eingegeben Matrix ist " + matrixSize + "x" + matrixSize +".");
                }
                if (matrixSize != 1) {
                    System.out.println("Bisher eingegeben:");
                    printMatrix(convertArrayListToArray(i, matrixSize, matrixAsArrayList));
                    System.out.println();
                    System.out.println("Jetzt Zeile " + (i+1) + " von " + matrixSize);
                }
                String input = in.nextLine();
                if (input.isBlank()) {
                    System.out.println("Du hast nichts eingegeben. Versuche es erneut.");
                    i--;
                } else {
                    String[] inputString = input.split(",");
                    if (i != 0 && inputString.length > matrixSize) {
                        System.out.println("Du hast mehr Zahlen eingegeben, als die Matrix groß ist. Versuche es nochmal.");
                        i--;
                        continue;
                    } else if (i != 0 && inputString.length < matrixSize) {
                        System.out.println("Du hast zu wenig Zahlen eingegeben. Versuche es nochmal.");
                        i--;
                        continue;
                    }

                    if (i == 0) {
                        matrixSize = inputString.length;
                    }
                    for (String s : inputString) {
                        matrixAsArrayList.add(Double.parseDouble(s));
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Du hast keine Nummer eingegeben, Versuche es nochmal.");
                i--;
            }
        }

        double[][] matrix = convertArrayListToArray(matrixSize, matrixSize, matrixAsArrayList);

        System.out.println();
        System.out.println("Eingegebene Matrix: ");
        printMatrix(matrix);
        System.out.println("____________________________");
        System.out.println();
        return matrix;
    }

    /**
     * prints the given matrix to the console
     * @param matrix the matrix to be printed
     */
    public void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }

    }

    /**
     * this helper method converts the Arraylist of the inputReader() method to the two-dimensional double array
     * @param rows number of rows of the matrix to be converted
     * @param columns number of columns of the matrix to be converted
     * @param matrixAsArrayList the matrix in its form as an ArrayList
     * @return the converted matrix
     */
    public double[][] convertArrayListToArray (int rows, int columns, ArrayList<Double> matrixAsArrayList) {
        double[][] matrix = new double[rows][columns];
        for (int h = 0; h < rows; h++) {
            for (int i = 0; i < columns; i++) {
                matrix[h][i] = matrixAsArrayList.get(i + (h * columns));
            }
        }
        return matrix;
    }

    /**
     * calculates the determinant of the entered matrix. If the matrix ist bigger than 2x2, the method calls the method
     * getStreichungsMatrix(), and calls itself recursively with the parameter returned by getStreichungsMatrix()
     * @param matrix the two-dimensional double array generated in inputReader()
     * @return result, determinant of matrix
     */
    public double calculator(double[][] matrix) {
        double result = 0;
        if (matrix.length == 1) {
            result = matrix[0][0];
        } else if (matrix.length == 2) {
            result = matrix[0][0]*matrix[1][1] - matrix[1][0]*matrix[0][1];
        } else {
            for (int i = 0; i < matrix.length ; i++) {
                // det(A) = Summe von i=1 bis: (-1)^(i+1)*a(i1)*det(A(i1))
                result += (Math.pow(-1,(i+2)))*(matrix[i][0])*(calculator(getStreichungsMatrix(matrix,i)));
            }
        }
        return result;
    }

    /**
     * generates a 'Streichungsmatrix', a smaller version of the original matrix
     * @param matrix the original matrix, a two-dimensional double array
     * @param entwicklungsZeile the row along which the generation of the 'Streichungsmatrix' happens
     * @return streichungsMatrix, a two-dimensional double array
     */
    public double[][]  getStreichungsMatrix(double[][] matrix, int entwicklungsZeile) {
        double[][] streichungsMatrix = new double[matrix.length-1][matrix.length-1];
        for (int spalte = 0; spalte < matrix.length-1; spalte++) {
            if (entwicklungsZeile == 0) {
                for (int zeile = 0; zeile < streichungsMatrix.length; zeile++) {
                    streichungsMatrix[zeile][spalte] = matrix[zeile+1][spalte+1];
                }
            } else if (entwicklungsZeile == matrix.length-1) {
                for (int zeile = 0; zeile < streichungsMatrix.length; zeile++) {
                    streichungsMatrix[zeile][spalte] = matrix[zeile][spalte+1];
                }
            } else {
                for (int zeile = 0; zeile < entwicklungsZeile; zeile++) {
                    streichungsMatrix[zeile][spalte] = matrix[zeile][spalte+1];
                }
                for (int zeile = entwicklungsZeile; zeile < streichungsMatrix.length; zeile++) {
                    streichungsMatrix[zeile][spalte] = matrix[zeile+1][spalte+1];
                }
            }
        }
        return streichungsMatrix;
    }


    public static void main(String[] args) {
        DetCalculator detCalculator = new DetCalculator();
        detCalculator.matrix = detCalculator.inputReader();
        System.out.println("Determinante der eingeben Matrix = " + detCalculator.calculator(detCalculator.matrix));
        System.out.println();
    }
}
