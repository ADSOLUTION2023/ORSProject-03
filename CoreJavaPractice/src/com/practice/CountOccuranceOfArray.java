package com.practice;

public class CountOccuranceOfArray {
	public static void main(String[] args) {
		String[] names = { "abc", "abca" };

		for (char a = 'a'; a <= 'z'; a++) {
			int count = 0;
			for (String s : names) {
				s = s.toLowerCase();
				for (int i = 0; i < s.length(); i++) {
					if (a == s.charAt(i)) {
						count++;
					}
				}
			}

			if (count != 0) {
				System.out.println(a + ": " + count);
			}
		}
	}
}

