package edu.iastate.cs228.hw4;

import java.util.Scanner;

public class test {

	public static void main(String[] args) throws ExpressionFormatException {
		String s = "- ( 2 * i % - 3 ) * 5";
		InfixExpression e = new InfixExpression(s);
		System.out.println(e.toString());
		
		

		e.postfix();
		System.out.println(e.postfixString());
		
		

	}

}
