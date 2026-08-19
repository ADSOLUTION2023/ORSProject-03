package com.practice;

import java.util.Arrays;

public class CountInt {

	public static void main(String[] args) {

		String[] s = { "amit1236788" };
		String r = "";

		for (String n : s) {
			char[] ch1 = n.toCharArray();
			for (int i = 0; i < ch1.length; i++) {
				if (Character.isDigit(ch1[i])) {
				r = r + ch1[i];
				}
			}
			int[] intAr = new int[r.length()];
			for (int i = 0; i < r.length(); i++) {
				intAr[i] = Character.getNumericValue(r.charAt(i));

			}
			System.out.println(Arrays.toString(intAr));
		}
	}
}

