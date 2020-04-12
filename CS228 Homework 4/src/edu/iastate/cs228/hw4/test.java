package edu.iastate.cs228.hw4;

import java.util.Scanner;

public class test {

	public static void main(String[] args) throws Exception {
		String s = "( 3 + 2 & ) * 2 + 2 - 5 + 7";
		InfixExpression e = new InfixExpression(s);
		System.out.println(e.toString());
		e.postfix();
		System.out.println(e.postfixString());
		
	
		System.out.println("Hello");
		
		
		

	}

}
