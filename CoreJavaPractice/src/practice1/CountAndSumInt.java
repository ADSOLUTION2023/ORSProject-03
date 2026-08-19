package practice1;

public class CountAndSumInt {
	
	public static void main(String[] args) {
		String s = "amit12345";
		int count  = 0;
		int sum = 0;
		
		for (int i =0;i<s.length();i++) {
			char[] ch = s.toCharArray();
			if(Character.isDigit(s.charAt(i))){
				sum = sum + Character.getNumericValue(s.charAt(i));
				count++;
			} 
		}
		System.out.println(count);
		System.out.println(sum);
		
	}

}
