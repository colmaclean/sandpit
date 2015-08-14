package mlchallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Processor {

    public static void main(String[] args) {
        String[] arrTokens = new String[]{"I", "like", "going", "along", "to", "the", "beach", ".", "When", "it", "is", "nice", "."};
        String[] missWords = new String[]{"IS", "TO"};
        
        Iterable<String> iterTokens = Arrays.asList(arrTokens);
        Set<String> setMissWords = new HashSet<String>(Arrays.asList(missWords));
        
        List<String> suggestions = processString(iterTokens.iterator(), setMissWords);
        
        for (String sugg : suggestions) {
            System.out.println(sugg);
        }
    }

    
    public static List<String> processString(Iterator<String> tokens, Set<String> missWords) {
        List<String> suggestions = new ArrayList<String>();
        
        List<List<String>> subStrings = new ArrayList<List<String>>();
        List<String> subString = new ArrayList<String>();
        
        // Process input iterator into collection of sub strings
        while (tokens.hasNext()) {
            String token = tokens.next();
            if (missWords.contains(token.toUpperCase()) || token.length() < 2) {
                subStrings.add(subString);
                subString = new ArrayList<String>();
                continue;
            }
            subString.add(token);
        }
        
        // Create output.
        for (List<String> currentString : subStrings) {
            // Left to right
            for (int i = 0 ; i < currentString.size() ; i++) {
                StringBuilder sbWorking = new StringBuilder();
                for (int j = i ; j < currentString.size() ; j++) {
                    if (sbWorking.length() > 0) {
                        sbWorking.append(" ");
                    }
                    sbWorking.append(currentString.get(j));
                    suggestions.add(sbWorking.toString());
                }
            }
        }
        
        return suggestions;
    }
}
