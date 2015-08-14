package lambda.thread;

public class Threader {

    public static void main(String... args) {
        repeatMessage("Hi", 5);
    }
    
    public static void repeatMessage(String text, int count) {
        Runnable r = () -> {
           for (int i = 0; i < count; i++) {
              System.out.println(text);
              Thread.yield();
           }
        };
        new Thread(r).start();
     }
    
}
