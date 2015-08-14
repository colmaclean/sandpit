package single;

public class Runner {

    public static void main(String[] args) {
        Singleton2 s1 = Singleton2.getInstance();
        System.out.println("Singleton: " + s1.inc());
        System.out.println("Singleton: " + Singleton2.getInstance().inc());
        System.out.println("Singleton: " + s1.inc());
        Singleton2 s2 = Singleton2.getInstance();
        System.out.println("Singleton: " + s2.inc());
    }
    
}
