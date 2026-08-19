package com.practice;

public class Reverse {
	public static void main(String[] args) {
		/*
		 * String s = "JAVA"; String rev = "";
		 * 
		 * for (int i = s.length() - 1; i >= 0; i--) { rev = rev + s.charAt(i); }
		 * System.out.println(rev);
		 */
		
		String s = "JAVA";

		String rev = new StringBuilder(s).reverse().toString();
		
		System.out.println(rev);

	}
	

}
