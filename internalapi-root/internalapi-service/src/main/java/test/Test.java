package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String args[]) {
		String idcard15="/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/;";
		String idcard18="/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$/;";
		
		String idNum="371522198510117832";
		//System.out.print(idNum.matches(idcard18));
		
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
		 Matcher idNumMatcher = idNumPattern.matcher(idNum);  
		 System.out.println(idNumMatcher.matches());
	}
}
