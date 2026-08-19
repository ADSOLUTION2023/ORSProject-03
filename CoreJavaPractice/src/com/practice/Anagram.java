package com.practice;

import java.util.Arrays;

public class Anagram {
	public static void main(String[] args) {
		String s1 = "silent";
		String s2 = "listen";
		
		char [] ch1 = s1.toCharArray();
		char [] ch2 = s2.toCharArray();
		
		Arrays.sort(ch1);
		Arrays.sort(ch2);
		
		if (Arrays.equals(ch1, ch2)) {
			System.out.println(s1 + " " + s2 + " is a anagram");
		}else
		System.out.println(s1 + " " + s2 + "  is not anagram");
	}

}
