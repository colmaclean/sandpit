package codility.brackets;

import java.util.Stack;


/*
    A string S consisting of N characters is considered to be properly nested if any of the following conditions is true:
    
    S is empty;
    S has the form "(U)" or "[U]" or "{U}" where U is a properly nested string;
    S has the form "VW" where V and W are properly nested strings.
    For example, the string "{[()()]}" is properly nested but "([)()]" is not.
    
    Write a function:
    
    class Solution { public int solution(String S); }
    
    that, given a string S consisting of N characters, returns 1 if S is properly nested and 0 otherwise.
    
    For example, given S = "{[()()]}", the function should return 1 and given S = "([)()]", the function should return 0, as explained above.
    
    Assume that:
    
    N is an integer within the range [0..200,000];
    string S consists only of the following characters: "(", "{", "[", "]", "}" and/or ")".
    Complexity:
    
    expected worst-case time complexity is O(N);
    expected worst-case space complexity is O(N) (not counting the storage required for input arguments).
*/

class Solution {
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        String q = "{[()()]}";
        System.out.println(q + " is good: " + sol.solution(q));
        
        q = "([)()]";
        System.out.println(q + " is good: " + sol.solution(q));
        
        q = "({[]";
        System.out.println(q + " is good: " + sol.solution(q));
    }
    
    public int solution(String S) {
        // write your code in Java SE 8
        
        Stack<Character> brackets = new Stack<Character>();
        if (S == null) {
            return 0;
        } else if (S.length() == 0) {
            return 1;
        } else {
            for (char c : S.toCharArray()) {
                if (c == '{' || c == '(' || c == '[') {
                    brackets.push(c);
                } else if (c == '}' || c == ')' || c == ']') {
                    if (brackets.empty()) {
                        return 0;
                    }
                    char d = brackets.pop();
                    if ((c == '}' && d != '{') || (c == ')' && d != '(') || (c == ']' && d != '[')) {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        }
        if (brackets.isEmpty()) {
            return 1;
        }
            
        return 0;
    }
}