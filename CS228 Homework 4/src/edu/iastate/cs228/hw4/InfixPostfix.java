package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix 
{
private static Scanner sc;

//
//	private static Scanner sc;
//	private static int trial;
//	private static boolean running;
//	private static int state;
//	private static String expression;
//	private static Character iOrP;

	/**
	 * Repeatedly evaluates input infix and postfix expressions.  See the project description
	 * for the input description. It constructs a HashMap object for each expression and passes it 
	 * to the created InfixExpression or PostfixExpression object. 
	 *  
	 * @param args
	 **/
	public static void main(String[] args) 
	{
		
		String iOrP;
		String curr;
		String expression = "";
		int state;
		int value;
		int eValue = 0;
		int trial = 1;
		boolean first = true;
		boolean running = true;
		HashMap<Character, Integer> h = new HashMap<Character, Integer>();
		sc = new Scanner(System.in);;
		Scanner s;

		
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out.println("(Enter \"I\" before an infix expression, \"P\" before a postfix expression) \n");
		
		while (running) {
			
			System.out.print("Trial " + trial + ":");
			state = sc.nextInt();
			
			switch (state) {
			
			case 1:
				
				System.out.print("Expression: ");
				iOrP = sc.next();
				expression = sc.nextLine().substring(1);
				s = new Scanner(expression);
				
				switch (iOrP) {
				
				case "I":
					
					InfixExpression in = new InfixExpression(expression, h);
					System.out.println("Infix form: " + in.toString());
					
					try {
						in.postfix();
					} catch (Exception e) {
						System.out.println(e);
						break;
					}
					
					System.out.println("Postfix form: " + in.postfixString());
					
					
					while (s.hasNext()) {
						curr = s.next();
						if (Character.isLetter(curr.charAt(0))) {
							if (first == true) {
								System.out.println("where");
								first = false;
							}
							
							System.out.print(curr.charAt(0) + " = ");
							value = sc.nextInt();
							in.varTable.put(curr.charAt(0), value);
	
						}
					}
					
					try {
						eValue = in.evaluate();
					} catch (Exception e) {
						System.out.println(e);
						break;
					} 
					System.out.println("Expression value: " + eValue);
					System.out.println("\n \n");
					break;
					
				case "P":
					PostfixExpression p = new PostfixExpression(expression, h);
					System.out.println("Postfix form: " + p.toString());
					
					while (s.hasNext()) {
						curr = s.next();
						if (Character.isLetter(curr.charAt(0))) {
							if (first == true) {
								System.out.println("where");
								first = false;
							}
							
							System.out.print(curr.charAt(0) + " = ");
							value = sc.nextInt();
							p.varTable.put(curr.charAt(0), value);
	
						}
					}
					
					try {
						eValue = p.evaluate();
					} catch (Exception e) {
						System.out.println(e);
						break;
					} 
					System.out.println("Expression value: " + eValue);
					System.out.println("\n \n");

					break;
				}
				
				
				break;
			case 2:
				
				break;
				
			case 3:
				running = false;
				break;
			}
			
			
			
			trial++;
			
		}
		
	}
	
	// helper methods if needed
}
