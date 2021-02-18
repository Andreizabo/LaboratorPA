public class Main {

    public static void main(String[] args) {
        System.out.print("Hello world!\n");

        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        int result = addDigits(transform(n));
        System.out.printf("Willy-nilly, this semester I will learn %d languages", result);
    }

    public static int transform(int x) {
        x *= 3;
        x += 0b10101;
        x += 0xFF;
        x *= 6;

        return x;
    }

    public static int addDigits(int x) {
        int digitSum, digitCount;
        do {
            digitCount = 0;
            digitSum = 0;
            while (x != 0) {
                digitSum += (x % 10);
                ++digitCount;
                x /= 10;
            }
            x = digitSum;
        }
        while(digitCount > 1);

        return x;
    }
}
