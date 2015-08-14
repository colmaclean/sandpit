package lambda.sort;

import java.util.Arrays;
import java.util.List;

public class Sorter {

    public static void main(String... args) {
        
        String[] words = new String[] {"deceptively long", "short", "middling"};
        
        System.out.println("before: " + Arrays.toString(words));
        
        Arrays.sort(words, (first, second) -> Integer.compare(first.length(), second.length()));
        
        System.out.println("after: " + Arrays.toString(words));

        List<String> wordList = Arrays.asList(words);
        
        wordList.stream().forEach(x -> System.out.println(x));
        wordList.stream().forEach(System.out::println);
        wordList.stream().forEach(x -> {
            if (x.length() > 10)
                System.out.println("You are long enough: " + x);
        });

        
        Panter p = (x -> x % 2);
        
        System.out.println(p.pants(13));
        
    }
    
    interface Panter {
        int pants(int x);
    }
}
