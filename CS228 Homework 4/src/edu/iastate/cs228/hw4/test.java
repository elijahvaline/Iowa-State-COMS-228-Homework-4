package edu.iastate.cs228.hw4;

import java.util.Scanner;

public class test {

	public static void main(String[] args) {
		String s = "a b 5 2 f s";
		Scanner sc = new Scanner(s);
		int i = 15;
		
		while (sc.hasNext()) {
			System.out.println(sc.next());
		}

		System.out.println(Expression.removeExtraSpaces(s));
		System.out.println(s);
	}

}
