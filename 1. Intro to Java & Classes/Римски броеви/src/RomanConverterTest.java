import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        // your solution here
        int[] numbers = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
        String[] romans = { "I", "IV", "V", "IX", "X", "XL", "L",
                "XC", "C", "CD", "D", "CM", "M" };
        int[] finalNumber = new int[7];

        int tmp = n;
        for(int i = 12; i >= 0; i--){
            int leftestDigit = tmp / numbers[i];
            for(int j = 0; j < leftestDigit; j++){
                System.out.print(romans[i]);
            }
            tmp = tmp - leftestDigit * numbers[i];
        }
        return "";
    }

}
