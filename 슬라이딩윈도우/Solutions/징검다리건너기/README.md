## 🔎징검 다리 건너기
[2019 카카오 개발자 겨울 인턴십: 징검다리 건너기](https://school.programmers.co.kr/learn/courses/30/lessons/64062)

**정확성과 효율 테스트 각 점수가 있습니다.**

선생님이 친구들이 무사히 징검다리를 건널 수 있게 해 주세요.

징검다리는 일렬로 놓여 있고 각 징검다리의 디딤돌의 숫자는 한 번 밟을 때마다 1씩 줄어듭니다.

디딤돌 숫자가 0이면 더 이상 밟을 수 없습니다. 이 때는 그 다음 디딤돌로 한번에 여러 칸 건널 수 있습니다.

단, 다음 밟을 수 있는 디딤돌이 여러 개인 경우 무조건 가까운 디딤돌만 건너뛸 수 있습니다.

왼쪽에서 오른쪽으로 건너야 건넌 것으로 인정합니다.

한 번에 한 명씩 건너야 하며, 한 사람이 모두 건넌 후 시작합니다.

`stones` 는 디딤돌 적힌 숫자 입니다.

건너뛸 수 있는 디딤돌 최대 칸수 k 입니다.

최대 몇 명까지 건널 수 있을까요?

## 🖐️제약 사항 분석

건너야 하는 친구들의 수는 INFINITE 입니다.

stones 배열의 크기는 1 ~ 200,000 이하 입니다.

각 배열의 원소는 1 ~ 200,000,000 이하 자연수 입니다.

k는 1이상 stones의 길이 이하인 자연수입니다.


## 구현 1 ❌
> 만약 그냥 반복문을 돌게 되면 어떻게 해야 할까?

```
stones 를 방문한다.
방문했을 때 0 이라면 그 다음으로 간다.
0이 아니라면 가는데,
  만약  방문이 k 보다 크다면 return;
  아니라면 건너뛴다.
```

### 트러블 슈팅

`error: unreachable statement`
이는 `java`에서 무한 루프를 돌게되면 나오게 된다.
> 디딤돌이 연속되었을 때만 고려해야 하는데, 전체 0을 카운팅했다.

하지만 더 큰 문제는 사실 시간 내 돌지 못한다는 것이다.

그래서 고민한 점은
1. 한 번의 루프만 돌고 끝날 수 있을까?
2. 그렇다면 **미리 몇 명이 지나갈 수 있을지** 찾을 수 있지 않을까?
3. 추가로 `stones[i]--` 를 처리하지 않아도 구현할 수 있지 않을까?


## 구현 2❌ - 이분 탐색
> 초점을 기대되는 **결과값**의 사람 수를 고민하고 이를 통과할 수 있을 지 **검증**하자.

``` java
public int solution(int[] stones, int k) {
        int left = 1;
        int right = 200000; // stone 배열 최대 길이
        int ans = 0;
        
        while (left <= right) {
            
            int mid = (left+right)/2;
            
            if(validCross(stones, k, mid)) {
                
                // mid 명이 건널 수 있다면 더 큰 숫자를 시도한다.
                ans = mid;
                left = mid+1;
                
            } else {
                right = mid - 1;
            }
            
        }
        return ans;
    }
    
    private boolean validCross(int[] stones,int k, int mid) {
        
        int cnt = 0;
        
        for (int stone: stones) {
            // 만약 mid 명이 건널 수 없다.
            if (stone < mid) {
                cnt++;
                if(cnt >= k) return false;
            } else {
                cnt = 0;
            }
        }
        return true;
        
    }
```

결과는 통과이지만 효율성은 실패하는 상황... 😭

그렇다면 다른 방향으로 풀어야 하는데, 다시 생각해보면

**연속된 k개의 돌이 모두 0** 이 된다면 징검다리를 건널 수 없다.

만약 다음과 같은 연속된 구간과 k가 3이라면

  4 -> 7 -> 5
  이 구간에서 지날 수 있는 최대의 사람은 7 명이라는 것이다.

그렇다면 슬라이딩 윈도우를 쓸 수 있지 않을까? 라는 생각이 들었다.

Deque를 이용해 구현했는데,

#### 수도 코드
1. 새로운 인덱스 `i` 가 들어온다.
2. Deque 의 맨 앞인 `peekFirst` 인덱스가 현재 윈도우 범위인 i-k+1 ~i 를 벗어났다면 바로 제거한다.
3. 현재 값인 stones[i] 보다 작은 값을 가진 deque 뒤쪽 인덱스를 모두 제거한다.(어짜피 현재가 더 크고 나중에 나가기 때문에 더 작은 값은 고려할 필요가 없다.)
4. 현재 인덱스 `i` 를 Deque 뒤에 삽입한다.
5. Deque의 가장 앞이 최대 이므로 이를 기준으로 MIN_VALUE 중 더 작은 값이 결과가 된다.

```java
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
```

## 시간 복잡도 ⏱️

생각보다 코드는 간단하게 나왔네요!

각 배열을 한 번씩 순회하기에 `O(N)` 이 소요됩니다.

한 번 삽입 , 한 번 제거 됩니다.

