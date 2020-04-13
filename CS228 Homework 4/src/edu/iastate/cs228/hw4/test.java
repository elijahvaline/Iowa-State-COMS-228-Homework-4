package edu.iastate.cs228.hw4;

import java.util.Scanner;

public class test {

	public static void main(String[] args) throws Exception {
		String s = "( 3 + 2 + + + ) % 2";
		InfixExpression e = new InfixExpression(s);
		System.out.println(e.toString());
		try {
		e.postfix();
		}
		catch(Exception e1) {
			System.out.println(e1);
		}
		
	
		System.out.println("Hello");
		
		
		

	}

}
