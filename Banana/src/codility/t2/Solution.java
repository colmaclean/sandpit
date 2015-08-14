package codility.t2;


//you can also use imports, for example:
//import java.util.*;

//you can use System.out.println for debugging purposes, e.g.
//System.out.println("this is a debug message");



class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int result = sol.solution(new int[] { -1, 3, -4, 5, 1, -6, 2, 1 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 1, 1, 1, 1 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 1, 1, 1 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 2000 } );
        System.out.println("Result: " + result);
        result = sol.solution(new int[] { 500, 1, -2, -1, 2 } );
        System.out.println("Result: " + result);
    }
    
    public int solution(int[] arrValues) {
        // write your code in Java SE 8
        long leftSum = 0;
        long rightSum = 0;

        if (arrValues != null && arrValues.length > 1) {

            for (int sub = 1; sub < arrValues.length; sub++) {
                rightSum += arrValues[sub];
            }

            for (int sub = 0; sub < arrValues.length - 1; sub++) {
                leftSum += arrValues[sub];
                rightSum -= arrValues[sub+1];
                if (leftSum == rightSum) {
                    return sub+1;
                }
            }
        }
        return -1;
    }
}