package single;

public class Runner2 {

    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        System.out.println("Singleton: " + s1.inc());
        System.out.println("Singleton: " + Singleton.getInstance().inc());
        System.out.println("Singleton: " + s1.inc());
        Singleton s2 = Singleton.getInstance();
        System.out.println("Singleton: " + s2.inc());
    }
    
}
