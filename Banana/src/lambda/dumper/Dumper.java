package lambda.dumper;

import java.util.Arrays;
import java.util.List;

public class Dumper {

    public interface IDump {
        void dumpThem(String name);
        
        default void megaBanana() {
            System.out.println("banana me up");
        }
    }
    
    public static void main (String[] args) {
        List<String> names = (List<String>)Arrays.asList(new String[] {"banana", "apple", "pear"});
        
        IDump dump = name -> {
            System.out.println("Name: " + name);
        };
        
        for (String name : names) {
            dump.dumpThem(name);
        }
        
        dump.megaBanana();
    }
}
