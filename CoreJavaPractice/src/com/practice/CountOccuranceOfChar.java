package com.practice;

public class CountOccuranceOfChar {
	public static void main(String[] args) {
		/*String s = "Amit Chandsarkar";
		int count = 0;
		
		for(int i = 0;i<s.length();i++) {
			char ch = s.charAt(i);
			String m = String.valueOf(ch);
			if(m.matches("r"))
			count++;
		}
		
	
		System.out.println(m + count);
	}*/
		String s = "Hello Java";

		int count = 0;

		for(int i = 0; i < s.length(); i++) {

		    char ch = Character.toLowerCase(s.charAt(i));

		    if(ch == 'a' || ch == 'e' || ch == 'i' ||
		       ch == 'o' || ch == 'u') {
		        count++;
		    }
		}

		System.out.println(count);
}
}
