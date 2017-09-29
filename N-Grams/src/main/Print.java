package main;

public class Print {
    public static void print(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void print(String format) {
        System.out.println(format);
    }
}
