package single;

public class Singleton2 {

    private static Object lock = new Object();
    private static volatile Singleton2 instance = null;
    private int total = 0;
    
    private Singleton2() {
    }
    
    public static synchronized Singleton2 getInstance() {
        Singleton2 s = instance;
        if (s == null) {
            synchronized(lock) {
                s = instance;
                if (s == null) {
                    s = new Singleton2();
                    instance = s;
                }
            }
        }
        return instance;
    }
    
    public int inc() {
        return ++total;
    }
}
