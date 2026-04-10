import java.util.*;

class Solution {
    
    public int solution(int[] queue1, int[] queue2) {
        long q1Sum = 0;
        long q2Sum = 0;
        int ans = 0;
        
        Deque<Integer> q1 = new ArrayDeque<>();
        Deque<Integer> q2 = new ArrayDeque<>();
        
        
        // 각 큐의 합을 구했다.
        for (int q : queue1) {
            q1Sum += q;
            q1.offer(q);
        }
        
        for (int q : queue2) {
            q2Sum += q;
            q2.offer(q);
        }
        
        if (q1.isEmpty()) return 0;
        
        while (q1Sum != q2Sum) {
            
            if (ans > (q1.size() + q2.size())*2) return -1;
            
            if (q1Sum > q2Sum) {
                int pop = q1.poll();
                q1Sum -= pop;
                q2.offer(pop);
                q2Sum += pop;
            } else {
                int pop = q2.poll();
                q2Sum -= pop;
                q1.offer(pop);
                q1Sum += pop;
            }
            
                ans++;
        }
        
        
        if ( q1Sum != q2Sum) ans = -1;
        
        return ans;
    }
}
