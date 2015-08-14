package single;

public class Singleton {

    private static Singleton instance = null;
    private int total = 0;
    
    private Singleton() {
    }
    
    public static synchronized Singleton getInstance() {

        if (instance == null) {
                instance = new Singleton();
        }
        return instance;
    }
    
    public int inc() {
        return ++total;
    }
}
