### 괄호

[9012 괄호](https://www.acmicpc.net/problem/9012)

괄호 문자열은 `(` 과 `)` 만으로 구성되어 있는 문자열 입니다.

이 때 올바르게 괄호가 있다면 `YES` 아니라면 `NO` 로 나타내세요.


### 사용한 자료 구조

`(` 가 들어온다면 자료구조의 가장 위에 쌓이고, `)` 가 들어온다면 자료 구조의 가장 위에서 하나를 빼면 된다.
이 행위를 했을 때 마지막에 아직 `(` 가 있다면 이는 올바른 괄호 문자열이 아니다.

이에 따라 나는 **stack** 자료구조를 택했다.
이는 마지 책 처럼 나중에 들어간 것이 먼저 나오는 형태이다.

**Last-In-Fisrt-Out**

### Java Stack

Java 에서 Stack 클래스를 제공해준다. (나는 잘 안 쓰게 되더라)
Vector 기반이어서 지금은 사용을 권장하지 않는다. 

```java
import java.util.Stack;

Stack<Integer> stack = new Stack<>();
```

대신 `ArrayDeque` 을 사용해 구현하는 것을 더 추천한다.
이는 Stack 뿐 아니라 Queue, deque 구현 시 가장 많이 사용한다.

```java
import java.util.deque;

Deque<Integer> stack = new ArrayDeque<>();

stack.push(123); // push
stack.pop(); // pop 의 주의 요소로 비어있다면 NoSuchElementException 발생 가능하다.
stack.peek(); // 확인만 한다.
stack.contain(123);
stack.size();
```

### 구현

```java
import java.util.*;
import java.io.*;

public class Main {
    
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		Deque<Character> stack = new ArrayDeque<>();
		
		for (int i = 0; i < N; i++) {
			String PS = br.readLine();
			char[] splits = PS.toCharArray();
			
			for(char split : splits) {
				if (split == '(') stack.push(split);
				else stack.pop();
			}
			
			if (stack.isEmpty()) System.out.println("YES");
			else System.out.println("NO");
			
			stack.clear();
		}
		
	}
}
```

### 트러블 슈팅

`NoSuchElement` 가 뜨면서 런타임 초과가 나왔다.

이는 존재하지 않는 것을 가져오려 할 때 발생되게 된다.

내 생각에는 

```java
for(char split : splits) {
	if (split == '(') stack.push(split);
	else stack.pop();
}
```

아마도 여기서 pop 할 때 비어있으면 Exception 이 나는 것 같아서 수정을 해야 할 것 같다.

```java
for(char split : splits) {
	if (split == '(') stack.push(split);
	else {
		if (stack.isEmpty()) {
			stack.push('F');
			break;
	}
	stack.pop();
	}
}
```

이런식으로 야매?로 Flag 를 걸어서 중간에 멈출 수 있게 했다.
