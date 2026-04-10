### 두 큐 합 같게 만들기
[두 큐 합 같게 만들기](https://school.programmers.co.kr/learn/courses/30/lessons/118667?language=java)

두 개의 큐가 주어졌을 때 하나의 큐에서 pop 하여 다른 큐에 push 하는 작업을 통해 각 큐의 원소 합이 같도록 만들자.

이 때 필요한 작업의 최소 횟수를 구하라.

### 사용한 자료 구조

문제에서 볼 수 있듯이 이는 `Queue`를 사용하는 문제이다.
**Queue** 란 **First-In-First-Out** 의 구조로 줄을 서는 것 처럼 먼저 줄을 선 사람이 먼저 나오는 구조이다.

### Java Queue 구현 방법
Java 에서는 Queue 를 제공해 준다. 이는 LinkedList 를 Extends 한 방식이다.
```java
Queue<Integer> queue = new LinkedList<>();

queue.offer(123); // push
queue.poll(); // pop
queue.peek(); // pop이 아닌 오직 데이터만 조회만 한다.
queue.size();
queue.isEmpty();
```

그러나 나는 통일화된 구현을 위해서 **Deque** 를 이용해 구현할 것이다.(계속계속~) -> Stack에서도 쓸 수 있지만 메서드가 다르다.
Deque 는 Stack과 Queue 가 혼합된 자료구조로 둘 다 사용할 수 있다는 장점이 있다.
```java
Deque<Integer> queue = new ArrayDeque<>();

queue.offer(123); // push
queue.poll(); // pop
queue.peek();
queue.size();
queue.isEmpty();
```
단 주의할 것은 Stack으로 사용할 때는 pop/push, Queue로 사용할 때는 offer/poll 로 메서드를 사용해야 한다.(당연하게도 자료구조가 다르므로)


### ⏰ 시간 복잡도

일단 각 배열의 길이 만큼 다시 queue에 넣어야 한다.(길이를 N 이라 하자.)
`O(2N)`

다음으로 `While` 문에서 각 queue 가 같아질 때 까지 돌게 되는데, 단 두 queue 가 모두 바꿔진 경우는 더 이상 가능한 경우가 없다고 판단해 break 를 한다.
`O(4N)`

결국 O(N) 만큼의 시간이 걸린다.



### 🤯 트러블 슈팅
문제에서 다음을 이야기 했다.
`언어에 따라 합 계산 과정 중 산술 오버플로우 발생 가능성이 있어 long type 고려가 필요하다.`

전체 합에 대해서 int로 적었는데, 생각해보니 당연히 long으로 해야 하는데 안 해서 Exception이 나왔다.
