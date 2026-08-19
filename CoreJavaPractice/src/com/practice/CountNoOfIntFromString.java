package com.practice;

public class CountNoOfIntFromString {
	
	public static void main(String[] args) {
		
		String s = "amit1910e993";
		int count = 0;
		
		for(int i=0;i<s.length();i++) {
			if(Character.isDigit(s.charAt(i))) {
				count++;
			
		}			
		}
		System.out.println("No. of Integer from String:" + count);
		
	}
	}


