package fizzbuzz;

public class FizzBuzzExample {

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            StringBuilder sb = new StringBuilder();
            if (i % 3 == 0) {
                sb.append("Fizz");
            }
            if (i % 5 == 0) {
                sb.append("Buzz");
            }
            System.out.println(sb.length() > 0 ? sb.toString() : i);
        }
    }
}
