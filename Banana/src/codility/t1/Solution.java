package codility.t1;

//you can also use imports, for example:
//import java.util.*;

//you can use System.out.println for debugging purposes, e.g.
//System.out.println("this is a debug message");



class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int result = sol.solution(new int[] { 3, 1, 2, 4, 3 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 0, 2000 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 2000 } );
        System.out.println("Result: " + result);
    }
    
    public int solution(int[] arrValues) {
        // write your code in Java SE 8
        long leftSum = 0;
        long rightSum = 0;
        long minDiff = 0;

        if (arrValues != null && arrValues.length > 1) {
            
            minDiff = Long.MAX_VALUE;

            for (int sub = 0; sub < arrValues.length; sub++) {
                rightSum += arrValues[sub];
            }

            for (int sub = 0; sub < arrValues.length; sub++) {
                int currentVal = arrValues[sub];
                leftSum += currentVal;
                rightSum -= currentVal;
                long currDiff = leftSum - rightSum;
                
                if (currDiff < 0) {
                    currDiff *= -1;
                }
                if (currDiff < minDiff) {
                    minDiff = currDiff;
                }
            }
        }
        return (int)minDiff;
    }
}