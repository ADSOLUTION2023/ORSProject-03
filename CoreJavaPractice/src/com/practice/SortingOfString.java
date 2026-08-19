package com.practice;

public class SortingOfString {
	public static void main(String[] args) {

		String name = "dbca";

		char[] ch = name.toCharArray();

		for (char a = 'a'; a <= 'z'; a++) {
			for (int i = 0; i < ch.length; i++) {

				if (a == ch[i]) {
					System.out.print(ch[i]);
				}

			}
		}
	}



}
