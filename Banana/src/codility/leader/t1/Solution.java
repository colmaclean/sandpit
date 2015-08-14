package codility.leader.t1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 A zero-indexed array A consisting of N integers is given. The dominator of array A is the value that occurs in more than half of the elements of A.

 For example, consider array A such that

 A[0] = 3    A[1] = 4    A[2] =  3
 A[3] = 2    A[4] = 3    A[5] = -1
 A[6] = 3    A[7] = 3

 The dominator of A is 3 because it occurs in 5 out of 8 elements of A (namely in those with indices 0, 2, 4, 6 and 7) and 5 is more than a half of 8.

 Write a function

 class Solution { public int solution(int[] A); }

 that, given a zero-indexed array A consisting of N integers, returns index of any element of array A in which the dominator of A occurs. The function should return -1 if array A does not have a dominator.

 Assume that:

 N is an integer within the range [0..100,000];
 each element of array A is an integer within the range [-2,147,483,648..2,147,483,647].
 For example, given array A such that

 A[0] = 3    A[1] = 4    A[2] =  3
 A[3] = 2    A[4] = 3    A[5] = -1
 A[6] = 3    A[7] = 3
 the function may return 0, 2, 4, 6 or 7, as explained above.

 Complexity:

 expected worst-case time complexity is O(N);
 expected worst-case space complexity is O(1), beyond input storage (not counting the storage required for input arguments).
 Elements of input arrays can be modified.
 */

class Solution {
    
    public static void main(String... args) {
        Solution sol = new Solution();
        
        int[] arr = {5, 4, 3, 2, 3, -1, 3, 3, 2, 2, 2};
        
        int dominator = sol.solution(arr);
        
        System.out.println("Dominator: " + dominator);
    }
    
    
    public int solution(int[] arr) {

        Map<Integer, List<Integer>> processMap = new HashMap<Integer, List<Integer>>();
        
        int recordCount = -1;
        int recordNumber = -1;

        for (int i=0 ; i < arr.length ; i++) {
            List<Integer> list;
            if (processMap.containsKey(arr[i])) {
                list = processMap.get(arr[i]);
                list.add(i);
            } else {
                list = new ArrayList<Integer>();
                list.add(i);
                processMap.put(new Integer(arr[i]), list);
            }
            if (list.size() > recordCount) {
                recordNumber = arr[i];
                recordCount = list.size();
            } else if (list.size() == recordCount) {
                recordNumber = -1;
            }
        }
        
        System.out.println("Record number is " + recordNumber + " with count " + recordCount);
        
        
        if (recordNumber != -1) {
            return processMap.get(recordNumber).get(0);
        } else {
            return -1;
        }
    }
}