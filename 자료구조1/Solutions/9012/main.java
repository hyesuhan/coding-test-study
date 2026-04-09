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
