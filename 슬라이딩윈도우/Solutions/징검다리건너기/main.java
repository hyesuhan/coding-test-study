class Solution {
    public int solution(int[] stones, int k) {
        // 모든 구간의 최대 중 최소를 저장.
        int MIN_VALUE = Integer.MAX_VALUE;
        
        // 인덱스를 저장하자
        Deque<Integer> deque = new ArrayDeque<>();
        
        
        for(int i = 0; i < stones.length; i++) {
            if(!deque.isEmpty() && deque.peekFirst() <= i-k) {
                // 범위에서 벗어난 인덱스
                deque.pollFirst();
            }
            
            while(!deque.isEmpty() && stones[deque.peekLast()] < stones[i]) {
                deque.pollLast();
            }
            
            // 현재 인덱스 추가
            deque.offerLast(i);
            
            if (i >= k-1) {
                // 윈도우 k 만큼 된 시점부터 최대값을 찾고 최솟값을 갱신한다.
                MIN_VALUE = Math.min(MIN_VALUE, stones[deque.peekFirst()]);
            }

        }
        
        return MIN_VALUE;
    }
}
